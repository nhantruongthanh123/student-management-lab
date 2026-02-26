package vn.edu.hcmut.cse.adse.lab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmut.cse.adse.lab.service.StudentService;
import vn.edu.hcmut.cse.adse.lab.entity.Student;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/students")
public class StudentWebController {
    @Autowired
    private StudentService service;
    // Route: GET http://localhost:8080/students
    @GetMapping
    public String getAllStudents(@RequestParam(required = false) String keyword, Model model) {
        List<Student> students;
        if (keyword != null && !keyword.isEmpty()) {
            students = service.searchByName(keyword);
        } else {
            students = service.getAll();
        }
        model.addAttribute("dsSinhVien", students);
        return "students";
    }
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        Student student = new Student();
        model.addAttribute("student", student);
        return "create_student";
    }
    @PostMapping
    public String saveStudent(@ModelAttribute("student") Student student) {
        service.saveStudent(student);
        return "redirect:/students";
    }
    @GetMapping("/{id}")
    public String viewStudent(@PathVariable("id") Integer id, Model model) {
        Student student = service.getById(id);
        model.addAttribute("student", student);
        return "student_detail";
    }
    @GetMapping("edit/{id}")
    public String editStudent(@PathVariable("id") Integer id, Model model) {
        Student student = service.getById(id);
        model.addAttribute("student", student);
        return "edit_student";
    }
    @PostMapping("/update")
    public String updateStudent(@ModelAttribute("student") Student student) {
        service.saveStudent(student);
        return "redirect:/students";
    }
    @GetMapping("delete/{id}")
    public String deleteStudent(@PathVariable("id") Integer id, Model model){
        service.deleteStudent(id);
        return "redirect:/students";
    }
}
