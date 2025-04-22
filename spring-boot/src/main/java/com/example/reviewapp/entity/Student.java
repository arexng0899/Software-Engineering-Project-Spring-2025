package com.example.reviewapp.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name = "study_hours_per_week")
    private Integer studyHoursPerWeek;
    
    @OneToMany(mappedBy = "student")
    private List<Review> reviews;
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Integer getStudyHoursPerWeek() {
        return studyHoursPerWeek;
    }
    
    public void setStudyHoursPerWeek(Integer studyHoursPerWeek) {
        this.studyHoursPerWeek = studyHoursPerWeek;
    }
    
    public List<Review> getReviews() {
        return reviews;
    }
    
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
