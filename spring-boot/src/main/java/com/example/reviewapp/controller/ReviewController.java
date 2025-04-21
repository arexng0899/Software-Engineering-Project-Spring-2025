package com.example.reviewapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.entity.User;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.ReviewService;
import com.example.reviewapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String reviewPage(Model model, HttpSession session) {
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        List<Review> recentReviews = reviewService.findRecentReviews();
        model.addAttribute("reviews", recentReviews);
        
        return "review";
    }
    
    @PostMapping
    public String createReview(
            @RequestParam String professorName,
            @RequestParam String courseId,
            @RequestParam String className,
            @RequestParam String content,
            @RequestParam(required = false) String department,
            HttpSession session,
            Model model) {
        
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Get or create professor
        Professor professor;
        Optional<Professor> professorOpt = professorService.findByName(professorName);
        if (professorOpt.isPresent()) {
            professor = professorOpt.get();
            // Update department if provided and different
            if (department != null && !department.isEmpty() && 
                (professor.getDepartment() == null || !professor.getDepartment().equals(department))) {
                professor.setDepartment(department);
                professor = professorService.save(professor);
            }
        } else {
            professor = new Professor();
            professor.setName(professorName);
            if (department != null && !department.isEmpty()) {
                professor.setDepartment(department);
            }
            professor = professorService.save(professor);
        }
        
        // Get user
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        // Create review
        Review review = new Review();
        review.setProfessor(professor);
        review.setUser(user);
        review.setCourseId(courseId);
        review.setClassName(className);
        review.setContent(content);
        
        reviewService.save(review);
        
        return "redirect:/reviews";
    }
    
    // REST endpoints for API access
    @RestController
    @RequestMapping("/api/reviews")
    public static class ReviewRestController {
        @Autowired
        private ReviewService reviewService;
        
        @Autowired
        private ProfessorService professorService;
        
        @GetMapping("/professor/{professorId}")
        public List<Review> getReviewsByProfessor(@PathVariable Long professorId) {
            Optional<Professor> professorOpt = professorService.findById(professorId);
            return professorOpt.map(reviewService::findByProfessor).orElse(null);
        }
    }
}
