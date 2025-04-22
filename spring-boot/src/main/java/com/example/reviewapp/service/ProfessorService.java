package com.example.reviewapp.service;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.User;
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
    
    public Optional<Professor> findById(Long id) {
        return professorRepository.findById(id);
    }
    
    public Optional<Professor> findByUser(User user) {
        return professorRepository.findByUser(user);
    }
    
    public List<Professor> searchByUsername(String username) {
        return professorRepository.findByUser_UsernameContainingIgnoreCase(username);
    }
    
    public List<Professor> searchByDepartment(String department) {
        return professorRepository.findByDepartmentContainingIgnoreCase(department);
    }
    
    public List<Professor> searchByUniversity(String university) {
        return professorRepository.findByUniversityContainingIgnoreCase(university);
    }
    
    public List<Professor> searchByAll(String query) {
        return professorRepository.findByDepartmentContainingIgnoreCaseOrUniversityContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(
                query, query, query);
    }
    
    public Professor save(Professor professor) {
        return professorRepository.save(professor);
    }
}
