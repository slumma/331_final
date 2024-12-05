package Final;

import java.util.ArrayList;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

public class testers {

    public static void main(String[] args) {
        // Example Departments
        Department csDepartment = new Department("Computer Science");
        Department mathDepartment = new Department("Mathematics");

        // Example Faculty
        Faculty faculty1 = new Faculty("Alice", "Johnson", "ajohnson@university.edu", "987-65-4321",
                csDepartment, "Science Building", "Room 205", "555-6789", "Professor");
        Faculty faculty2 = new Faculty("Bob", "Smith", "bsmith@university.edu", "123-45-6789",
                csDepartment, "Math Building", "Room 101", "555-2345", "Professor");

        // Adding Faculty to Departments
        csDepartment.addFaculty(faculty1);
        csDepartment.addFaculty(faculty2);

        // Example Students
        Student student1 = new Student("John", "Doe", "jdoe@university.edu", "123-45-6789", 3.5,
                "123 Main St", "Harrisonburg", "VA", "22801", "Jane Doe", "555-1234",
                "456 Elm St", "Harrisonburg", "VA", "22801");
        Student student2 = new Student("Emily", "Davis", "edavis@university.edu", "987-65-4321", 3.8,
                "789 Oak St", "Harrisonburg", "VA", "22801", "Mark Davis", "555-4321",
                "321 Pine St", "Harrisonburg", "VA", "22801");

        // Example Semester
        Semester semester = new Semester("Fall", 2024, 16);

        // Example Courses
        Course course1 = new Course("CS", "101", "Mon, Wed, Fri", "9:00 AM", "10:15 AM", "3", 
                                    "Computer Science", faculty1);
        Course course2 = new Course("MATH", "201", "Tue, Thu", "10:30 AM", "11:45 AM", "4", 
                                    "Computer Science", faculty2);

        // Example Schedules
        Schedule schedule1 = new Schedule(student1, semester, new ArrayList<>());
        Schedule schedule2 = new Schedule(student2, semester, new ArrayList<>());

        // Adding courses to schedules
        schedule1.addCourse(course1);
        schedule2.addCourse(course2);

        // Print examples to verify
        System.out.println("Departments:");
        System.out.println(csDepartment);

        System.out.println("\nFaculty:");
        System.out.println(faculty1);
        System.out.println(faculty2);

        System.out.println("\nStudents:");
        System.out.println(student1);
        System.out.println(student2);

        System.out.println("\nSchedules:");
        System.out.println(schedule1);
        System.out.println(schedule2);
    }
    
}
