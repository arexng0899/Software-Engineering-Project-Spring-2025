package com.example.reviewapp.repository;

import com.example.reviewapp.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    Optional<Professor> findByName(String name);
    List<Professor> findByNameContainingIgnoreCase(String name);
    List<Professor> findByDepartmentContainingIgnoreCase(String department);
    List<Professor> findByNameContainingIgnoreCaseOrDepartmentContainingIgnoreCase(String name, String department);
}
