package Final;

public class Faculty extends Person {
    private String officeNumber;
    private Department department;  // stores department object
    private String phoneNumber;
    private String rank;

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
        this.officeNumber = officeBuilding + " " + officeNumber;  // Combine office building and number
        this.phoneNumber = phoneNumber;
        this.rank = rank;
    }

    // getters n setters
    public String getOfficeNumber() {
        return officeNumber;
    }

    public void setOfficeNumber(String officeNumber) {
        this.officeNumber = officeNumber;
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
