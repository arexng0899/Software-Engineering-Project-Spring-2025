package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Review;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private ProfessorService professorService;
    
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("userId") != null) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("username", session.getAttribute("username"));
            model.addAttribute("userRole", session.getAttribute("userRole"));
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        
        // Add recent reviews to the home page
        model.addAttribute("recentReviews", reviewService.findRecentReviews());
        
        return "home";
    }
    
    @GetMapping("/login")
    public String login(HttpSession session) {
        // If user is already logged in, redirect to home
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }
        return "login";
    }
    
    @GetMapping("/signup")
    public String signup(HttpSession session) {
        // If user is already logged in, redirect to home
        if (session.getAttribute("userId") != null) {
            return "redirect:/";
        }
        return "signup";
    }
}
