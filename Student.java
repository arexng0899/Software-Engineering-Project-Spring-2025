public class Student {
    private String username;
    private String email;
    private String password;
    private int studyHours;

    public Student(String username, String email, String password, int studyHours) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.studyHours = studyHours;
    }
    public String toString() {
        return "Student [username=" + username + ", email=" + email + ", studyHours=" + studyHours + "]";
    }
}

