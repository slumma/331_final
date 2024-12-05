package Final;
import java.util.Scanner;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

public class Person {

    // general info about person -- will be used for both students and professors
    public String firstName;
    public String lastName;
    protected String emailAddress;
    protected String SSN;
    public int universityID;
    private static int nextID = 1000;  // increases each time to generate new ID
    
    public Person(){
        this.firstName = "N/A";
        this.lastName = "N/A";
        this.emailAddress = "N/A";
        this.SSN = "N/A";
        
        this.universityID = nextID++;  // Assign unique ID and increment it
    }
    
    public Person(String f, String l, String e, String ssn){
        this.setFirstName(f);
        this.setLastName(l);
        this.setEmail(e);
        this.SSN = ssn; // should have some way of verifying the SSN, but its out of scope for project
        
        this.universityID = nextID++;  // Assign unique ID and increment it
    }
    
    // setter for first name
    public void setFirstName(String firstName) {
        Scanner in = new Scanner(System.in);
        boolean hasDigit;

        do {
            hasDigit = false;

            // checks if the input contains any digits
            for (int i = 0; i < firstName.length(); i++) {
                if (Character.isDigit(firstName.charAt(i))) {
                    hasDigit = true;
                    break;
                }
            }

            if (hasDigit) {
                System.out.println("Error: First Name cannot contain numbers. Please enter a valid name:");
                firstName = in.nextLine(); // reprompt user 
            }
        } while (hasDigit);

        // assign the valid first name
        this.firstName = firstName;
    }

    // setter for last name
    public void setLastName(String lastName) {
        Scanner in = new Scanner(System.in);
        boolean hasDigit;

        do {
            hasDigit = false;

            // checks if the input contains any digits
            for (int i = 0; i < lastName.length(); i++) {
                if (Character.isDigit(lastName.charAt(i))) {
                    hasDigit = true;
                    break;
                }
            }

            if (hasDigit) {
                System.out.println("Error: Last Name cannot contain numbers. Please enter a valid name:");
                lastName = in.nextLine(); // reprompt user 
            }
        } while (hasDigit);

        // assign the valid last name
        this.lastName = lastName;
    }
    
    public void   setEmail(String email) {
        this.emailAddress = email; 
    }

    // Getter methods for first and last name
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public int getUniversityID(){
        return universityID;
    }

    public String getEmail(){
        return emailAddress;
    }

}
