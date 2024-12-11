package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

public class Faculty extends Person {
    private String office;
    private Department department;  // stores department object
    private String phoneNumber;
    private String rank;
    private String officeBuilding;
    private String officeNumber; 

    public Faculty() {
        // Initialize with default values or leave them as null/empty
        super();  // Call the parent constructor for Person
        this.officeNumber = "N/A";
        this.department = new Department("N/A");  // Create a default department
        this.phoneNumber = "N/A";
        this.rank = "N/A";
    }

    public Faculty(String firstName, String lastName, String email, String ssn,
                   Department department, String officeBuilding, String officeNumber, 
                   String phoneNumber, String rank) {
        super(firstName, lastName, email, ssn);  // Call the superclass constructor
        this.department = department;  // Store the full Department object
        this.office = officeBuilding + " " + officeNumber;  // Combine office building and number
        this.phoneNumber = phoneNumber;
        this.rank = rank;
        this.officeBuilding = officeBuilding;
        this.officeNumber = officeNumber;
    }
    
    public Faculty(int facultyID, String name, String email, Department department,
                   String officeBuilding, String officeNumber, String phoneNumber, String rank) {
        this.name = name;
        this.emailAddress = email;
        this.universityID = facultyID;
        this.department = department;  
        this.office = officeBuilding + " " + officeNumber;  
        this.phoneNumber = phoneNumber;
        this.rank = rank;
        this.officeBuilding = officeBuilding;
        this.officeNumber = officeNumber;
    }
    
    public Faculty(String name, String email){
        this.name = name;
        this.emailAddress = email;
    }

    // getters n setters
    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
    }
    
    public String getOfficeBuilding(){
        return officeBuilding;
    }
    
    public String getOffice(){
        return office;
    }

    public Department getDepartment() {
        return department;  // Return the full Department object
    }

    public void setDepartment(Department department) {
        this.department = department;  // Directly assign the Department object
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    // faculty report 
    @Override
    public String toString() {
        String departmentName = "No Department";  // Default department name
        if (department != null) {
            departmentName = department.getDepartmentName();  // If department exists, get its name
        }

        return "===================== Faculty Details =====================\n" +
               "ID              : " + getUniversityID() + "\n" +
               "First Name      : " + getFirstName() + "\n" +
               "Last Name       : " + getLastName() + "\n" +
               "Department      : " + departmentName + "\n" +
               "Office          : " + officeNumber + "\n" +
               "Phone           : " + phoneNumber + "\n" +
               "Position        : " + rank + "\n" +
               "===========================================================\n";
    }

}
