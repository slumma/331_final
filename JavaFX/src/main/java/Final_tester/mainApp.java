package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

//import java.lang.classfile.ClassFile;  --> error saying its a preview API (?)
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

        // Initialize a list to hold student objects
        ArrayList<Student> students = loadStudentsFromDatabase(); 
        ArrayList<Faculty> faculty = new ArrayList<>();
        ArrayList<Course> courses = new ArrayList<>();
        ArrayList<Department> departments = new ArrayList<>();
        ArrayList<Schedule> schedules = new ArrayList<>();
        



        // main menu -- functions as long as user doesnt choose exit 
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
                    studentManagement(students);
                    break;
                case 2:
                    courseManagement(courses);
                    break;
                case 3:
                    facultyManagement(faculty, departments);
                    break;
                case 4:
                    Schedule newEnrollments = enrollment(students, courses, schedules, currentSemester);

                    schedules.add(newEnrollments);
                    break;
                case 5:
                    reports(courses, students, schedules, faculty, departments);                        
                    break;
            }
        } 
    }
    
    //////////////////////////////////////////
    //                                      //
    //          student management          //
    //                                      //
    //////////////////////////////////////////
    
    public static void studentManagement(ArrayList<Student> students) {
        int choice;

        // student management sub menu 
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

    }

    public static void addStudent(ArrayList<Student> students) {

        // create a new student object
        Student newStudent = new Student();

        // get user input to populate the student object
        System.out.println("Please enter new student's first name:");
        String firstName = in.nextLine();

        System.out.println("Please enter new student's last name:");
        String lastName = in.nextLine();

        System.out.println("Please enter new student's email address:");
        String email = in.nextLine();

        System.out.println("Please enter new student's SSN:");
        String ssn = in.nextLine();

        System.out.println("Please enter new student GPA:");
        double gpa = in.nextDouble();
        in.nextLine();  // Consume the newline after nextDouble()

        System.out.println("Please enter new student's home street address:");
        String homeStreet = in.nextLine();

        System.out.println("Please enter new student's home city:");
        String homeCity = in.nextLine();

        System.out.println("Please enter new student's home state:");
        String homeState = in.nextLine();

        System.out.println("Please enter new student's home ZIP code:");
        String homeZIP = in.nextLine();

        // Emergency contact details
        System.out.println("Please enter emergency contact name:");
        String eContactName = in.nextLine();

        System.out.println("Please enter emergency contact phone:");
        String eContactPhone = in.nextLine();

        System.out.println("Please enter emergency contact street address:");
        String eContactStreet = in.nextLine();

        System.out.println("Please enter emergency contact city:");
        String eContactCity = in.nextLine();

        System.out.println("Please enter emergency contact state:");
        String eContactState = in.nextLine();

        System.out.println("Please enter emergency contact ZIP code:");
        String eContactZIP = in.nextLine();

        // Set values for the new Student object using input from the user
        newStudent = new Student(firstName, lastName, email, ssn, gpa, homeStreet, homeCity, homeState, homeZIP, eContactName, eContactPhone, eContactStreet, eContactCity, eContactState, eContactZIP);

        // Build the SQL query from the student object
        String sqlQuery = "INSERT INTO STUDENT (STUDENTID, STUDENTNAME, SSN, STUDENTHOMEADDRESS, STUDENTEMAILADDRESS, GPA, "
                 + "EMERGENCYCONTACTNAME, EMERGENCYCONTACTPHONE, EMERGENCYCONTACTADDRESS) VALUES ("
                 + newStudent.universityID + ", '" + newStudent.getFirstName() + " " + newStudent.getLastName() + "', '"
                 + newStudent.getSSN() + "', '" + newStudent.getHomeStreet() + ", " + newStudent.getHomeCity() + ", "
                 + newStudent.getHomeState() + " " + newStudent.getHomeZIP() + "', '" + newStudent.getEmail() + "', "
                 + newStudent.getGPA() + ", '" + newStudent.getEContactName() + "', '" + newStudent.getEContactPhone() + "', '"
                 + newStudent.getEContactStreet() + ", " + newStudent.getEContactCity() + ", " + newStudent.getEContactState() + " "
                 + newStudent.getEContactZIP() + "')";


        // call runDBQuery to execute the INSERT query
        runDBQuery(sqlQuery, 'c');
    }
    
    public static void deleteStudent(ArrayList<Student> students) {
        if (students.isEmpty()) { // if the student list has no students, print error and return to sub-menu 
            System.out.println("No students available to delete.");
            return;
        }

        // shows all students that can be deleted
        displayStudentNamesAndIDs(students);
    
        // ask for the universityID of the student to delete
        System.out.print("Enter the University ID of the student to delete: ");
        int universityID = in.nextInt();

        // SQL query to delete the student with the given ID
        String query = "DELETE FROM STUDENT WHERE STUDENTID = " + universityID;

        // Call runDBQuery to execute the DELETE query
        runDBQuery(query, 'd'); // 'd' for DELETE query

    System.out.println("Student with ID " + universityID + " has been deleted.");


    }

    // edits student info
        public static void editStudent(ArrayList<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students available to edit.");
            return;
        }

        // Show all students that can be edited
        displayStudentNamesAndIDs(students);

        // Ask for the universityID of the student to edit
        System.out.print("Enter the University ID of the student to edit: ");
        int universityID = in.nextInt();
        in.nextLine(); // Consume the newline character

        // Find the student with the matching universityID
        Student studentToEdit = null;
        for (Student student : students) {
            if (student.getUniversityID() == universityID) {
                studentToEdit = student;
                break;
            }
        }

        // If student found, edit their details
        if (studentToEdit != null) {
            System.out.println("Editing details for student with University ID: " + universityID);

            // Edit the student's details
            System.out.print("Enter new name (current: " + studentToEdit.getName() + "): ");
            String newName = in.nextLine();
            if (!newName.isEmpty()) {
                studentToEdit.setName(newName);
            }

            System.out.print("Enter new email address (current: " + studentToEdit.getEmail() + "): ");
            String newEmail = in.nextLine();
            if (!newEmail.isEmpty()) {
                studentToEdit.setEmail(newEmail);
            }

            System.out.print("Enter new GPA (current: " + studentToEdit.getGPA() + "): ");
            double newGPA = in.nextDouble();
            studentToEdit.setGPA(newGPA);

            // Edit the student address
            in.nextLine(); // Consume the newline after nextDouble()
            System.out.print("Enter new home street address (current: " + studentToEdit.getHomeStreet() + "): ");
            String newHomeStreet = in.nextLine();
            if (!newHomeStreet.isEmpty()) {
                studentToEdit.setHomeStreet(newHomeStreet);
                studentToEdit.updateHomeAddress();
            }

            System.out.print("Enter new home city (current: " + studentToEdit.getHomeCity() + "): ");
            String newHomeCity = in.nextLine();
            if (!newHomeCity.isEmpty()) {
                studentToEdit.setHomeCity(newHomeCity);
                studentToEdit.updateHomeAddress();
            }

            System.out.print("Enter new home state (current: " + studentToEdit.getHomeState() + "): ");
            String newHomeState = in.nextLine();
            if (!newHomeState.isEmpty()) {
                studentToEdit.setHomeState(newHomeState);
                studentToEdit.updateHomeAddress();
            }

            System.out.print("Enter new home ZIP code (current: " + studentToEdit.getHomeZIP() + "): ");
            String newHomeZIP = in.nextLine();
            if (!newHomeZIP.isEmpty()) {
                studentToEdit.setHomeZIP(newHomeZIP);
                studentToEdit.updateHomeAddress();
            }

            // Edit the student's emergency contact information
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
                studentToEdit.updateEContactAddress();
            }

            System.out.print("Enter new emergency contact city (current: " + studentToEdit.getEContactCity() + "): ");
            String newEContactCity = in.nextLine();
            if (!newEContactCity.isEmpty()) {
                studentToEdit.setEContactCity(newEContactCity);
                studentToEdit.updateEContactAddress();
            }

            System.out.print("Enter new emergency contact state (current: " + studentToEdit.getEContactState() + "): ");
            String newEContactState = in.nextLine();
            if (!newEContactState.isEmpty()) {
                studentToEdit.setEContactState(newEContactState);
                studentToEdit.updateEContactAddress();
            }

            System.out.print("Enter new emergency contact ZIP (current: " + studentToEdit.getEContactZIP() + "): ");
            String newEContactZIP = in.nextLine();
            if (!newEContactZIP.isEmpty()) {
                studentToEdit.setEContactZIP(newEContactZIP);
                studentToEdit.updateEContactAddress();
            }

            // Construct the SQL query
            String sqlQuery = "UPDATE STUDENT SET "
                    + "STUDENTNAME = '" + studentToEdit.getName() + "', "
                    + "STUDENTEMAILADDRESS = '" + studentToEdit.getEmail() + "', "
                    + "GPA = " + studentToEdit.getGPA() + ", "
                    + "STUDENTHOMEADDRESS = '" + studentToEdit.getAddress() + "', "
                    + "EMERGENCYCONTACTNAME = '" + studentToEdit.getEContactName() + "', "
                    + "EMERGENCYCONTACTPHONE = '" + studentToEdit.getEContactPhone() + "', "
                    + "EMERGENCYCONTACTADDRESS = '" + studentToEdit.getEmAddress() + "' "
                    + "WHERE STUDENTID = " + studentToEdit.getUniversityID();

            // Execute the SQL query to update the student's details
            runDBQuery(sqlQuery, 'u'); // 'u' for UPDATE query

            System.out.println("Student details updated successfully.");
        } else {
            System.out.println("No student found with University ID " + universityID + ".");
        }

    }


    
    // method so that dont have to write this code over and over to print all students 
    public static void displayStudentNamesAndIDs(ArrayList<Student> students) {
        generateStudentReport(students);
    }
    
    //////////////////////////////////////////
    //                                      //
    //          course management           //
    //                                      //
    //////////////////////////////////////////


    // 90% of this comes from the studentManagement methods so refer to comments there 
    public static void courseManagement(ArrayList<Course> currentCourses) {
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
            choice = in.nextInt();
            in.nextLine();
    
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
        String creditHours = in.nextLine();
    
        System.out.print("Enter subject: ");
        String subject = in.nextLine();
    
        Faculty faculty = new Faculty(); // assumes a new faculty member is assigned for simplicity, satisfy faculty requirement
    
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

        viewCourses(courses);
    
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
                String creditHours = in.nextLine();
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
            System.out.println("=======================");
            System.out.println("Course ID     : " + course.getCourseID());
            System.out.println("Course Name   : " + course.getCourseName());
            System.out.println("Subject       : " + course.getSubject());
            System.out.println("Faculty       : " + course.getFaculty().getFirstName() + " " + course.getFaculty().getLastName());
            System.out.println("=======================");
        }
        
    }
    

    //////////////////////////////////////////
    //                                      //
    //          fac management              //
    //                                      //
    //////////////////////////////////////////

    // same stuff as the other 2 management methods, just had to include departments to keep track of who is in what department for the reports
    public static void facultyManagement(ArrayList<Faculty> currentMembers, ArrayList<Department> departments) {
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
                    addFaculty(facultyList, departments);
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

    }

    public static void addFaculty(ArrayList<Faculty> facultyList, ArrayList<Department> departments) {
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
    
        // check if department already exists
        Department department = null;
        for (Department dept : departments) {
            if (dept.getDepartmentName().equalsIgnoreCase(departmentName)) {
                department = dept;
                break;
            }
        }
    
        // if department is not found, create it
        if (department == null) {
            System.out.println("Department not found: " + departmentName);
            // create new department and add to list
            department = new Department(departmentName);
            departments.add(department); // adds the new department to the list
            System.out.println("New department created: " + departmentName);
        }
    
        // create the new faculty member and assign it to the department
        Faculty newFaculty = new Faculty(firstName, lastName, email, ssn, department, officeBuilding, officeNumber, phoneNumber, rank);
        facultyList.add(newFaculty); // adds the faculty member to the faculty list
    
        // Add the new faculty member to the department's faculty list
        department.addFaculty(newFaculty); // Ensure Department has an addFaculty method
    
        System.out.println("Faculty member added successfully to the " + departmentName + " department!");
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

    public static Schedule enrollment(ArrayList<Student> students, ArrayList<Course> courses, ArrayList<Schedule> schedules, Semester semester) {
        // display available students
        System.out.println("Available Students:");
        for (Student student : students) {
            System.out.println(student.getFirstName() + " " + student.getLastName() + " - ID: " + student.getUniversityID());
        }
    
        // ask for ID
        System.out.print("\nEnter student ID to enroll: ");
        int studentID = in.nextInt();
        in.nextLine(); // Consume newline
    
        // find student
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
    
        // display courses
        System.out.println("\nAvailable Courses:");
        for (Course course : courses) {
            System.out.println("=====================");
            System.out.println("Course ID    : " + course.getCourseID());
            System.out.println("Course Name  : " + course.getCourseName());
            System.out.println("Professor    : " + course.getFaculty().getFirstName() + " " + course.getFaculty().getLastName());
            System.out.println("=====================");
        }
    
        // ask for the course to enroll in
        System.out.print("\nEnter the course ID to enroll in: ");
        int courseID = in.nextInt();
        in.nextLine(); // Consume newline
    
        // find the selected course
        Course selectedCourse = null;
        for (Course course : courses) {
            if (course.getCourseID() == (courseID)) {
                selectedCourse = course;
                break;
            }
        }
    
        if (selectedCourse == null) {
            System.out.println("Course not found with ID: " + courseID);
            return null; // return null if course not found
        }
    
        // checks if the student already has a schedule
        Schedule studentSchedule = null;
        for (Schedule schedule : schedules) {
            if (schedule.getStudent().equals(student)) {
                studentSchedule = schedule;
                break;
            }
        }
    
        // if no schedule exists, create a new one
        if (studentSchedule == null) {
            studentSchedule = new Schedule(student, semester, new ArrayList<>());
            schedules.add(studentSchedule); // Add the new schedule to the list
        }
    
        // add the selected course to the student's schedule
        studentSchedule.addCourse(selectedCourse);
    
        // confirmation message
        System.out.println(student.getFirstName() + " " + student.getLastName() + " successfully enrolled in " + selectedCourse.getCourseName() + "\n");
    
        // return the updated schedule
        return studentSchedule;
    }
    
    

    //////////////////////////////////////////
    //                                      //
    //              Reports                 //
    //                                      //
    //////////////////////////////////////////

    public static void reports(ArrayList<Course> courses, ArrayList<Student> students, ArrayList<Schedule> schedules, ArrayList<Faculty> faculty, ArrayList<Department> departments) {
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
                    generateDepartmentReport(departments);
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

    // shows all students in database (AKA heap)
    public static void generateStudentReport(ArrayList<Student> students) {
            System.out.println("\n=== Student Report ===");

            if (students.isEmpty()) {
                System.out.println("No students available.");
                return;
            }
            
            // gets all students
            String query = "SELECT * FROM STUDENT";
            
            // executes query 
            runDBQuery(query, 'r');

            // copied from runDBquery but its catered to returning specificly students.
            try {
                if (jsqlResults != null) {
                    while (jsqlResults.next()) {
                        int studentId = jsqlResults.getInt("STUDENTID");
                        String studentName = jsqlResults.getString("STUDENTNAME");
                        String ssn = jsqlResults.getString("SSN");
                        String homeAddress = jsqlResults.getString("STUDENTHOMEADDRESS");
                        String email = jsqlResults.getString("STUDENTEMAILADDRESS");
                        double gpa = jsqlResults.getDouble("GPA");
                        String emergencyContactName = jsqlResults.getString("EMERGENCYCONTACTNAME");
                        String emergencyContactPhone = jsqlResults.getString("EMERGENCYCONTACTPHONE");
                        String emergencyContactAddress = jsqlResults.getString("EMERGENCYCONTACTADDRESS");

                        // Print student data in the desired format
                        System.out.println("=====================");
                        System.out.println(studentName + " (" + studentId + ")");
                        System.out.println("SSN: " + ssn);
                        System.out.println("Home Address: " + homeAddress);
                        System.out.println("Email: " + email);
                        System.out.println("GPA: " + gpa);
                        System.out.println("Emergency Contact Name: " + emergencyContactName);
                        System.out.println("Emergency Contact Phone: " + emergencyContactPhone);
                        System.out.println("Emergency Contact Address: " + emergencyContactAddress);
                        System.out.println("=====================");
                        System.out.println(); // Adds a blank line between each student's information
                    }
                } else {
                    System.out.println("No results returned.");
                }
            } catch (SQLException sqlex) {
                System.out.println("Error processing results: " + sqlex.toString());
            }
    }

    // shows all fac members in heap
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

    // shows all courses 
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

    // shows all members in each department
    public static void generateDepartmentReport(ArrayList<Department> departments) {
        System.out.println("\n=== Department Report ===");
        for (Department department : departments) {
            System.out.println("Department: " + department.getDepartmentName());
            System.out.println("Faculty Members:");
            List<Faculty> facultyList = department.getFacultyMembers(); // gets list from department cdf
            if (facultyList.isEmpty()) { // if empty print error message 
                System.out.println(" - No faculty members.");
            } else {
                for (Faculty faculty : facultyList) { // prints name of each member in specific department
                    System.out.println(" - " + faculty.getFirstName() + " " + faculty.getLastName() + " (" + faculty.getUniversityID() + ")");
                }
            }
            System.out.println("--------------------------");
        }
    }

    // shows a schedule for specific student 
    public static void generateStudentScheduleReport(ArrayList<Student> students, ArrayList<Schedule> schedules) {
        Scanner in = new Scanner(System.in);

        displayStudentNamesAndIDs(students);
    
        // same logic from previous methods 
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
    }
    
    
    public static ArrayList<Student> loadStudentsFromDatabase() {
        ArrayList<Student> students = new ArrayList<>();
        String query = "SELECT * FROM Student";

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser";
            String pass = "javapass";
            oDS = new OracleDataSource();
            oDS.setURL(URL);
            jsqlConn = oDS.getConnection(user, pass);

            // Prepare and execute the query
            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // Iterate through the result set and create Student objects
            while (resultSet.next()) {
                int studentID = resultSet.getInt("studentID");
                String studentName = resultSet.getString("studentName");
                String ssn = resultSet.getString("SSN");
                String homeAddress = resultSet.getString("studentHomeAddress");
                String email = resultSet.getString("studentEmailAddress");
                double gpa = resultSet.getDouble("GPA");
                String emergencyContactName = resultSet.getString("emergencyContactName");
                String emergencyContactPhone = resultSet.getString("emergencyContactPhone");
                String emergencyContactAddress = resultSet.getString("emergencyContactAddress");

                // Create a new Student object
                Student student = new Student(studentID, studentName, email, gpa, homeAddress, emergencyContactName, emergencyContactPhone, emergencyContactAddress, ssn);

                // Add to the ArrayList
                students.add(student);
            }

            // Close resources
            resultSet.close();
            stmt.close();
            jsqlConn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }
    
}
