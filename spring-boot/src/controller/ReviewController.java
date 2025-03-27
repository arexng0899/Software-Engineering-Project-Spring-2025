package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.repository.ProfessorRepository;
import com.example.reviewapp.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping("/{professorId}")
    public Review createReview(@PathVariable Long professorId, @RequestBody Review review) {
        Professor professor = professorRepository.findById(professorId)
            .orElseThrow(() -> new RuntimeException("Professor not found"));
        review.setProfessor(professor);
        return reviewRepository.save(review);
    }

    @GetMapping("/professor/{professorId}")
    public List<Review> getReviewsByProfessor(@PathVariable Long professorId) {
        return reviewRepository.findAll().stream()
            .filter(r -> r.getProfessor().getId().equals(professorId))
            .collect(Collectors.toList());
    }
}
