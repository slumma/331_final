package Final;

import java.util.ArrayList;

public class Department {

    private String departmentName;  // arrayList to store the department name
    private ArrayList<Faculty> members;  // arrayList to hold the faculty members

    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.members = new ArrayList<>();
    }

    // method to add a faculty member to the department
    public void addFaculty(Faculty faculty) {
        // check if the faculty member is already in the department ==> same logic from schedule 
        if (members.contains(faculty)) {
            System.out.println("Error: Faculty member " + faculty.getFirstName() + " " + faculty.getLastName() + " is already in the department.");
        } else {
            members.add(faculty);
            System.out.println("Faculty member " + faculty.getFirstName() + " " + faculty.getLastName() + " added successfully.");
        }
    }

    // Method to remove a faculty member from the department
    public void removeFaculty(Faculty faculty) {
        // Check if the faculty member is in the department
        if (members.contains(faculty)) {
            members.remove(faculty);
            System.out.println("Faculty member " + faculty.getFirstName() + " " + faculty.getLastName() + " removed successfully.");
        } else {
            System.out.println("Error: Faculty member " + faculty.getFirstName() + " " + faculty.getLastName() + " not found in the department.");
        }
    }

    // Getter for department name
    public String getDepartmentName() {
        return departmentName;
    }

    // Method to return a string representation of the Department
    @Override
    public String toString() {
        String departmentDetails = "===================== Department Details =====================\n" +
                                    "Department Name : " + departmentName + "\n" +
                                    "------------------------------------------------------------\n" +
                                    "Faculty Members:\n";

        if (members.isEmpty()) {
            departmentDetails += "No faculty members in the department.\n";
        } else {
            for (Faculty faculty : members) {
                departmentDetails += " - " + faculty.getFirstName() + " " + faculty.getLastName() + 
                                     " (ID: " + faculty.getUniversityID() + ")\n";
            }
        }

        departmentDetails += "============================================================\n";
        return departmentDetails;
    }
}
