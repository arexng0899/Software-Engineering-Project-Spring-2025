package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.entity.User;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.ReviewService;
import com.example.reviewapp.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping
    public String getAllProfessors(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        
        List<Professor> professors = professorService.findAll();
        model.addAttribute("professors", professors);
        return "professors";
    }
    
    @GetMapping("/{id}")
    public String getProfessorById(@PathVariable Integer id, Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        
        Optional<Professor> professorOpt = professorService.findById(id);
        if (professorOpt.isPresent()) {
            Professor professor = professorOpt.get();
            List<Review> reviews = reviewService.findByProfessor(professor);
            
            model.addAttribute("professor", professor);
            model.addAttribute("reviews", reviews);
            return "professor-detail";
        }
        return "redirect:/professors";
    }
    
    @GetMapping("/search")
    public String searchProfessors(
            @RequestParam String query, 
            @RequestParam(required = false) String searchType, 
            Model model,
            HttpSession session) {
        
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        
        List<Professor> professors;
        
        if (searchType != null && searchType.equals("department")) {
            professors = professorService.searchByDepartment(query);
            model.addAttribute("searchType", "department");
        } else if (searchType != null && searchType.equals("university")) {
            professors = professorService.searchByUniversity(query);
            model.addAttribute("searchType", "university");
        } else if (searchType != null && searchType.equals("name")) {
            professors = professorService.searchByUsername(query);
            model.addAttribute("searchType", "name");
        } else {
            professors = professorService.searchByAll(query);
            model.addAttribute("searchType", "all");
        }
        
        model.addAttribute("professors", professors);
        model.addAttribute("query", query);
        return "professors";
    }
    
    @GetMapping("/create")
    public String showCreateProfessorForm(Model model, HttpSession session) {
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        
        model.addAttribute("professor", new Professor());
        return "create-professor";
    }
    
    @PostMapping("/create")
    public String createProfessor(
            @RequestParam String username,
            @RequestParam String department,
            @RequestParam String university,
            HttpSession session,
            Model model) {
        
        // Check if user is logged in
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        
        // Check if username already exists
        if (userService.usernameExists(username)) {
            model.addAttribute("error", "A professor with this username already exists");
            return "create-professor";
        }
        
        // Create user for professor
        User user = new User();
        user.setUsername(username);
        // Generate a random email since we don't collect it anymore
        user.setEmail(username.replaceAll("\\s+", ".").toLowerCase() + "@university.edu");
        user.setPassword("professor123"); // Default password
        user.setRole(User.UserRole.professor);
        User savedUser = userService.createUser(user);
        
        // Create professor profile
        Professor professor = new Professor();
        professor.setUser(savedUser);
        professor.setDepartment(department);
        professor.setUniversity(university);
        professorService.save(professor);
        
        return "redirect:/professors";
    }
    
    // REST endpoints for API access
    @RestController
    @RequestMapping("/api/professors")
    public static class ProfessorRestController {
        @Autowired
        private ProfessorService professorService;
        
        @GetMapping
        public List<Professor> getAllProfessors() {
            return professorService.findAll();
        }
        
        @GetMapping("/{id}")
        public Professor getProfessorById(@PathVariable Integer id) {
            return professorService.findById(id).orElse(null);
        }
        
        @GetMapping("/search")
        public List<Professor> searchProfessors(
                @RequestParam String query,
                @RequestParam(required = false) String searchType) {
            
            if (searchType != null && searchType.equals("department")) {
                return professorService.searchByDepartment(query);
            } else if (searchType != null && searchType.equals("university")) {
                return professorService.searchByUniversity(query);
            } else if (searchType != null && searchType.equals("name")) {
                return professorService.searchByUsername(query);
            } else {
                return professorService.searchByAll(query);
            }
        }
    }
}
