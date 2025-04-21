package com.example.reviewapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.service.ProfessorService;
import com.example.reviewapp.service.ReviewService;

@Controller
@RequestMapping("/professors")
public class ProfessorController {
    @Autowired
    private ProfessorService professorService;
    
    @Autowired
    private ReviewService reviewService;
    
    @GetMapping
    public String getAllProfessors(Model model) {
        List<Professor> professors = professorService.findAll();
        model.addAttribute("professors", professors);
        return "professors";
    }
    
    @GetMapping("/{id}")
    public String getProfessorById(@PathVariable Long id, Model model) {
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
    public String searchProfessors(@RequestParam String query, @RequestParam(required = false) String searchType, Model model) {
        List<Professor> professors;
        
        if (searchType != null && searchType.equals("department")) {
            professors = professorService.searchByDepartment(query);
            model.addAttribute("searchType", "department");
        } else if (searchType != null && searchType.equals("name")) {
            professors = professorService.searchByName(query);
            model.addAttribute("searchType", "name");
        } else {
            professors = professorService.searchByNameOrDepartment(query);
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
        
        @PostMapping
        public Professor createProfessor(@RequestBody Professor professor) {
            return professorService.save(professor);
        }
    }
}
