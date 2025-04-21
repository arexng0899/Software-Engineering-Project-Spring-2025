package com.example.reviewapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.reviewapp.service.ReviewService;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping("/")
    public String home(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("userId") != null) {
            model.addAttribute("isLoggedIn", true);
        } else {
            model.addAttribute("isLoggedIn", false);
        }
        
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