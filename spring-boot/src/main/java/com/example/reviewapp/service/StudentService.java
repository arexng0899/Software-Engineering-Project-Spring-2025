package com.example.reviewapp.service;

import com.example.reviewapp.entity.Student;
import com.example.reviewapp.entity.User;
import com.example.reviewapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    
    public Student save(Student student) {
        return studentRepository.save(student);
    }
    
    public Optional<Student> findById(Integer id) {
        return studentRepository.findById(id);
    }
    
    public Optional<Student> findByUser(User user) {
        return studentRepository.findByUser(user);
    }
}
