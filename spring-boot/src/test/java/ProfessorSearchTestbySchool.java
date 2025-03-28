package com.example.reviewapp.controller;

import com.example.reviewapp.entity.Professor;
import com.example.reviewapp.repository.ProfessorRepository;
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

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProfessorSearchTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private ProfessorRepository professorRepository;

    private Professor professor1;
    private Professor professor2;

    @BeforeEach
    void setUp() {
        professor1 = new Professor("Dr. David James", "Software Engineering", "Georgia State University");
        professor1.setId(1L);
        
        professor2 = new Professor("Dr. Louis Henry ", "Mobile App Developement", "Georgia State University");
        professor2.setId(2L);
    }

    @Test
    @DisplayName("Should return professors by school when GET /professors/school/{schoolName}")
    public void testSearchProfessorBySchool() throws Exception {
        List<Professor> professors = Arrays.asList(professor1, professor2);
        when(professorRepository.findBySchool("Georgia State University")).thenReturn(professors);

        mockMvc.perform(get("/professors/school/Georgia State University")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Dr. David James")))
                .andExpect(jsonPath("$[1].name", is("Dr. Louid Henry")));
        
        verify(professorRepository, times(1)).findBySchool("Georgia State University");
    }
}
