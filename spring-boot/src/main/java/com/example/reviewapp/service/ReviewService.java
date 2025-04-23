package com.example.reviewapp.service;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.entity.Student;
import com.example.reviewapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;
    
    public Review save(Review review) {
        return reviewRepository.save(review);
    }
    
    public List<Review> findByProfessor(Professor professor) {
        return reviewRepository.findByProfessor(professor);
    }
    
    public List<Review> findByStudent(Student student) {
        return reviewRepository.findByStudent(student);
    }
    
    public List<Review> findRecentReviews() {
        return reviewRepository.findTop10ByOrderByCreatedAtDesc();
    }
    
    public Optional<Review> findById(Integer id) {
        return reviewRepository.findById(id);
    }
    
    public void delete(Integer id) {
        reviewRepository.deleteById(id);
    }
}
