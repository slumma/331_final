package Final_tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import oracle.jdbc.pool.OracleDataSource;
import static testDB.InventoryDBConsole.jsqlConn;
import static testDB.InventoryDBConsole.jsqlStmt;
import static testDB.InventoryDBConsole.oDS;


public class testers {
    // Oracle Database connection details
    public static Scanner keyboardIn = new Scanner(System.in);
    public static OracleDataSource oDS;
    public static Connection jsqlConn;
    public static PreparedStatement jsqlStmt;
    public static ResultSet jsqlResults;


    // Method to run the database query based on query type
    public static void runDBQuery(String query, char queryType) {
        // queryType - Using the C.R.U.D. acronym
        // 'r' - SELECT
        // 'c', 'u', or 'd' - UPDATE, INSERT, DELETE

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser"; // From setup instructions
            String pass = "javapass"; // From setup instructions

            oDS = new OracleDataSource();
            oDS.setURL(URL);
            
            // Establish connection
            jsqlConn = oDS.getConnection(user, pass);
            jsqlStmt = jsqlConn.prepareStatement(
                    query, 
                    ResultSet.TYPE_SCROLL_INSENSITIVE, 
                    ResultSet.CONCUR_READ_ONLY);
            
            // Execute query based on the queryType
            if (queryType == 'r') {
                jsqlResults = jsqlStmt.executeQuery();
                // Process the result for SELECT queries
                while (jsqlResults.next()) {
                    int studentId = jsqlResults.getInt("STUDENTID");
                    String studentName = jsqlResults.getString("STUDENTNAME");
                    String ssn = jsqlResults.getString("SSN");
                    String homeAddress = jsqlResults.getString("STUDENTHOMEADDRESS");
                    String email = jsqlResults.getString("STUDENTEMAILADDRESS");
                    double gpa = jsqlResults.getDouble("GPA");
                    String emergencyContactName = jsqlResults.getString("EMERGENCYCONTACTNAME");
                    String emergencyContactPhone = jsqlResults.getString("EMERGENCYCONTACTPHONE");
                    String emergencyContactAddress = jsqlResults.getString("EMERGENCYCONTACTADDRESS");

                    // Print student data in the desired format
                    System.out.println("==========");
                    System.out.println(studentName + " (" + studentId + ")");
                    System.out.println("SSN: " + ssn);
                    System.out.println("Home Address: " + homeAddress);
                    System.out.println("Email: " + email);
                    System.out.println("GPA: " + gpa);
                    System.out.println("Emergency Contact Name: " + emergencyContactName);
                    System.out.println("Emergency Contact Phone: " + emergencyContactPhone);
                    System.out.println("Emergency Contact Address: " + emergencyContactAddress);
                    System.out.println("==========");
                    System.out.println(); // Adds a blank line between each student's information
                }
            } else {
                jsqlStmt.executeUpdate(); // For other query types like 'c', 'u', 'd'
            }
        } catch (SQLException sqlex) {
            System.out.println(sqlex.toString());
        }
    }

    // Method to show all students
    public static void showAllStudents() {
        String query = "SELECT * FROM STUDENT";
        runDBQuery(query, 'r'); // 'r' for SELECT query
    }
    
    public static void deleteStudent() {
    // Ask for the student ID to delete
    System.out.println("Please enter the Student ID to delete:");
    int studentId = keyboardIn.nextInt();
    keyboardIn.nextLine(); // Consume the newline character

    // SQL query to delete the student with the given ID
    String query = "DELETE FROM STUDENT WHERE STUDENTID = " + studentId;

    // Call runDBQuery to execute the DELETE query
    runDBQuery(query, 'd'); // 'd' for DELETE query

    System.out.println("Student with ID " + studentId + " has been deleted.");
}

    
    public static void newStudent() {
        // Create a new student object
        Student newStudent = new Student();

        // Get user input to populate the student object
        System.out.println("Please enter new student's first name:");
        String firstName = keyboardIn.nextLine();

        System.out.println("Please enter new student's last name:");
        String lastName = keyboardIn.nextLine();

        System.out.println("Please enter new student's email address:");
        String email = keyboardIn.nextLine();

        System.out.println("Please enter new student's SSN:");
        String ssn = keyboardIn.nextLine();

        System.out.println("Please enter new student GPA:");
        double gpa = keyboardIn.nextDouble();
        keyboardIn.nextLine();  // Consume the newline after nextDouble()

        // Address details
        System.out.println("Please enter new student's home street address:");
        String homeStreet = keyboardIn.nextLine();

        System.out.println("Please enter new student's home city:");
        String homeCity = keyboardIn.nextLine();

        System.out.println("Please enter new student's home state:");
        String homeState = keyboardIn.nextLine();

        System.out.println("Please enter new student's home ZIP code:");
        String homeZIP = keyboardIn.nextLine();

        // Emergency contact details
        System.out.println("Please enter emergency contact name:");
        String eContactName = keyboardIn.nextLine();

        System.out.println("Please enter emergency contact phone:");
        String eContactPhone = keyboardIn.nextLine();

        System.out.println("Please enter emergency contact street address:");
        String eContactStreet = keyboardIn.nextLine();

        System.out.println("Please enter emergency contact city:");
        String eContactCity = keyboardIn.nextLine();

        System.out.println("Please enter emergency contact state:");
        String eContactState = keyboardIn.nextLine();

        System.out.println("Please enter emergency contact ZIP code:");
        String eContactZIP = keyboardIn.nextLine();

        // Set values for the new Student object using input from the user
        newStudent = new Student(firstName, lastName, email, ssn, gpa, homeStreet, homeCity, homeState, homeZIP, eContactName, eContactPhone, eContactStreet, eContactCity, eContactState, eContactZIP);

        // Build the SQL query from the student object
        String sqlQuery = "INSERT INTO STUDENT (STUDENTID, STUDENTNAME, SSN, STUDENTHOMEADDRESS, STUDENTEMAILADDRESS, GPA, "
                 + "EMERGENCYCONTACTNAME, EMERGENCYCONTACTPHONE, EMERGENCYCONTACTADDRESS) VALUES ("
                 + newStudent.universityID + ", '" + newStudent.getFirstName() + " " + newStudent.getLastName() + "', '"
                 + newStudent.getSSN() + "', '" + newStudent.getHomeStreet() + ", " + newStudent.getHomeCity() + ", "
                 + newStudent.getHomeState() + " " + newStudent.getHomeZIP() + "', '" + newStudent.getEmail() + "', "
                 + newStudent.getGPA() + ", '" + newStudent.getEContactName() + "', '" + newStudent.getEContactPhone() + "', '"
                 + newStudent.getEContactStreet() + ", " + newStudent.getEContactCity() + ", " + newStudent.getEContactState() + " "
                 + newStudent.getEContactZIP() + "')";


        // Call runDBQuery to execute the INSERT query
        runDBQuery(sqlQuery, 'c');
}

    



    // Main method to run the program
    public static void main(String[] args) {
        newStudent();
        showAllStudents(); // Calling the method to display all students
    }
}
