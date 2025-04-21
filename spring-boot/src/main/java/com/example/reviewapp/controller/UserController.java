package com.example.reviewapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.reviewapp.entity.User;
import com.example.reviewapp.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    // Changed from /user/login to /login to match the form action
    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password, HttpSession session, Model model) {
        if (userService.authenticate(name, password)) {
            Optional<User> userOpt = userService.findByName(name);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute("userId", user.getId());
                session.setAttribute("userName", user.getName());
                return "redirect:/profile";
            }
        }
        
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
    
    // Changed from /user/signup to /signup to match the form action
    @PostMapping("/signup")
    public String signup(@ModelAttribute User user, @RequestParam String confirmPassword, Model model) {
        // Validate password match
        if (!user.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }
        
        // Check if email already exists
        if (userService.emailExists(user.getEmail())) {
            model.addAttribute("error", "Email already in use");
            return "signup";
        }
        
        // Create user
        userService.createUser(user);
        
        return "redirect:/login";
    }
    
    // Changed from /user/profile to /profile to match the links in templates
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "user-profile";
        }
        
        return "redirect:/login";
    }
    
    // Changed from /user/logout to /logout to match the links in templates
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
