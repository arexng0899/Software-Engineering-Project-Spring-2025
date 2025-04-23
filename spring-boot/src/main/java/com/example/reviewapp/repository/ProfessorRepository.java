package com.example.reviewapp.repository;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Integer> {
    Optional<Professor> findByUser(User user);
    List<Professor> findByDepartmentContainingIgnoreCase(String department);
    List<Professor> findByUniversityContainingIgnoreCase(String university);
    List<Professor> findByUser_UsernameContainingIgnoreCase(String username);
    List<Professor> findByDepartmentContainingIgnoreCaseOrUniversityContainingIgnoreCaseOrUser_UsernameContainingIgnoreCase(
            String department, String university, String username);
}
