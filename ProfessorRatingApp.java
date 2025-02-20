import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfessorRatingApp {
   
    private static List<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        System.out.println("Welcome to the Professor Rating App");

        while (!exit) {
            System.out.println("\nMenu:");
            System.out.println("1. Register Student Profile");
            System.out.println("2. View All Students");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    registerStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
        System.out.println("Exiting the app. Goodbye!");
    }

    
    private static void registerStudent(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter study hours (1-10): ");
        int studyHours = scanner.nextInt();
        scanner.nextLine(); 

        Student student = new Student(username, email, password, studyHours);
        students.add(student);
        System.out.println("Student registered successfully: " + student);
    }

    
    private static void viewStudents() {
        System.out.println("List of registered students:");
        for (Student student : students) {
            System.out.println(student);
        }
    }
}
