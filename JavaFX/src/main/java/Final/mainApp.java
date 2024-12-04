package Final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;

public class mainApp {

    public static OracleDataSource oDS;
    public static Connection jsqlConn;
    public static PreparedStatement jsqlStmt;
    public static ResultSet jsqlResults;
    public static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {



        int menuChoice = 0;
        
        while (menuChoice != 6)
        {
            System.out.println("======SCC Management Software======");
            System.out.println("1 - Student Management");
            System.out.println("2 - Course Management");
            System.out.println("3 - Faculty Management");
            System.out.println("4 - Enrollment");
            System.out.println("5 - Reports");
            System.out.println("6 - Exit Application");
            System.out.println("===================================");
            System.out.println("Enter Selection: ");
            menuChoice = in.nextInt();
            
            in.nextLine(); // Consume errant new line character
            
            switch (menuChoice)
            {
                case 1: 
                    studentManagement();
                    break;
                case 2:
                    courseManagement();
                    break;
                case 3:
                    facultyManagement();
                    break;
                case 4:
                    enrollment();
                    break;
                case 5:
                    reports();                        
                    break;
            }
        } 
    }
    
    // student management
    public static void studentManagement() {
        ArrayList<Student> students = new ArrayList<>();
        int choice;

        do {
            System.out.println("\n====== Student Management ======");
            System.out.println("1 - Add a Student");
            System.out.println("2 - Delete a Student");
            System.out.println("3 - Edit a Student");
            System.out.println("4 - Return to Main Menu");
            System.out.println("================================");
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            in.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(students);
                    break;
                case 2:
                    deleteStudent(students);
                    break;
                case 3:
                    editStudent(students);
                    break;
                case 4:
                    System.out.println("Returning to the main menu...\n");
                    return;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
    }

    public static void addStudent(ArrayList<Student> students) {
        System.out.print("Enter First Name: ");
        String firstName = in.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Enter Email: ");
        String email = in.nextLine();
        System.out.print("Enter SSN: ");
        String ssn = in.nextLine();
        System.out.print("Enter GPA: ");
        double gpa = in.nextDouble();
        in.nextLine(); // Consume newline
    
        // Home address
        System.out.print("Enter Home Street: ");
        String homeStreet = in.nextLine();
        System.out.print("Enter Home City: ");
        String homeCity = in.nextLine();
        System.out.print("Enter Home State: ");
        String homeState = in.nextLine();
        System.out.print("Enter Home ZIP: ");
        String homeZIP = in.nextLine();
    
        // Emergency contact
        System.out.print("Enter Emergency Contact Name: ");
        String eContactName = in.nextLine();
        System.out.print("Enter Emergency Contact Phone: ");
        String eContactPhone = in.nextLine();
        System.out.print("Enter Emergency Contact Street: ");
        String eContactStreet = in.nextLine();
        System.out.print("Enter Emergency Contact City: ");
        String eContactCity = in.nextLine();
        System.out.print("Enter Emergency Contact State: ");
        String eContactState = in.nextLine();
        System.out.print("Enter Emergency Contact ZIP: ");
        String eContactZIP = in.nextLine();
    
        // Add the student to the list
        students.add(new Student(firstName, lastName, email, ssn, gpa, homeStreet, homeCity, homeState, homeZIP,
                eContactName, eContactPhone, eContactStreet, eContactCity, eContactState, eContactZIP));
        System.out.println("Student added successfully!");
    }
    
    public static void deleteStudent(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students available to delete.");
            return;
        }

        // shows all students that can be deleted
        displayStudentNamesAndIDs(students);
    
        // Ask for the universityID of the student to delete
        System.out.print("Enter the University ID of the student to delete: ");
        int universityID = in.nextInt();
        
        // Find the student with the matching University ID
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getUniversityID() == universityID) {
                studentToRemove = student;
                break;
            }
        }
    
        // If student is found, remove them
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            System.out.println("Student with University ID " + universityID + " has been deleted.");
        } else {
            System.out.println("No student found with University ID " + universityID + ".");
        }
    }

    public static void editStudent(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students available to edit.");
            return;
        }
        

        // shows all students that can be edited 
        displayStudentNamesAndIDs(students);

        // ask for the universityID of the student to edit
        System.out.print("Enter the University ID of the student to edit: ");
        int universityID = in.nextInt();
        in.nextLine(); // Consume the newline character
    
        // find the student with the matching universityID
        Student studentToEdit = null;
        for (Student student : students) {
            if (student.getUniversityID() == universityID) {
                studentToEdit = student;
                break;
            }
        }
    
        // ff student found, edit their details
        if (studentToEdit != null) {
            System.out.println("Editing details for student with University ID: " + universityID);
    
            // edit the student's details
            System.out.print("Enter new first name (current: " + studentToEdit.getFirstName() + "): ");
            String newFirstName = in.nextLine();
            if (!newFirstName.isEmpty()) {
                studentToEdit.setFirstName(newFirstName);
            }
    
            System.out.print("Enter new last name (current: " + studentToEdit.getLastName() + "): ");
            String newLastName = in.nextLine();
            if (!newLastName.isEmpty()) {
                studentToEdit.setLastName(newLastName);
            }
    
            System.out.print("Enter new email address (current: " + studentToEdit.getEmail() + "): ");
            String newEmail = in.nextLine();
            if (!newEmail.isEmpty()) {
                studentToEdit.setEmail(newEmail);
            }
    
            System.out.print("Enter new GPA (current: " + studentToEdit.getGPA() + "): ");
            double newGPA = in.nextDouble();
            studentToEdit.setGPA(newGPA);
    
            // edit the student address
            in.nextLine(); 
            System.out.print("Enter new home street address (current: " + studentToEdit.getHomeStreet() + "): ");
            String newHomeStreet = in.nextLine();
            if (!newHomeStreet.isEmpty()) {
                studentToEdit.setHomeStreet(newHomeStreet);
            }
    
            System.out.print("Enter new home city (current: " + studentToEdit.getHomeCity() + "): ");
            String newHomeCity = in.nextLine();
            if (!newHomeCity.isEmpty()) {
                studentToEdit.setHomeCity(newHomeCity);
            }
    
            System.out.print("Enter new home state (current: " + studentToEdit.getHomeState() + "): ");
            String newHomeState = in.nextLine();
            if (!newHomeState.isEmpty()) {
                studentToEdit.setHomeState(newHomeState);
            }
    
            System.out.print("Enter new home ZIP code (current: " + studentToEdit.getHomeZIP() + "): ");
            String newHomeZIP = in.nextLine();
            if (!newHomeZIP.isEmpty()) {
                studentToEdit.setHomeZIP(newHomeZIP);
            }
    
            // edit the student's emergency contact information
            System.out.print("Enter new emergency contact name (current: " + studentToEdit.getEContactName() + "): ");
            String newEContactName = in.nextLine();
            if (!newEContactName.isEmpty()) {
                studentToEdit.setEContactName(newEContactName);
            }
    
            System.out.print("Enter new emergency contact phone (current: " + studentToEdit.getEContactPhone() + "): ");
            String newEContactPhone = in.nextLine();
            if (!newEContactPhone.isEmpty()) {
                studentToEdit.setEContactPhone(newEContactPhone);
            }
    
            System.out.print("Enter new emergency contact street (current: " + studentToEdit.getEContactStreet() + "): ");
            String newEContactStreet = in.nextLine();
            if (!newEContactStreet.isEmpty()) {
                studentToEdit.setEContactStreet(newEContactStreet);
            }
    
            System.out.print("Enter new emergency contact city (current: " + studentToEdit.getEContactCity() + "): ");
            String newEContactCity = in.nextLine();
            if (!newEContactCity.isEmpty()) {
                studentToEdit.setEContactCity(newEContactCity);
            }
    
            System.out.print("Enter new emergency contact state (current: " + studentToEdit.getEContactState() + "): ");
            String newEContactState = in.nextLine();
            if (!newEContactState.isEmpty()) {
                studentToEdit.setEContactState(newEContactState);
            }
    
            System.out.print("Enter new emergency contact ZIP (current: " + studentToEdit.getEContactZIP() + "): ");
            String newEContactZIP = in.nextLine();
            if (!newEContactZIP.isEmpty()) {
                studentToEdit.setEContactZIP(newEContactZIP);
            }
    
            System.out.println("Student details updated successfully.");
        } else {
            System.out.println("No student found with University ID " + universityID + ".");
        }
    }
    
    public static void displayStudentNamesAndIDs(ArrayList<Student> students) {
        // check if the list is empty
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return; // exits method
        }
        
        // iterate over the list of students and print their name and ID
        System.out.println("===== Student List =====");
        for (Student student : students) {
            System.out.println("=== Student ===");
            System.out.println("Name   : " + student.getFirstName() + " " + student.getLastName());
            System.out.println("ID     : " + student.getUniversityID());
            System.out.println("=====================");
        }
        System.out.println("=======================");
    }
    
    //////////////////////////////////////////
    //          course management           //
    //////////////////////////////////////////

    public static void courseManagement()
    {
        
    }

    //////////////////////////////////////////
    //          fac management              //
    //////////////////////////////////////////

    public static void facultyManagement()
    {
        
    }
    

    //////////////////////////////////////////
    //          Enrollment           //
    //////////////////////////////////////////

    public static void enrollment()
    {
        
    }

    public static void reports()
    {
        
    }
    
    

    /* use for Part 3 
    public static void runDBQuery(String query, char queryType)
    {
        // queryType - Using the C.R.U.D. acronym
        // 'r' - SELECT
        // 'c', 'u', or 'd' - UPDATE, INSERT, DELETE
        
        try
        {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser"; // From setup instructions
            String pass = "javapass"; // From setup instructions

            oDS = new OracleDataSource();
            oDS.setURL(URL);
            
            jsqlConn = oDS.getConnection(user, pass);
            jsqlStmt = jsqlConn.prepareStatement(
                    query, 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY);
            
            if (queryType == 'r')
                jsqlResults = jsqlStmt.executeQuery();
            else
                jsqlStmt.executeUpdate();
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.toString());
        }
    } */
    
    
}
