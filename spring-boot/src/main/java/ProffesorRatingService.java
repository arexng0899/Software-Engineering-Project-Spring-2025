public class ProffesorRatingService {
    private final ProfessorRepository professorRepository;

    public ProfessorRatingService(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    public void rateProfessor(Long professorId, double rating) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor not found"));
        // Update rating logic here
    }

}
