package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.entity.Review;
import com.example.reviewapp.repository.ProfessorRepository;
import com.example.reviewapp.repository.ReviewRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ReviewRepository reviewRepository;

    private ProfessorRepository professorRepository;

    private Professor testProfessor;
    private Review testReview;

    @BeforeEach
    void setUp() {
        testProfessor = new Professor("John Doe", "Computer Science");
        testProfessor.setId(1L);
        
        testReview = new Review("Great professor!", testProfessor);
        testReview.setId(1L);
    }

    @Test
    @DisplayName("Should create a new review when POST /reviews/{professorId}")
    public void testCreateReview() throws Exception {
        when(professorRepository.findById(1L)).thenReturn(Optional.of(testProfessor));
        when(reviewRepository.save(any(Review.class))).thenReturn(testReview);

        Review reviewToCreate = new Review();
        reviewToCreate.setReviewText("Great professor!");

        mockMvc.perform(post("/reviews/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reviewToCreate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewText", is("Great professor!")));
        
        verify(professorRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    @DisplayName("Should return reviews for a professor when GET /reviews/professor/{professorId}")
    public void testGetReviewsByProfessor() throws Exception {
        Review review2 = new Review("Very knowledgeable!", testProfessor);
        review2.setId(2L);
        
        List<Review> reviews = Arrays.asList(testReview, review2);
        
        when(reviewRepository.findAll()).thenReturn(reviews);

        mockMvc.perform(get("/reviews/professor/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].reviewText", is("Great professor!")))
                .andExpect(jsonPath("$[1].reviewText", is("Very knowledgeable!")));
        
        verify(reviewRepository, times(1)).findAll();
    }
}

