package com.example.demo.contollers;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping(path = "api/v1")
@RestController
public class StudentController {
    private final StudentService studentService;
    @Autowired
    StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @GetMapping(path = "/api/{id}")
    public List<Long> t(@PathVariable(name="id") Long id){
        return List.of(id);
    }
    @GetMapping("/")
    public List<Student> getStudents(){
        return studentService.getStudents();
    }
    @PostMapping("/saveStudent")
    public void registerNewStudent(@RequestBody Student student){
        studentService.addNewStudent(student);
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable(name = "studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }
    @PutMapping(path = "{studentId}")
    public void updateStudentByEmailAndName(
            @PathVariable(name = "studentId") Long studentId,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String name){
        studentService.updateStudentsEmailAndName(studentId, email, name);
    }
}
