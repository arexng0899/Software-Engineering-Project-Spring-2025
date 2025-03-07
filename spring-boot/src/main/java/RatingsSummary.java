JPanel professorProfilePanel = new JPanel();
professorProfilePanel.setLayout(new BoxLayout(professorProfilePanel, BoxLayout.Y_AXIS));

professorProfilePanel.add(new JLabel("Professor: David James"));
professorProfilePanel.add(new JLabel("Average Rating: 4.5 / 5"));

JPanel reviewsPanel = new JPanel();
reviewsPanel.setLayout(new BoxLayout(reviewsPanel, BoxLayout.Y_AXIS));
reviewsPanel.add(new JLabel("Recent Reviews:"));
reviewsPanel.add(new JTextArea("Great professor, very clear explanitory and lectures are understandible!"));
reviewsPanel.add(new JTextArea("Could improve in providing more examples and gives too much assighnments."));
professorProfilePanel.add(reviewsPanel);
