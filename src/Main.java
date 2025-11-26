import java.util.List;
import java.util.Scanner;
import model.Student;
import service.Loader;
import service.StudentManager;
import service.StudentNotFoundException;
import util.FileUtil;

public class Main {
    public static void main(String[] args) {
        List<Student> loaded = FileUtil.loadStudents();
        StudentManager manager = new StudentManager(loaded);

        Scanner sc = new Scanner(System.in);
        int choice = 0;

        try {
            do {
                System.out.println("===== Capstone Student Menu =====");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Search by Roll No");
                System.out.println("4. Delete by Roll No");
                System.out.println("5. Sort by Marks");
                System.out.println("6. Save and Exit");
                System.out.print("Enter choice: ");

                String line = sc.nextLine();
                if (line.isEmpty()) {
                    continue;
                }
                choice = Integer.parseInt(line);

                switch (choice) {
                    case 1:
                        Loader addLoader = new Loader("Adding");
                        Thread t1 = new Thread(addLoader);
                        t1.start();

                        System.out.print("Enter Roll No: ");
                        int roll = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Email: ");
                        String email = sc.nextLine();
                        System.out.print("Enter Course: ");
                        String course = sc.nextLine();
                        System.out.print("Enter Marks: ");
                        double marks = Double.parseDouble(sc.nextLine());

                        if (name.isEmpty() || email.isEmpty() || course.isEmpty()) {
                            System.out.println("Fields cannot be empty.");
                            break;
                        }
                        if (marks < 0 || marks > 100) {
                            System.out.println("Marks must be between 0 and 100.");
                            break;
                        }

                        Student s = new Student(roll, name, email, course, marks);
                        manager.addStudent(s);

                        try {
                            t1.join();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        break;

                    case 2:
                        manager.viewAllStudents();
                        break;

                    case 3:
                        System.out.print("Enter roll no to search: ");
                        int searchRoll = Integer.parseInt(sc.nextLine());
                        try {
                            Student found = manager.searchStudent(searchRoll);
                            System.out.println("Student Info:");
                            found.displayDetails();
                        } catch (StudentNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 4:
                        System.out.print("Enter roll no to delete: ");
                        int deleteRoll = Integer.parseInt(sc.nextLine());
                        try {
                            manager.deleteStudent(deleteRoll);
                        } catch (StudentNotFoundException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    case 5:
                        manager.sortByMarksDesc();
                        break;

                    case 6:
                        Loader saveLoader = new Loader("Saving");
                        Thread t2 = new Thread(saveLoader);
                        t2.start();
                        FileUtil.saveStudents(manager.getAllStudents());
                        try {
                            t2.join();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("Saved and exiting.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                }
            } while (choice != 6);
        } catch (NumberFormatException e) {
            System.out.println("Invalid numeric input.");
        } finally {
            sc.close();
        }
    }
}
