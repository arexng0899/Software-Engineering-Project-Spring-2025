package controller;

@RestController
@RequestMapping("/reviews")
class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProfessorRepository professorRepository;

    @PostMapping("/{professorId}")
    public Review createReview(@PathVariable Long professorId, @RequestBody Review review) {
        Professor professor = professorRepository.findById(professorId).orElseThrow(() -> new RuntimeException("Professor not found"));
        review.setProfessor(professor);
        return reviewRepository.save(review);
    }

    @GetMapping("/professor/{professorId}")
    public List<Review> getReviewsByProfessor(@PathVariable Long professorId) {
        return reviewRepository.findAll().stream().filter(r -> r.getProfessor().getId().equals(professorId)).toList();
    }
}