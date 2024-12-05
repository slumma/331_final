package Final;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Scanner;

import oracle.jdbc.pool.OracleDataSource;

public class mainApp {

    public static OracleDataSource oDS;
    public static Connection jsqlConn;
    public static PreparedStatement jsqlStmt;
    public static ResultSet jsqlResults;
    public static Scanner in = new Scanner(System.in);
    
    public static void main(String[] args) {

        // initialize variables 
        int menuChoice = 0;
        Semester currentSemester = new Semester("Fall", 2024, 15); // current semester irl

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Faculty> faculty = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Department> departments = new ArrayList<>();
        ArrayList<Schedule> schedules = new ArrayList<>();
        
        // tests /////////////////////////////////////////////

        // Create some departments
        departments.add(new Department("Computer Information Systems"));
        departments.add(new Department("Computer Science"));
        departments.add(new Department("Systems Thinking"));

        // Mock Faculty
        faculty.add(new Faculty("Jeremy", "Ezell", "jeremy@example.com", "123-45-6789", 
                departments.get(0), "College of Business", "5522", "555-1234", "Professor"));
        faculty.add(new Faculty("Dymtro", "Babik", "babik@example.com", "987-65-4321", 
                departments.get(1), "Showker", "1102", "555-5678", "Associate Professor"));
        faculty.add(new Faculty("Sean", "Lough", "seanLough@example.com", "111-11-1111", 
                departments.get(2), "Hartman Hall", "2021", "555-8765", "Professor"));

        // Mock Courses
        courses.add(new Course("CIS", "221", "Mon, Wed, Fri", "9:00 AM", "10:00 AM", 3, "Introduction to Computer Science", faculty.get(2)));
        courses.add(new Course("CIS", "331", "Mon, Wed, Fri", "11:00 AM", "12:00 PM", 3, "Advanced Programming", faculty.get(0)));
        courses.add(new Course("COB", "304", "Tue, Thu", "10:00 AM", "11:30 AM", 4, "Enterprise Architecture", faculty.get(1)));

        // Mock Students (using the new constructor with all parameters)
        students.add(new Student("John", "Doe", "john.doe@example.com", "123-45-6789", 3.7, 
                "123 Elm St", "Hometown", "VA", "12345", "Mary Doe", "555-1111", 
                "456 Oak St", "Hometown", "VA", "12345"));

        students.add(new Student("Jane", "Smith", "jane.smith@example.com", "987-65-4321", 3.9, 
                "789 Pine St", "Cityville", "NY", "67890", "John Smith", "555-2222", 
                "123 Maple St", "Cityville", "NY", "67890"));

        students.add(new Student("Bob", "Johnson", "bob.johnson@example.com", "111-11-1111", 2.8, 
                "321 Birch St", "Smalltown", "CA", "11223", "Alice Johnson", "555-3333", 
                "654 Cedar St", "Smalltown", "CA", "11223"));

        students.add(new Student("Alice", "Williams", "alice.williams@example.com", "222-22-2222", 3.5, 
                "654 Oak St", "Bigcity", "TX", "44556", "Robert Williams", "555-4444", 
                "123 Pine St", "Bigcity", "TX", "44556"));

        students.add(new Student("Eve", "Brown", "eve.brown@example.com", "333-33-3333", 4.0, 
                "987 Maple St", "Metropolis", "IL", "77889", "Linda Brown", "555-5555", 
                "987 Elm St", "Metropolis", "IL", "77889"));

        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        while (menuChoice != 6)
        {
            System.out.println("======SCC Management Software======");
            System.out.println("1 - Student Management");
            System.out.println("2 - Course Management");
            System.out.println("3 - Faculty Management");
            System.out.println("4 - Enrollment");
            System.out.println("5 - Reports");
            System.out.println("6 - Exit Application");
            System.out.println("===================================");
            System.out.println("Enter your choice: ");
            menuChoice = in.nextInt();
            
            in.nextLine(); // Consume errant new line character
            
            switch (menuChoice)
            {
                case 1: 
                    ArrayList<Student> newStudents = studentManagement(students);

                    students.addAll(newStudents);
                    break;
                case 2:
                    ArrayList<Course> newCourses = courseManagement(courses);

                    courses.addAll(newCourses);
                    break;
                case 3:
                    ArrayList<Faculty> newFaculty = facultyManagement(faculty);

                    faculty.addAll(newFaculty);
                    break;
                case 4:
                    Schedule newEnrollments = enrollment(students, courses, currentSemester);

                    schedules.add(newEnrollments);
                    break;
                case 5:
                    reports(courses, students, schedules, faculty);                        
                    break;
            }
        } 
    }
    
    //////////////////////////////////////////
    //                                      //
    //          course management           //
    //                                      //
    //////////////////////////////////////////
    
    public static ArrayList<Student> studentManagement(ArrayList<Student> currentStudents) {
        ArrayList<Student> students = new ArrayList<>();
        students.addAll(currentStudents);
        int choice;

        do {
            System.out.println("\n====== Student Management ======");
            System.out.println("1 - Add a Student");
            System.out.println("2 - Delete a Student");
            System.out.println("3 - Edit a Student");
            System.out.println("4 - View all students");
            System.out.println("5 - Exit to main menu");
            System.out.println("================================");
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            in.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addStudent(students);
                    break;
                case 2:
                    deleteStudent(students);
                    break;
                case 3:
                    editStudent(students);
                    break;
                case 4:
                    displayStudentNamesAndIDs(students);
                    break;
                case 5:
                    System.out.println("Returning to the main menu...\n");
                    choice = 5;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);

        return students;

    }

    public static void addStudent(ArrayList<Student> students) {
        System.out.print("Enter First Name: ");
        String firstName = in.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Enter Email: ");
        String email = in.nextLine();
        System.out.print("Enter SSN: ");
        String ssn = in.nextLine();
        System.out.print("Enter GPA: ");
        double gpa = in.nextDouble();
        in.nextLine(); // Consume newline
    
        // Home address
        System.out.print("Enter Home Street: ");
        String homeStreet = in.nextLine();
        System.out.print("Enter Home City: ");
        String homeCity = in.nextLine();
        System.out.print("Enter Home State: ");
        String homeState = in.nextLine();
        System.out.print("Enter Home ZIP: ");
        String homeZIP = in.nextLine();
    
        // Emergency contact
        System.out.print("Enter Emergency Contact Name: ");
        String eContactName = in.nextLine();
        System.out.print("Enter Emergency Contact Phone: ");
        String eContactPhone = in.nextLine();
        System.out.print("Enter Emergency Contact Street: ");
        String eContactStreet = in.nextLine();
        System.out.print("Enter Emergency Contact City: ");
        String eContactCity = in.nextLine();
        System.out.print("Enter Emergency Contact State: ");
        String eContactState = in.nextLine();
        System.out.print("Enter Emergency Contact ZIP: ");
        String eContactZIP = in.nextLine();
    
        // Add the student to the list
        students.add(new Student(firstName, lastName, email, ssn, gpa, homeStreet, homeCity, homeState, homeZIP,
                eContactName, eContactPhone, eContactStreet, eContactCity, eContactState, eContactZIP));
        System.out.println("Student added successfully!");
    }
    
    public static void deleteStudent(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students available to delete.");
            return;
        }

        // shows all students that can be deleted
        displayStudentNamesAndIDs(students);
    
        // Ask for the universityID of the student to delete
        System.out.print("Enter the University ID of the student to delete: ");
        int universityID = in.nextInt();
        
        // Find the student with the matching University ID
        Student studentToRemove = null;
        for (Student student : students) {
            if (student.getUniversityID() == universityID) {
                studentToRemove = student;
                break;
            }
        }
    
        // If student is found, remove them
        if (studentToRemove != null) {
            students.remove(studentToRemove);
            System.out.println("Student with University ID " + universityID + " has been deleted.");
        } else {
            System.out.println("No student found with University ID " + universityID + ".");
        }
    }

    public static void editStudent(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students available to edit.");
            return;
        }
        

        // shows all students that can be edited 
        displayStudentNamesAndIDs(students);

        // ask for the universityID of the student to edit
        System.out.print("Enter the University ID of the student to edit: ");
        int universityID = in.nextInt();
        in.nextLine(); // Consume the newline character
    
        // find the student with the matching universityID
        Student studentToEdit = null;
        for (Student student : students) {
            if (student.getUniversityID() == universityID) {
                studentToEdit = student;
                break;
            }
        }
    
        // ff student found, edit their details
        if (studentToEdit != null) {
            System.out.println("Editing details for student with University ID: " + universityID);
    
            // edit the student's details
            System.out.print("Enter new first name (current: " + studentToEdit.getFirstName() + "): ");
            String newFirstName = in.nextLine();
            if (!newFirstName.isEmpty()) {
                studentToEdit.setFirstName(newFirstName);
            }
    
            System.out.print("Enter new last name (current: " + studentToEdit.getLastName() + "): ");
            String newLastName = in.nextLine();
            if (!newLastName.isEmpty()) {
                studentToEdit.setLastName(newLastName);
            }
    
            System.out.print("Enter new email address (current: " + studentToEdit.getEmail() + "): ");
            String newEmail = in.nextLine();
            if (!newEmail.isEmpty()) {
                studentToEdit.setEmail(newEmail);
            }
    
            System.out.print("Enter new GPA (current: " + studentToEdit.getGPA() + "): ");
            double newGPA = in.nextDouble();
            studentToEdit.setGPA(newGPA);
    
            // edit the student address
            in.nextLine(); 
            System.out.print("Enter new home street address (current: " + studentToEdit.getHomeStreet() + "): ");
            String newHomeStreet = in.nextLine();
            if (!newHomeStreet.isEmpty()) {
                studentToEdit.setHomeStreet(newHomeStreet);
            }
    
            System.out.print("Enter new home city (current: " + studentToEdit.getHomeCity() + "): ");
            String newHomeCity = in.nextLine();
            if (!newHomeCity.isEmpty()) {
                studentToEdit.setHomeCity(newHomeCity);
            }
    
            System.out.print("Enter new home state (current: " + studentToEdit.getHomeState() + "): ");
            String newHomeState = in.nextLine();
            if (!newHomeState.isEmpty()) {
                studentToEdit.setHomeState(newHomeState);
            }
    
            System.out.print("Enter new home ZIP code (current: " + studentToEdit.getHomeZIP() + "): ");
            String newHomeZIP = in.nextLine();
            if (!newHomeZIP.isEmpty()) {
                studentToEdit.setHomeZIP(newHomeZIP);
            }
    
            // edit the student's emergency contact information
            System.out.print("Enter new emergency contact name (current: " + studentToEdit.getEContactName() + "): ");
            String newEContactName = in.nextLine();
            if (!newEContactName.isEmpty()) {
                studentToEdit.setEContactName(newEContactName);
            }
    
            System.out.print("Enter new emergency contact phone (current: " + studentToEdit.getEContactPhone() + "): ");
            String newEContactPhone = in.nextLine();
            if (!newEContactPhone.isEmpty()) {
                studentToEdit.setEContactPhone(newEContactPhone);
            }
    
            System.out.print("Enter new emergency contact street (current: " + studentToEdit.getEContactStreet() + "): ");
            String newEContactStreet = in.nextLine();
            if (!newEContactStreet.isEmpty()) {
                studentToEdit.setEContactStreet(newEContactStreet);
            }
    
            System.out.print("Enter new emergency contact city (current: " + studentToEdit.getEContactCity() + "): ");
            String newEContactCity = in.nextLine();
            if (!newEContactCity.isEmpty()) {
                studentToEdit.setEContactCity(newEContactCity);
            }
    
            System.out.print("Enter new emergency contact state (current: " + studentToEdit.getEContactState() + "): ");
            String newEContactState = in.nextLine();
            if (!newEContactState.isEmpty()) {
                studentToEdit.setEContactState(newEContactState);
            }
    
            System.out.print("Enter new emergency contact ZIP (current: " + studentToEdit.getEContactZIP() + "): ");
            String newEContactZIP = in.nextLine();
            if (!newEContactZIP.isEmpty()) {
                studentToEdit.setEContactZIP(newEContactZIP);
            }
    
            System.out.println("Student details updated successfully.");
        } else {
            System.out.println("No student found with University ID " + universityID + ".");
        }
    }
    
    public static void displayStudentNamesAndIDs(ArrayList<Student> students) {
        // check if the list is empty
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return; // exits method
        }
        
        // iterate over the list of students and print their name and ID
        System.out.println("===== Student List =====");
        for (Student student : students) {
            System.out.println("=====================");
            System.out.println("Name   : " + student.getFirstName() + " " + student.getLastName());
            System.out.println("ID     : " + student.getUniversityID());
            System.out.println("=====================");
        }
        System.out.println("=======================");
    }
    
    //////////////////////////////////////////
    //                                      //
    //          course management           //
    //                                      //
    //////////////////////////////////////////

    public static ArrayList<Course> courseManagement(ArrayList<Course> currentCourses) {
        ArrayList<Course> courses = new ArrayList<>();
        courses.addAll(currentCourses);
        int choice;
    
        do {
            System.out.println("\n======= Course Management =======");
            System.out.println("1 - Add a Course");
            System.out.println("2 - Delete a Course");
            System.out.println("3 - Edit a Course");
            System.out.println("4 - View All Courses");
            System.out.println("5 - Return to Main Menu");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");
            choice = readIntInput();
    
            switch (choice) {
                case 1:
                    addCourse(courses);
                    break;
                case 2:
                    deleteCourse(courses);
                    break;
                case 3:
                    editCourse(courses);
                    break;
                case 4:
                    viewCourses(courses);
                    break;
                case 5:
                    System.out.println("Returning to the main menu...\n");
                    choice = 5;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);

        return courses;
    }
    
    public static void addCourse(ArrayList<Course> courses) {
        System.out.println("\n=== Add a New Course ===");
    
        System.out.print("Enter course prefix (e.g., CSC): ");
        String prefix = in.nextLine();
    
        System.out.print("Enter course number: ");
        String number = in.nextLine();
    
        System.out.print("Enter course days (e.g., Mon/Wed/Fri): ");
        String days = in.nextLine();
    
        System.out.print("Enter start time (e.g., 9:00 AM): ");
        String startTime = in.nextLine();
    
        System.out.print("Enter end time (e.g., 10:00 AM): ");
        String endTime = in.nextLine();
    
        System.out.print("Enter credit hours: ");
        int creditHours = readIntInput();
    
        System.out.print("Enter subject: ");
        String subject = in.nextLine();
    
        Faculty faculty = new Faculty(); // Assume a new faculty member is assigned for simplicity
    
        courses.add(new Course(prefix, number, days, startTime, endTime, creditHours, subject, faculty));
        System.out.println("Course added successfully!");
    }
    
    public static void deleteCourse(ArrayList<Course> courses) {
        System.out.println("\n=== Delete a Course ===");
        if (courses.isEmpty()) {
            System.out.println("No courses available to delete.");
            return;
        }
    
        System.out.print("Enter course number to delete: ");
        String courseNumber = in.nextLine();
    
        boolean found = false;
        for (Course course : courses) {
            if (courseNumber.equals(course.getCourseNumber())) {
                courses.remove(course);
                System.out.println("Course removed successfully!");
                found = true;
                break;
            }
        }
    
        if (!found) {
            System.out.println("Course not found.");
        }
    }
    
    public static void editCourse(ArrayList<Course> courses) {
        System.out.println("\n=== Edit a Course ===");
        if (courses.isEmpty()) {
            System.out.println("No courses available to edit.");
            return;
        }
    
        System.out.print("Enter course number to edit: ");
        String courseNumber = in.nextLine();
    
        for (Course course : courses) {
            if (courseNumber.equals(course.getCourseNumber())) {
                System.out.println("Editing course: " + course.getCourseName());
    
                System.out.print("Enter new days of week: ");
                String days = in.nextLine();
                course.setDaysOfWeek(days);
    
                System.out.print("Enter new start time: ");
                String startTime = in.nextLine();
                course.setStartTime(startTime);
    
                System.out.print("Enter new end time: ");
                String endTime = in.nextLine();
                course.setEndTime(endTime);
    
                System.out.print("Enter new credit hours: ");
                int creditHours = readIntInput();
                course.setCreditHours(creditHours);
    
                System.out.print("Enter new subject: ");
                String subject = in.nextLine();
                course.setSubject(subject);
    
                System.out.println("Course updated successfully!");
                return;
            }
        }
    
        System.out.println("Course not found.");
    }
    
    public static void viewCourses(ArrayList<Course> courses) {
        System.out.println("\n=== All Courses ===");
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
    
        for (Course course : courses) {
            System.out.println(course);
        }
    }

    public static int readIntInput() {
        System.out.print("Enter a number: ");
        String input = in.nextLine();
        boolean isValid = false;
        int result = 0;
    
        // Validate input
        while (!isValid) {
            isValid = true; // Assume valid input initially
            result = 0; // Reset result for each attempt
    
            for (int i = 0; i < input.length(); i++) {
                char currentChar = input.charAt(i);
    
                // Ensure each character is a digit
                if (!Character.isDigit(currentChar)) {
                    isValid = false;
                    break;
                }
    
                // Convert character to digit and calculate the integer value
                result = result * 10 + (currentChar - '0');
            }
    
            if (!isValid || input.isEmpty()) {
                System.out.print("Invalid input. Please enter a valid number: ");
                input = in.nextLine();
            }
        }
    
        // At this point, input is guaranteed to be valid
        return result;
    }
    
    

    //////////////////////////////////////////
    //                                      //
    //          fac management              //
    //                                      //
    //////////////////////////////////////////


    public static ArrayList<Faculty> facultyManagement(ArrayList<Faculty> currentMembers) {
        ArrayList<Faculty> facultyList = new ArrayList<>();
        facultyList.addAll(currentMembers);
        int choice;

        do {
            System.out.println("\n====== Faculty Management ======");
            System.out.println("1 - Add a Faculty Member");
            System.out.println("2 - Delete a Faculty Member");
            System.out.println("3 - Edit a Faculty Member");
            System.out.println("4 - View All Faculty Members");
            System.out.println("5 - Return to Main Menu");
            System.out.println("================================");
            System.out.print("Enter your choice: ");
            choice = in.nextInt();
            in.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addFaculty(facultyList);
                    break;
                case 2:
                    deleteFaculty(facultyList);
                    break;
                case 3:
                    editFaculty(facultyList);
                    break;
                case 4:
                    viewFaculty(facultyList);
                    break;
                case 5:
                    System.out.println("Returning to the main menu...\n");
                    choice = 5;
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);

        return facultyList;
    }

    public static void addFaculty(ArrayList<Faculty> facultyList) {
        System.out.println("\n=== Add Faculty Member ===");
        System.out.print("Enter First Name: ");
        String firstName = in.nextLine();
        System.out.print("Enter Last Name: ");
        String lastName = in.nextLine();
        System.out.print("Enter Email: ");
        String email = in.nextLine();
        System.out.print("Enter SSN: ");
        String ssn = in.nextLine();
        System.out.print("Enter Department: ");
        String departmentName = in.nextLine();
        System.out.print("Enter Office Building: ");
        String officeBuilding = in.nextLine();
        System.out.print("Enter Office Number: ");
        String officeNumber = in.nextLine();
        System.out.print("Enter Phone Number: ");
        String phoneNumber = in.nextLine();
        System.out.print("Enter Position: ");
        String rank = in.nextLine();

        Department department = new Department(departmentName);
        Faculty newFaculty = new Faculty(firstName, lastName, email, ssn, department, officeBuilding, officeNumber, phoneNumber, rank);
        facultyList.add(newFaculty);

        System.out.println("Faculty member added successfully!");
    }

    public static void deleteFaculty(ArrayList<Faculty> facultyList) {
        System.out.println("\n=== Delete Faculty Member ===");
        System.out.print("Enter Faculty University ID to Delete: ");
        int universityID = in.nextInt();
        in.nextLine(); // Consume newline

        Faculty facultyToRemove = null;
        for (Faculty faculty : facultyList) {
            if (faculty.getUniversityID() == universityID) {
                facultyToRemove = faculty;
                break;
            }
        }

        if (facultyToRemove != null) {
            facultyList.remove(facultyToRemove);
            System.out.println("Faculty member removed successfully!");
        } else {
            System.out.println("No faculty member found with that ID.");
        }
    }

    public static void editFaculty(ArrayList<Faculty> facultyList) {
        System.out.println("\n=== Edit Faculty Member ===");

        viewFaculty(facultyList);

        System.out.print("Enter Faculty University ID to Edit: ");
        int universityID = in.nextInt();
        in.nextLine(); // Consume newline

        Faculty facultyToEdit = null;
        for (Faculty faculty : facultyList) {
            if (faculty.getUniversityID() == universityID) {
                facultyToEdit = faculty;
                break;
            }
        }

        if (facultyToEdit == null) {
            System.out.println("No faculty member found with that ID.");
            return;
        }

        System.out.println("Editing Faculty Member: " + facultyToEdit.getFirstName() + " " + facultyToEdit.getLastName());
        System.out.print("Enter New First Name (or press Enter to keep current): ");
        String newFirstName = in.nextLine();
        if (!newFirstName.isEmpty()) facultyToEdit.firstName = newFirstName;

        System.out.print("Enter New Last Name (or press Enter to keep current): ");
        String newLastName = in.nextLine();
        if (!newLastName.isEmpty()) facultyToEdit.lastName = newLastName;

        System.out.print("Enter New Email (or press Enter to keep current): ");
        String newEmail = in.nextLine();
        if (!newEmail.isEmpty()) facultyToEdit.setEmail(newEmail);

        System.out.print("Enter New Phone Number (or press Enter to keep current): ");
        String newPhoneNumber = in.nextLine();
        if (!newPhoneNumber.isEmpty()) facultyToEdit.setPhoneNumber(newPhoneNumber);

        System.out.print("Enter New Rank (or press Enter to keep current): ");
        String newRank = in.nextLine();
        if (!newRank.isEmpty()) facultyToEdit.setRank(newRank);

        System.out.println("Faculty member updated successfully!");
    }

    public static void viewFaculty(ArrayList<Faculty> facultyList) {
        System.out.println("\n=== Faculty Members ===");
        for (Faculty faculty : facultyList) {
            System.out.println(faculty);
        }
        if (facultyList.isEmpty()) {
            System.out.println("No faculty members available.");
        }
    }

    //////////////////////////////////////////
    //                                      //
    //              Enrollment              //
    //                                      //
    //////////////////////////////////////////

    public static Schedule enrollment(ArrayList<Student> students, ArrayList<Course> courses, Semester semester) {
        // Display available students
        System.out.println("Available Students:");
        for (Student student : students) {
            System.out.println(student.getFirstName() + " " + student.getLastName() + " - ID: " + student.getUniversityID());
        }
    
        // Ask for student ID
        System.out.print("\nEnter student ID to enroll: ");
        int studentID = in.nextInt();
        in.nextLine(); // Consume newline
    
        // Find student by ID
        Student student = null;
        for (Student s : students) {
            if (s.getUniversityID() == studentID) {
                student = s;
                break;
            }
        }
    
        if (student == null) {
            System.out.println("Student not found with ID: " + studentID);
            return null; // Return null if student not found
        }
    
        // Display available courses
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("=====================");
            System.out.println("Course ID    : " + course.getCourseID());
            System.out.println("Course Name  : " + course.getCourseName());
            System.out.println("Professor    : " + course.getFaculty().getFirstName() + " " + course.getFaculty().getLastName());
            System.out.println("=====================");
        }

        // Ask for the course to enroll in
        System.out.print("\nEnter the course ID to enroll in: ");
        int courseID = in.nextInt();
        in.nextLine(); // Consume newline
    
        // Find the selected course
        Course selectedCourse = null;
        for (Course course : courses) {
            if (course.getCourseID() == (courseID)) {
                selectedCourse = course;
                break;
            }
        }
    
        if (selectedCourse == null) {
            System.out.println("Course not found with ID: " + courseID);
            return null; // Return null if course not found
        }
    
        // Create or update the student's schedule
        Schedule schedule = new Schedule(student, semester, new ArrayList<>());
        schedule.addCourse(selectedCourse);
    
        // Confirmation message
        System.out.println(student.getFirstName() + " " + student.getLastName() + " successfully enrolled in " + selectedCourse.getCourseName() + "\n");
    
        // Return the student's schedule
        return schedule;
    }
    

    //////////////////////////////////////////
    //                                      //
    //              Reports                 //
    //                                      //
    //////////////////////////////////////////

    public static void reports(ArrayList<Course> courses, ArrayList<Student> students, ArrayList<Schedule> schedules, ArrayList<Faculty> faculty) {
        Scanner in = new Scanner(System.in);
        int choice;

        do {
            // Display the report menu
            System.out.println("=== Report Generation Menu ===");
            System.out.println("1. Generate Student Report");
            System.out.println("2. Generate Faculty Report");
            System.out.println("3. Generate Course Report");
            System.out.println("4. Generate Department Report");
            System.out.println("5. Generate Student Schedule Report");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            
            // Get user input for the menu option
            choice = in.nextInt();

            switch (choice) {
                case 1:
                    generateStudentReport(students);
                    break;
                case 2:
                    generateFacultyReport(faculty);
                    break;
                case 3:
                    generateCourseReport(courses);
                    break;
                case 4:
                    generateDepartmentReport(faculty);
                    break;
                case 5:
                    generateStudentScheduleReport(students, schedules);
                    break;
                case 6:
                    System.out.println("Exiting the report menu.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        } while (choice != 6);  // Repeat until user exits
    }

    public static void generateStudentReport(ArrayList<Student> students) {
        System.out.println("\n=== Student Report ===");
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (Student student : students) {
            System.out.println("Name: " + student.getFirstName() + " " + student.getLastName());
            System.out.println("University ID: " + student.getUniversityID());
            System.out.println("Email: " + student.getEmail());
            System.out.println("GPA: " + student.getGPA());
            System.out.println("--------------------------");
        }
    }

    public static void generateFacultyReport(ArrayList<Faculty> faculty) {
        System.out.println("\n=== Faculty Report ===");
        if (faculty.isEmpty()) {
            System.out.println("No faculty available.");
            return;
        }
        for (Faculty member : faculty) {
            System.out.println("Name: " + member.getFirstName() + " " + member.getLastName());
            System.out.println("Department: " + member.getDepartment().getDepartmentName());
            System.out.println("Office Location: " + member.getOfficeNumber());
            System.out.println("Email: " + member.getEmail());
            System.out.println("--------------------------");
        }
    }

    public static void generateCourseReport(ArrayList<Course> courses) {
        System.out.println("\n=== Course Report ===");
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getCourseID());
            System.out.println("Course Name: " + course.getCourseName());
            System.out.println("Instructor: " + course.getFaculty().getFirstName() + " " + course.getFaculty().getLastName());
            System.out.println("Schedule: " + course.getDaysOfWeek() + " " + course.getStartTime() + " - " + course.getEndTime());
            System.out.println("--------------------------");
        }
    }

    public static void generateDepartmentReport(ArrayList<Faculty> faculty) {
        System.out.println("\n=== Department Report ===");
    
        // Use ArrayLists to track department names and corresponding faculty members
        ArrayList<String> departmentNames = new ArrayList<>();
        ArrayList<ArrayList<String>> facultyByDepartment = new ArrayList<>();
    
        // Organize faculty by department
        for (Faculty member : faculty) {
            String department = member.getDepartment().getDepartmentName();
            int index = departmentNames.indexOf(department);
    
            if (index == -1) {
                // New department
                departmentNames.add(department);
                ArrayList<String> newFacultyList = new ArrayList<>();
                newFacultyList.add(member.getFirstName() + " " + member.getLastName());
                facultyByDepartment.add(newFacultyList);
            } else {
                // Existing department
                facultyByDepartment.get(index).add(member.getFirstName() + " " + member.getLastName());
            }
        }
    
        // Display the report
        for (int i = 0; i < departmentNames.size(); i++) {
            System.out.println("Department: " + departmentNames.get(i));
            System.out.println("Faculty Members:");
            for (String facultyMember : facultyByDepartment.get(i)) {
                System.out.println(" - " + facultyMember);
            }
            System.out.println("--------------------------");
        }
    }
    
    public static void generateStudentScheduleReport(ArrayList<Student> students, ArrayList<Schedule> schedules) {
        Scanner in = new Scanner(System.in);

        displayStudentNamesAndIDs(students);
    
        System.out.print("\nEnter the University ID of the student: ");
        int studentID = in.nextInt();
        in.nextLine(); // Consume newline
    
        Student selectedStudent = null;
        for (Student student : students) {
            if (student.getUniversityID() == studentID) {
                selectedStudent = student;
                break;
            }
        }
    
        if (selectedStudent == null) {
            System.out.println("Student with ID " + studentID + " not found.");
            return;
        }
    
        // Find the student's schedule
        for (Schedule schedule : schedules) {
            if (schedule.getStudent().equals(selectedStudent)) {
                System.out.println("\n=== Schedule Report ===");
                System.out.println(schedule);
                return;
            }
        }
    
        System.out.println("No schedule found for student " + selectedStudent.getFirstName() + " " + selectedStudent.getLastName() + ".");
    }
    
    
    /* use for Part 3 
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
    } */
    
    
}
