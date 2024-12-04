package Final;

import java.sql.*;
import java.util.Scanner;
import oracle.jdbc.pool.*;
import oracle.jdbc.*;
import java.util.*;

public class mainApp {

    public static OracleDataSource oDS;
    public static Connection jsqlConn;
    public static PreparedStatement jsqlStmt;
    public static ResultSet jsqlResults;
    public static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {



        /*int menuChoice = 0;
        
        while (menuChoice != 5)
        {
            System.out.println("1 - Create new Inventory Item");
            System.out.println("2 - Update Existing Inventory Item");
            System.out.println("3 - Delete Existing Inventory Item");
            System.out.println("4 - Print Inventory");
            System.out.println("5 - Exit Application");
            System.out.println("===================================");
            System.out.println("Selection?: ");
            menuChoice = in.nextInt();
            
            in.nextLine(); // Consume errant new line character
            
            switch (menuChoice)
            {
                case 1: newItem();
                    break;
                case 2: 
                    break;
                case 3: 
                    break;
                case 4: printInventory();
                    break;
            }
        } */
    }
    
    public static void newItem()
    {
        String name, desc;
        double cost;
        int qty;
        
        String sqlQuery = "INSERT INTO INVENTORY (ITEMNAME,ITEMDESCRIPTION,ITEMCOST,ITEMQTY) VALUES (";
        
        System.out.println("Please enter new item name:");
        name = in.nextLine();
        System.out.println("Please enter new item description:");
        desc = in.nextLine();
        System.out.println("Please enter new item cost:");
        cost = in.nextDouble();
        System.out.println("Please enter new item quantity:");
        qty = in.nextInt();
        
        sqlQuery += "'" + name + "','" + desc + "'," + cost + "," + qty + ")";
        
        runDBQuery(sqlQuery, 'c');
        
        
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
