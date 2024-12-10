package Final_tester;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import oracle.jdbc.pool.OracleDataSource;


public class testers {
    // Oracle Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1"; // Updated URL
    private static final String USER = "javauser"; // Updated username
    private static final String PASS = "javapass"; // Updated password

    private static Connection jsqlConn = null;
    private static PreparedStatement jsqlStmt = null;
    private static ResultSet jsqlResults = null;

    // Method to run the database query based on query type
    public static void runDBQuery(String query, char queryType) {
        // queryType - Using the C.R.U.D. acronym
        // 'r' - SELECT
        // 'c', 'u', or 'd' - UPDATE, INSERT, DELETE

        try {
            // Establish connection
            OracleDataSource oDS = new OracleDataSource();
            oDS.setURL(DB_URL);
            jsqlConn = oDS.getConnection(USER, PASS);
            
            // Prepare statement
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
                    System.out.println("Student (" + studentId + ")");
                    System.out.println("Name: " + studentName);
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
        } finally {
            try {
                if (jsqlResults != null) jsqlResults.close();
                if (jsqlStmt != null) jsqlStmt.close();
                if (jsqlConn != null) jsqlConn.close();
            } catch (SQLException sqlex) {
                System.out.println(sqlex.toString());
            }
        }
    }

    // Method to show all students
    public void showAllStudents() {
        String query = "SELECT * FROM STUDENT";
        runDBQuery(query, 'r'); // 'r' for SELECT query
    }

    // Main method to run the program
    public static void main(String[] args) {
        testers sm = new testers();
        sm.showAllStudents(); // Calling the method to display all students
    }
}
