package Final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    public static void studentManagement()
    {
        
    }

    public static void courseManagement()
    {
        
    }

    public static void facultyManagement()
    {
        
    }
    
    public static void enrollment()
    {
        
    }

    public static void reports()
    {
        
    }
    public static void printInventory()
    {
        String sqlQuery = "SELECT * FROM INVENTORY";
        runDBQuery(sqlQuery, 'r');
        
        try
        {
            while (jsqlResults.next())
            {
            System.out.println(
                    String.format(
                            "%-15s%-15s$%-9.2f%-7d",
                            jsqlResults.getString(1),
                            jsqlResults.getString(2),
                            jsqlResults.getDouble(3),
                            jsqlResults.getInt(4)));
            
            }
        }
        catch (SQLException sqlex)
        {
            System.out.println(sqlex.toString());
        }
        
    }
    
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
    } // End of runDBQuery() method
    
    
}
