package com.example.reviewapp.repository;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByProfessor(Professor professor);
    List<Review> findByUser(User user);
    List<Review> findTop10ByOrderByCreatedAtDesc();
}
