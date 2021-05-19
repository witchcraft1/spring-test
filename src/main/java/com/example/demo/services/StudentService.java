package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Component == @Service but @Service is more readable and easier for understanding that it is exactly some Service
//so @Service is just for semantic purposes
@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student){
        Optional<Student> studentByEmail = studentRepository.findStudentByEmail(student.getEmail());
        if(studentByEmail.isPresent())
            throw new IllegalArgumentException("email taken");
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists)
            throw new IllegalArgumentException("student doesnt exist with id: " + studentId);
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudentsEmailAndName(Long studentId, String email, String name) {
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        if(studentOptional.isEmpty())
            throw new IllegalArgumentException();
        Student student = studentOptional.get();

        if(name != null && name.length() > 0 && !Objects.equals(name,student.getName()))
            student.setName(name);

        if(email != null && email.length() > 0 &&
                !Objects.equals(email, student.getEmail()) &&
                studentRepository.findStudentByEmail(email).isEmpty())
            student.setEmail(email);

    }
}
