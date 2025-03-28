package com.example.reviewapp.entity;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reviewText;

    @ManyToOne
    @JoinColumn(name = "professor_id", nullable = false)
    private Professor professor;
    
    public Review() {
    }

    public Review(String reviewText, Professor professor) {
        this.reviewText = reviewText;
        this.professor = professor;
    }

    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getReviewText() { 
        return reviewText; 
    }
    
    public void setReviewText(String reviewText) { 
        this.reviewText = reviewText; 
    }
    
    public Professor getProfessor() { 
        return professor; 
    }
    
    public void setProfessor(Professor professor) { 
        this.professor = professor; 
    }
}
