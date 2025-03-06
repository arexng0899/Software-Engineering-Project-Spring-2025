//demo example

public class ProfessorListDisplay extends JFrame {

    private JList<String> professorList;

    public ProfessorListDisplay() {
        setTitle("Professors List");
        setLayout(new BorderLayout());
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      
        List<String> professors = new ArrayList<>();
        professors.add("Dr. David James - Average Rating: 4.5");

        professorList = new JList<>(professors.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(professorList);

        add(scrollPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        ProfessorListDisplay display = new ProfessorListDisplay();
        display.setVisible(true);
    }
}
