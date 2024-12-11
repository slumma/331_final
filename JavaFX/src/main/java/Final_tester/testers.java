package Final_tester;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import oracle.jdbc.pool.OracleDataSource;

public class testers {
    // Oracle Database connection details
    public static OracleDataSource oDS;
    public static Connection jsqlConn;
    public static PreparedStatement jsqlStmt;
    public static ResultSet jsqlResults;

    // Faculty information (hardcoded as an example)
    static String facultyID = "F123"; 
    static String firstName = "John";
    static String lastName = "Doe";
    static String email = "john.doe@example.com";
    static String officeBuilding = "Building A";
    static String officeNumber = "101";
    static String phoneNumber = "555-1234";
    static String position = "Professor";
    
    // Create a Department object (can be dynamic based on input)
    static Department department = new Department("Computer Science");  // Example department name

    // Main method to run the program
    public static void main(String[] args) {
        try {
            // Step 1: Check if the department exists in the database
            String checkDeptQuery = "SELECT DEPARTMENTID FROM DEPARTMENT WHERE DEPARTMENTNAME = '" + department.getDepartmentName() + "'";
            runDBQuery(checkDeptQuery, 'r');  // Execute query to check if department exists

            // Step 2: If department does not exist, insert the new department
            if (jsqlResults == null || !jsqlResults.next()) {
                // Insert new department
                String insertDeptQuery = "INSERT INTO DEPARTMENT (DEPARTMENTNAME) VALUES ('" + department.getDepartmentName() + "')";
                runDBQuery(insertDeptQuery, 'c');  // Insert new department

                // Get the department ID of the newly inserted department
                String getDeptIdQuery = "SELECT DEPARTMENTID FROM DEPARTMENT WHERE DEPARTMENTNAME = '" + department.getDepartmentName() + "'";
                runDBQuery(getDeptIdQuery, 'r');  // Fetch the department ID

                // Assuming departmentID is the first column in the result set
                if (jsqlResults != null && jsqlResults.next()) {
                    // Use the department's own getDepartmentID() method to fetch the ID
                    department.getDepartmentID();  // This will give the department's dynamic ID
                }
            } else {
                // Department exists, get its ID and assign it dynamically
                department.getDepartmentID();  // This retrieves the department ID if it exists
            }

            // Step 3: Check if departmentID is valid (non-null and non-zero)
            if (department.getDepartmentID() == -1) {
                System.out.println("Error: Department ID is invalid.");
                return;  // Exit if department ID is not valid
            }

            // Step 4: Insert the faculty member with the valid department ID
            String sqlQuery = "INSERT INTO FACULTY (FACULTYID, FACULTYNAME, FACULTYEMAILADDRESS, DEPARTMENTID, BUILDINGNAME, OFFICENUMBER, FACULTYPHONENUMBER, POSITION) "
                            + "VALUES ('" + facultyID + "', '" + firstName + " " + lastName + "', '" + email + "', "
                            + department.getDepartmentID() + ", '" + officeBuilding + "', '" + officeNumber + "', '" + phoneNumber + "', '" + position + "')";


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to execute database queries (left unchanged as per your request)
    public static void runDBQuery(String query, char queryType) {
        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser"; // Replace with actual user
            String pass = "javapass"; // Replace with actual password

            oDS = new OracleDataSource();
            oDS.setURL(URL);

            jsqlConn = oDS.getConnection(user, pass);
            jsqlStmt = jsqlConn.prepareStatement(query);

            if (queryType == 'r') {
                jsqlResults = jsqlStmt.executeQuery();
            } else {
                jsqlStmt.executeUpdate();
            }
        } catch (SQLException sqlex) {
            System.out.println(sqlex.toString());
        }
    }
}
