package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Student;
import com.example.reviewapp.entity.User;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.StudentService;
import com.example.reviewapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private ProfessorService professorService;
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        if (userService.authenticate(username, password)) {
            Optional<User> userOpt = userService.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute("userId", user.getId());
                session.setAttribute("username", user.getUsername());
                session.setAttribute("userRole", user.getRole().toString());
                return "redirect:/profile";
            }
        }
        
        model.addAttribute("error", "Invalid username or password");
        return "login";
    }
    
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
        
        // Check if username already exists
        if (userService.usernameExists(user.getUsername())) {
            model.addAttribute("error", "Username already in use");
            return "signup";
        }
        
        // Set role to student by default
        user.setRole(User.UserRole.student);
        
        // Create user
        User savedUser = userService.createUser(user);
        
        // Create student profile
        Student student = new Student();
        student.setUser(savedUser);
        student.setStudyHoursPerWeek(0); // Default value
        studentService.save(student);
        
        return "redirect:/login";
    }
    
    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            
            if (user.isStudent()) {
                Optional<Student> studentOpt = studentService.findByUser(user);
                studentOpt.ifPresent(student -> model.addAttribute("student", student));
            } else if (user.isProfessor()) {
                Optional<Professor> professorOpt = professorService.findByUser(user);
                professorOpt.ifPresent(professor -> model.addAttribute("professor", professor));
            }
            
            return "user-profile";
        }
        
        return "redirect:/login";
    }
    
    @GetMapping("/profile/edit")
    public String editProfileForm(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            model.addAttribute("user", user);
            
            if (user.isStudent()) {
                Optional<Student> studentOpt = studentService.findByUser(user);
                studentOpt.ifPresent(student -> model.addAttribute("student", student));
            } else if (user.isProfessor()) {
                Optional<Professor> professorOpt = professorService.findByUser(user);
                professorOpt.ifPresent(professor -> model.addAttribute("professor", professor));
            }
            
            return "edit-profile";
        }
        
        return "redirect:/login";
    }
    
    @PostMapping("/profile/update")
    public String updateProfile(
            @RequestParam String email,
            @RequestParam(required = false) Integer studyHoursPerWeek,
            HttpSession session,
            Model model) {
        
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        
        Optional<User> userOpt = userService.findById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            
            // Update email if changed
            if (!user.getEmail().equals(email)) {
                // Check if email is already in use by another user
                if (userService.emailExists(email) && !userService.findByEmail(email).get().getId().equals(userId)) {
                    model.addAttribute("error", "Email already in use by another account");
                    model.addAttribute("user", user);
                    return "edit-profile";
                }
                user.setEmail(email);
                userService.createUser(user); // Save updated user
            }
            
            // Update student-specific fields
            if (user.isStudent() && studyHoursPerWeek != null) {
                Optional<Student> studentOpt = studentService.findByUser(user);
                if (studentOpt.isPresent()) {
                    Student student = studentOpt.get();
                    student.setStudyHoursPerWeek(studyHoursPerWeek);
                    studentService.save(student);
                }
            }
            
            return "redirect:/profile";
        }
        
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
