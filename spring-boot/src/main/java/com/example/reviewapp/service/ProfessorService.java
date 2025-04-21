package com.example.reviewapp.service;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {
    @Autowired
    private ProfessorRepository professorRepository;
    
    public List<Professor> findAll() {
        return professorRepository.findAll();
    }
    
    public Optional<Professor> findByName(String name) {
        return professorRepository.findByName(name);
    }
    
    public List<Professor> searchByName(String name) {
        return professorRepository.findByNameContainingIgnoreCase(name);
    }
    
    public List<Professor> searchByDepartment(String department) {
        return professorRepository.findByDepartmentContainingIgnoreCase(department);
    }
    
    public List<Professor> searchByNameOrDepartment(String query) {
        return professorRepository.findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(query, query);
    }
    
    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }
}
