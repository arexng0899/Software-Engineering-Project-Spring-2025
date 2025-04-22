package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.entity.Student;
import com.example.reviewapp.entity.User;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.ReviewService;
import com.example.reviewapp.service.StudentService;
import com.example.reviewapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentService studentService;
    
    @GetMapping
    public String reviewPage(Model model, HttpSession session) {
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Get user
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        
        User user = userOpt.get();
        
        // Check if user is a student
        if (!user.isStudent()) {
            model.addAttribute("error", "Only students can submit reviews");
        }
        
        // Get all professors for the dropdown
        List<Professor> professors = professorService.findAll();
        model.addAttribute("professors", professors);
        
        // Get recent reviews
        List<Review> recentReviews = reviewService.findRecentReviews();
        model.addAttribute("reviews", recentReviews);
        
        return "review";
    }
    
    @PostMapping
    public String createReview(
            @RequestParam Long professorId,
            @RequestParam String courseId,
            @RequestParam String className,
            @RequestParam String content,
            @RequestParam Integer rating,
            HttpSession session,
            Model model) {
        
        // Check if user is logged in
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        // Get professor
        Optional<Professor> professorOpt = professorService.findById(professorId);
        if (professorOpt.isEmpty()) {
            model.addAttribute("error", "Professor not found");
            return "review";
        }
        Professor professor = professorOpt.get();
        
        // Get user
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isEmpty()) {
            return "redirect:/login";
        }
        User user = userOpt.get();
        
        // Check if user is a student
        if (!user.isStudent()) {
            model.addAttribute("error", "Only students can submit reviews");
            return "review";
        }
        
        // Get student
        Optional<Student> studentOpt = studentService.findByUser(user);
        if (studentOpt.isEmpty()) {
            model.addAttribute("error", "Student profile not found");
            return "review";
        }
        Student student = studentOpt.get();
        
        // Create review
        Review review = new Review();
        review.setProfessor(professor);
        review.setStudent(student);
        review.setCourseId(courseId);
        review.setClassName(className);
        review.setContent(content);
        review.setRating(rating);
        
        reviewService.save(review);
        
        model.addAttribute("success", "Review submitted successfully!");
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
