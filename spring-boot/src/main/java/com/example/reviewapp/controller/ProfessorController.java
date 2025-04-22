package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.ReviewService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private ReviewService reviewService;
    
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
    public String getProfessorById(@PathVariable Long id, Model model, HttpSession session) {
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
        public Professor getProfessorById(@PathVariable Long id) {
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

