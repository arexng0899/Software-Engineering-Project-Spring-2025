public class ProfileCreation extends JFrame {
    private JTextField usernameField, emailField;
    private JButton saveButton;

    public ProfileCreation() {
        setTitle("Profile Page");
        setSize(350, 250);
        setLayout(new GridLayout(3, 2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        saveButton = new JButton("Save Profile");
        add(saveButton);

        saveButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            JOptionPane.showMessageDialog(this, "Profile Created:\nUsername: " + username + "\nEmail: " + email);
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new ProfileCreation();
    }
}
