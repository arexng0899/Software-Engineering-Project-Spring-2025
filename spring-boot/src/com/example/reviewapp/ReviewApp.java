package com.example.reviewapp;

import jakarta.persistence.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@SpringBootApplication
public class ReviewApp {
    public static void main(String[] args) {
        SpringApplication.run(ReviewApp.class, args);
    }
}
