public class ProfessorSearchTest {

    private MockMvc mockMvc;

    private ProfessorService professorService;

    public void searchProfessorBySchool_ShouldReturnProfessors() throws Exception {
        String schoolName = "Georgia State University";
        List<Professor> professors = Arrays.asList(
            new Professor(1L, "Dr. David James", "Software Engineering", "Georgia State University"),
            new Professor(2L, "Louis Henry", "Mobile App Programming", "Georgia State University")
        );

        given(professorService.getProfessorsBySchool(schoolName)).willReturn(professors);

        this.mockMvc.perform(get("/api/professors")
                .param("school", schoolName)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dr. David James"))
                .andExpect(jsonPath("$[1].name").value("Louis Henry"));

        then(professorService).should(times(1)).getProfessorsBySchool(schoolName);
    }
}
