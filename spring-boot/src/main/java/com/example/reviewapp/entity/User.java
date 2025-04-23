package com.example.reviewapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;
    
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Student student;
    
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Professor professor;

    @Column(name = "profile_picture")
    private String profilePicture;
    
    public enum UserRole {
        student, professor, admin
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public Student getStudent() {
        return student;
    }
    
    public void setStudent(Student student) {
        this.student = student;
    }
    
    public Professor getProfessor() {
        return professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }
    
    // Helper method to check if user is a student
    public boolean isStudent() {
        return role == UserRole.student;
    }
    
    // Helper method to check if user is a professor
    public boolean isProfessor() {
        return role == UserRole.professor;
    }
    
    // Helper method to check if user is an admin
    public boolean isAdmin() {
        return role == UserRole.admin;
    }

    public String getProfilePicture() {
        return profilePicture;
    }
    
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
