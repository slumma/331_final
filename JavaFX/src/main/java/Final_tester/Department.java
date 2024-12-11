package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

import java.util.ArrayList;
import java.util.List;

public class Department {

    private String departmentName;  // arrayList to store the department name
    private ArrayList<Faculty> members;  // arrayList to hold the faculty members
    public int departmentID;
    private static int nextID = 1;

    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.members = new ArrayList<>();
        this.departmentID = nextID++;
    }
    
    public Department(String name, int id){
        this.departmentName = name;
        this.departmentID = id;
        this.members = new ArrayList<>();
    }

    // method to add a faculty member to the department
    public void addFaculty(Faculty newFaculty) {
        if (newFaculty != null && !members.contains(newFaculty)) {
            members.add(newFaculty);
        }
    }

    // Method to remove a faculty member from the department
    public void removeFaculty(Faculty faculty) {
        // Check if the faculty member is in the department
        if (members.contains(faculty)) {
            members.remove(faculty);
            System.out.println("Faculty member " + faculty.getName() + " removed successfully.");
        } else {
            System.out.println("Error: Faculty member " + faculty.getFirstName() + " " + faculty.getLastName() + " not found in the department.");
        }
    }

    // Getter for department name
    public String getDepartmentName() {
        return departmentName;
    }

     public List<Faculty> getFacultyMembers() {
        return members;
    }
     
    public int getDepartmentID(){
        return departmentID;
    }
    
    public void setDepartmentID(int id){
        this.departmentID = id;
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
