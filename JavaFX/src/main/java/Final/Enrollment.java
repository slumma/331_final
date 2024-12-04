
package Final;

import java.util.ArrayList;

public class Enrollment {
    private Semester semester;
    private ArrayList<Student> students;

    public Enrollment(Semester semester, ArrayList<Student> students) {
        this.semester = semester;
        this.students = students;
    }

    public Enrollment() {
        this.semester = new Semester(); 
        this.students = new ArrayList<>();
    }

    // method to add a student to the enrollment
    public void addStudent(Student student) {
        // check if the student is already in the list
        if (students.contains(student)) {
            System.out.println("Error: Student " + student.getFirstName() + " " + student.getLastName() + " is already enrolled.");
        } else {
            students.add(student);
            System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " added successfully.");
        }
    }

    // method to remove a student from the enrollment
    public void removeStudent(Student student) {
        // check if the student is in the list
        if (students.contains(student)) {
            students.remove(student);
            System.out.println("Student " + student.getFirstName() + " " + student.getLastName() + " removed successfully.");
        } else {
            System.out.println("Error: Student " + student.getFirstName() + " " + student.getLastName() + " not found.");
        }
    }

    // enrollment report
    public String toString() {
        
        // starter string 
        String enrollmentDetails = "Enrollment for Semester: " + semester.getPeriod() + " " + semester.getYear() + "\n";
        enrollmentDetails += "Students Enrolled: \n";

        // checks if the list of students is empty ==> if true, print message saying none are enrolled
        if (students.isEmpty()) {
            enrollmentDetails += "No students enrolled.\n";
        } else {
            for (Student student : students) {
                enrollmentDetails += " - " + student.getFirstName() + " (" + student.getUniversityID() + ") " + student.getLastName() + " (ID: " + student.getUniversityID() + ")\n";
            }
        }

        return enrollmentDetails;
    }
}

