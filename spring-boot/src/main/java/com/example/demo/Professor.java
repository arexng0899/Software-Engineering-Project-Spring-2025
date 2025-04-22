package com.example.demo;

import jakarta.persistence.*;

@Entity
@Table(name = "professors")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long professor_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String department;
    private String university;

    // Getters and Setters

    public Long getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(Long professor_id) {
        this.professor_id = professor_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }
}
