package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/


import java.util.Scanner;

public class Student extends Person {
    
    private double GPA;
    
    // address 
    private String homeStreet;
    private String homeCity;
    private String homeState;
    private String homeZIP;
    
    private String address = homeStreet + ", " + homeCity + ", " + homeState + ", " + homeZIP;
    
    // emergency contact 
    private String eContactName;
    private String eContactPhone;
    private String eContactStreet;
    private String eContactCity;
    private String eContactState;
    private String eContactZIP;
    
    private String eAddress = eContactStreet + ", " + eContactCity + ", " + eContactState + ", " + eContactZIP;
    
    public Student(){
        super();
        this.GPA = 0.0d;
        this.homeStreet = "N/A";
        this.homeCity = "N/A";
        this.homeState = "N/A";
        this.homeZIP = "N/A";
        
        this.eContactName = "N/A";
        this.eContactPhone = "N/A";
        this.eContactStreet = "N/A";
        this.eContactCity = "N/A";
        this.eContactState = "N/A";
        this.eContactZIP = "N/A";
    }
    
    public Student(String f, String l, String e, String ssn, double GPA, String homeStreet, String homeCity, String homeState, String homeZIP, String eContactName, 
            String eContactPhone, String eContactStreet, String eContactCity, String eContactState, String eContactZIP){
        
        super(f, l, e, ssn);
        this.setGPA(GPA);
        
        this.homeStreet = homeStreet;
        this.homeCity = homeCity;
        this.homeState = homeState;
        this.homeZIP = homeZIP;
        
        this.eContactName = eContactName;
        this.eContactPhone = eContactPhone;
        this.eContactStreet = eContactStreet;
        this.eContactCity = eContactCity;
        this.eContactState = eContactState;
        this.eContactZIP = eContactZIP;
    }
    
    // to be read in from db
    public Student(int studentID, String name, String email, double gpa,
                   String homeAddress, String emergencyContactName, String emergencyContactPhone,
                   String emergencyContactAddress, String ssn) {
        
        this.universityID = studentID;
        this.name = name;
        this.emailAddress = email;
        this.setGPA(gpa);
        this.address = homeAddress;
        this.eContactName = emergencyContactName;
        this.eContactPhone = emergencyContactPhone;
        this.eAddress = emergencyContactAddress;
        this.SSN = ssn;
    }
    
    public Student (int id, String fname, String lname){
        this.universityID = id;
        this.firstName = fname;
        this.lastName = lname;
    } 
    
    public Student (int id, String name){
        this.universityID = id;
        this.name = name;
    } 

    
    // setter
    public void setGPA(double g){
        Scanner scanner = new Scanner(System.in);
        boolean isValid;

        // chekcs if the gpa is over 0 and less than 4 ==> both parts must be in it to be valid 
        do {
            isValid = (g <= 4.0d) && (g >= 0.0d);

            if (!isValid) {
                System.out.println("Error: GPA must be between 0.0 and 4.0. Please enter a valid GPA:");
                g = scanner.nextDouble(); // reprompt the user 
            }
        } while (!isValid);

        // set gpa
        this.GPA = g;
    }
    
    // getters n setters
    
    public double getGPA() {
        return GPA;
    }
    
    public String getHomeStreet() {
        return homeStreet;
    }


    public String getHomeCity() {
        return homeCity;
    }

    public String getHomeState() {
        return homeState;
    }

    public String getHomeZIP() {
        return homeZIP;
    }


    public String getEContactName() {
        return eContactName;
    }

    public void setEContactName(String eContactName) {
        this.eContactName = eContactName;
    }

    public String getEContactPhone() {
        return eContactPhone;
    }

    public void setEContactPhone(String eContactPhone) {
        this.eContactPhone = eContactPhone;
    }

    public String getEContactStreet() {
        return eContactStreet;
    }

    public String getEContactCity() {
        return eContactCity;
    }


    public String getEContactState() {
        return eContactState;
    }


    public String getEContactZIP() {
        return eContactZIP;
    }


    public String getSSN() {
        return SSN; 
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getEmAddress(){
        return eAddress;
    }
    
    public void setHomeStreet(String homeStreet) {
    this.homeStreet = homeStreet;
    updateHomeAddress();
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
        updateHomeAddress();
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
        updateHomeAddress();
    }

    public void setHomeZIP(String homeZIP) {
        this.homeZIP = homeZIP;
        updateHomeAddress();
    }

    

    public void setEContactStreet(String eContactStreet) {
        this.eContactStreet = eContactStreet;
        updateEContactAddress();
    }

    public void setEContactCity(String eContactCity) {
        this.eContactCity = eContactCity;
        updateEContactAddress();
    }

    public void setEContactState(String eContactState) {
        this.eContactState = eContactState;
        updateEContactAddress();
    }

    public void setEContactZIP(String eContactZIP) {
        this.eContactZIP = eContactZIP;
        updateEContactAddress();
    }


    
    
    // fixes the issue of the database not being updated FINALLY FOUND IT
    public void updateEContactAddress() {
    this.eAddress = eContactStreet + ", " + eContactCity + ", " + eContactState + " " + eContactZIP;
}

    public void updateHomeAddress() {
        this.address = homeStreet + ", " + homeCity + ", " + homeState + " " + homeZIP;
    }

    // report of student 
    @Override
    public String toString() {
        return "======================== Student Details ========================\n" +
               "First Name      : " + this.firstName + "\n" +
               "Last Name       : " + this.lastName + "\n" +
               "Email           : " + this.emailAddress + "\n" +
               "SSN             : " + this.SSN + "\n" +
               "GPA             : " + this.GPA + "\n" +
               "-------------------- Home Address --------------------\n" +
               "Street          : " + this.homeStreet + "\n" +
               "City            : " + this.homeCity + "\n" +
               "State           : " + this.homeState + "\n" +
               "ZIP             : " + this.homeZIP + "\n" +
               "---------------- Emergency Contact ------------------\n" +
               "Name            : " + this.eContactName + "\n" +
               "Phone           : " + this.eContactPhone + "\n" +
               "Street          : " + this.eContactStreet + "\n" +
               "City            : " + this.eContactCity + "\n" +
               "State           : " + this.eContactState + "\n" +
               "ZIP             : " + this.eContactZIP + "\n" +
               "==============================================================";

    }

    
}
