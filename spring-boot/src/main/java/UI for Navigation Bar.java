JPanel navPanel = new JPanel();
navPanel.setLayout(new FlowLayout(FlowLayout.LEFT)):

JButton homeButton = new JButton("Home");
JButton profileButton = new JButton("Profile");
JButton submitReviewButton = new JButton("Submit Review");
JButton logoutButton = new JButton("Logout");

navPanel.add(homeButton):
navPanel.add(profileButton);
navPanel.add(submitReviewButton);
navPanel.add(logoutButton);
