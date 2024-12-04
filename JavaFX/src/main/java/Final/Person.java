package Final;
import java.util.*;

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
        this.setName("First Name", f);
        this.setName("Last Name", l);
        this.setEmail(e);
        this.SSN = ssn; // should have some way of verifying the SSN, but its out of scope for project
        
        this.universityID = nextID++;  // Assign unique ID and increment it
    }
    
    // fieldName = first OR last, name = user entered name
    private void setName(String fieldName, String name){
        Scanner scanner = new Scanner(System.in); 
        boolean hasDigit;

        do {
            hasDigit = false;

            // checks if the input contains any digits
            for (int i = 0; i < name.length(); i++) {
                if (Character.isDigit(name.charAt(i))) {
                    hasDigit = true;
                    break;
                }
            }

            if (hasDigit) {
                System.out.println("Error: " + fieldName + " cannot contain numbers. Please enter a valid name:");
                name = scanner.nextLine(); // reprompt user 
            }
        } while (hasDigit);

        // assign the valid name to the appropriate field
        if (fieldName.equals("First Name")) {
            this.firstName = name;
        } else if (fieldName.equals("Last Name")) {
            this.lastName = name;
        }
    }
    
    private void setEmail(String email) {
        Scanner scanner = new Scanner(System.in);
        boolean isValid;

        // checks if the email the user provided has an '@' and '.' ==> both parts must be in it to be valid 
        do {
            isValid = email.contains("@") && email.contains(".");

            if (!isValid) {
                System.out.println("Error: Email must contain '@' and '.' symbols. Please enter a valid email:");
                email = scanner.nextLine(); // reprompt the user 
            }
        } while (!isValid);

        // set email
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
}

