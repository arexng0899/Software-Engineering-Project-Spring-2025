package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long student_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int study_hours_per_week;

    // Getters and Setters

    public Long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(Long student_id) {
        this.student_id = student_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStudy_hours_per_week() {
        return study_hours_per_week;
    }

    public void setStudy_hours_per_week(int study_hours_per_week) {
        this.study_hours_per_week = study_hours_per_week;
    }
}
