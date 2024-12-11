package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

/*
Note to self (to-do):
    - need to add, edit, delete for faculty members --> probably will be the toughest one
    - need to set up enrollment using the db
    - finish up the reports (student and cours already done)
    - test for errors
*/

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
        ArrayList<Student> students = loadStudentsFromDatabase();  // made a method that loads up the students from the db into the arrayList
        
        ArrayList<Course> courses = loadCoursesFromDatabase();
        ArrayList<Department> departments = loadDepartmentsFromDatabase();
        ArrayList<Schedule> schedules = new ArrayList<>();
        
        ArrayList<Faculty> faculty = loadFacultyFromDatabase(departments);
        



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
            System.out.print("Enter new name: ");
            String newName = in.nextLine();
            if (!newName.isEmpty()) {
                studentToEdit.setName(newName);
            }

            System.out.print("Enter new email address: ");
            String newEmail = in.nextLine();
            if (!newEmail.isEmpty()) {
                studentToEdit.setEmail(newEmail);
            }

            System.out.print("Enter new GPA: ");
            double newGPA = in.nextDouble();
            studentToEdit.setGPA(newGPA);

            // Edit the student address
            in.nextLine(); // Consume the newline after nextDouble()
            System.out.print("Enter new home street address: ");
            String newHomeStreet = in.nextLine();
            if (!newHomeStreet.isEmpty()) {
                studentToEdit.setHomeStreet(newHomeStreet);
                studentToEdit.updateHomeAddress();
            }

            System.out.print("Enter new home city: ");
            String newHomeCity = in.nextLine();
            if (!newHomeCity.isEmpty()) {
                studentToEdit.setHomeCity(newHomeCity);
                studentToEdit.updateHomeAddress();
            }

            System.out.print("Enter new home state: ");
            String newHomeState = in.nextLine();
            if (!newHomeState.isEmpty()) {
                studentToEdit.setHomeState(newHomeState);
                studentToEdit.updateHomeAddress();
            }

            System.out.print("Enter new home ZIP code: ");
            String newHomeZIP = in.nextLine();
            if (!newHomeZIP.isEmpty()) {
                studentToEdit.setHomeZIP(newHomeZIP);
                studentToEdit.updateHomeAddress();
            }

            // Edit the student's emergency contact information
            System.out.print("Enter new emergency contact name: ");
            String newEContactName = in.nextLine();
            if (!newEContactName.isEmpty()) {
                studentToEdit.setEContactName(newEContactName);
            }

            System.out.print("Enter new emergency contact phone: ");
            String newEContactPhone = in.nextLine();
            if (!newEContactPhone.isEmpty()) {
                studentToEdit.setEContactPhone(newEContactPhone);
                
            }

            System.out.print("Enter new emergency contact street: ");
            String newEContactStreet = in.nextLine();
            if (!newEContactStreet.isEmpty()) {
                studentToEdit.setEContactStreet(newEContactStreet);
                studentToEdit.updateEContactAddress();
            }

            System.out.print("Enter new emergency contact city: ");
            String newEContactCity = in.nextLine();
            if (!newEContactCity.isEmpty()) {
                studentToEdit.setEContactCity(newEContactCity);
                studentToEdit.updateEContactAddress();
            }

            System.out.print("Enter new emergency contact state: ");
            String newEContactState = in.nextLine();
            if (!newEContactState.isEmpty()) {
                studentToEdit.setEContactState(newEContactState);
                studentToEdit.updateEContactAddress();
            }

            System.out.print("Enter new emergency contact ZIP: ");
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
        generateStudentReport(students); // im lazy i know
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
                    generateCourseReport(courses);
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

        // Get user input to populate the course object
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
        int creditHours = in.nextInt();
        in.nextLine();

        System.out.print("Enter subject: ");
        String subject = in.nextLine();

        Faculty faculty = new Faculty();

        // Create the new course object with the provided information
        Course newCourse = new Course(prefix, number, days, startTime, endTime, creditHours, subject, faculty);

        // Build the SQL query from the course object
        String sqlQuery = "INSERT INTO COURSE (COURSEID, COURSEPREFIX, COURSENUMBER, COURSENAME, DAYSOFWEEK, STARTTIME, ENDTIME, CREDITHOURS, SUBJECT) VALUES ('"
                + newCourse.getCourseID() + "', '" + newCourse.getCoursePrefix() + "', '" + newCourse.getCourseNumber() + "', '" + newCourse.getCourseName() + "', '"
                + newCourse.getDaysOfWeek() + "', '" + newCourse.getStartTime() + "', '" + newCourse.getEndTime() + "', "
                + newCourse.getCreditHours() + ", '" + newCourse.getSubject() + "')";

        // Call runDBQuery to execute the INSERT query
        runDBQuery(sqlQuery, 'c');

        // Add the new course to the list of courses
        courses.add(newCourse);
        System.out.println("Course added successfully!");
    }

    
    public static void deleteCourse(ArrayList<Course> courses) {
        if (courses.isEmpty()) { // If the course list is empty, print error and return to the sub-menu
            System.out.println("No courses available to delete.");
            return;
        }

        // Display all courses that can be deleted
        generateCourseReport(courses);

        // Ask for the courseID of the course to delete
        System.out.print("Enter the Course ID of the course to delete: ");
        int courseID = in.nextInt();

        // SQL query to delete the course with the given ID
        String query = "DELETE FROM COURSE WHERE COURSEID = " + courseID;

        // Call runDBQuery to execute the DELETE query
        runDBQuery(query, 'd'); // 'd' for DELETE query

        System.out.println("Course with ID " + courseID + " has been deleted.");
    }

    
    public static void editCourse(ArrayList<Course> courses) {
        System.out.println("\n=== Edit a Course ===");

        if (courses.isEmpty()) {
            System.out.println("No courses available to edit.");
            return;
        }

        // show all courses that can be edited
        generateCourseReport(courses);

        // ask for the course number of the course to edit
        System.out.print("Enter the ID of the course to edit: ");
        int courseID = in.nextInt();
        in.nextLine();

        // find the course with the matching course number
        Course courseToEdit = null;
        for (Course course : courses) {
            if (courseID == course.getCourseID()) {
                courseToEdit = course;
                break;
            }
        }

        // if course found, edit its details
        if (courseToEdit != null) {
            System.out.println("Editing course: " + courseToEdit.getCourseName());

            // Edit the course's details
            System.out.print("Enter new course prefix (current: " + courseToEdit.getCoursePrefix() + "): ");
            String newCoursePrefix = in.nextLine();
            if (!newCoursePrefix.isEmpty()) {
                courseToEdit.setCoursePrefix(newCoursePrefix);
            }

            // Option to edit course number
            System.out.print("Enter new course number (current: " + courseToEdit.getCourseNumber() + "): ");
            String newCourseNumber = in.nextLine();
            if (!newCourseNumber.isEmpty()) {
                courseToEdit.setCourseNumber(newCourseNumber);
            }
            
            System.out.print("Enter new course name: ");
            String newCourseName = in.nextLine();
            if (!newCourseName.isEmpty()) {
                courseToEdit.setCourseName(newCourseName);
            }

            System.out.print("Enter new days of the week: ");
            String newDays = in.nextLine();
            if (!newDays.isEmpty()) {
                courseToEdit.setDaysOfWeek(newDays);
            }

            System.out.print("Enter new start time: ");
            String newStartTime = in.nextLine();
            if (!newStartTime.isEmpty()) {
                courseToEdit.setStartTime(newStartTime);
            }

            System.out.print("Enter new end time: ");
            String newEndTime = in.nextLine();
            if (!newEndTime.isEmpty()) {
                courseToEdit.setEndTime(newEndTime);
            }

            System.out.print("Enter new credit hours: ");
            int newCreditHours = in.nextInt();
            in.nextLine(); // Consume the newline character
            courseToEdit.setCreditHours(newCreditHours);

            System.out.print("Enter new subject: ");
            String newSubject = in.nextLine();
            if (!newSubject.isEmpty()) {
                courseToEdit.setSubject(newSubject);
            }

            // Construct the SQL query to update the course details
            String sqlQuery = "UPDATE COURSE SET "
                    + "COURSEPREFIX = '" + courseToEdit.getCoursePrefix() + "', "
                    + "COURSENUMBER = '" + courseToEdit.getCourseNumber() + "', "
                    + "COURSENAME = '" + courseToEdit.getCourseName() + "', "
                    + "DAYSOFWEEK = '" + courseToEdit.getDaysOfWeek() + "', "
                    + "STARTTIME = '" + courseToEdit.getStartTime() + "', "
                    + "ENDTIME = '" + courseToEdit.getEndTime() + "', "
                    + "CREDITHOURS = " + courseToEdit.getCreditHours() + ", "
                    + "SUBJECT = '" + courseToEdit.getSubject() + "' "
                    + "WHERE COURSEID = '" + courseToEdit.getCourseID() + "'";

            // Execute the SQL query to update the course details in the database
            runDBQuery(sqlQuery, 'u'); // 'u' for UPDATE query

            System.out.println("Course details updated successfully!");
        } else {
            System.out.println("Course not found.");
        }
    }

    
    public static void viewCourses(ArrayList<Course> courses) {
        generateCourseReport(courses);
        
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

        // Check if the department already exists
        Department department = null;
        for (Department dept : departments) {
            if (dept.getDepartmentName().equalsIgnoreCase(departmentName)) {
                department = dept;
                break;
            }
        }

        // If department is not found, create it
        if (department == null) {
            System.out.println("Department not found: " + departmentName);
            // Create new department and add to the list
            department = new Department(departmentName);
            departments.add(department); // Adds the new department to the list
            
            String insertDeptQuery = "INSERT INTO DEPARTMENT (DEPARTMENTID, DEPARTMENTNAME) VALUES (" + department.getDepartmentID() + ", '" + department.getDepartmentName() + "')";
            runDBQuery(insertDeptQuery, 'c');
            
            System.out.println("New department created: " + departmentName);
        }

        // Create the new faculty member object
        Faculty newFaculty = new Faculty(firstName, lastName, email, ssn, department, officeBuilding, officeNumber, phoneNumber, rank);
        facultyList.add(newFaculty); // Adds the faculty member to the faculty list

        // Add the new faculty member to the department's faculty list
        department.addFaculty(newFaculty); // Ensure Department has an addFaculty method

        // Insert the new faculty member into the database
        String sqlQuery = "INSERT INTO FACULTY (FACULTYID, FACULTYNAME, FACULTYEMAILADDRESS, DEPARTMENTID, BUILDINGNAME, OFFICENUMBER, FACULTYPHONENUMBER, POSITION) "
                + "VALUES (" + newFaculty.getUniversityID() + ", '"+ newFaculty.getFirstName() + " " + newFaculty.getLastName() + "', '"
                + newFaculty.getEmail() + "', (SELECT DEPARTMENTID FROM DEPARTMENT WHERE DEPARTMENTNAME = '" + departmentName + "'), '"
                + newFaculty.getOfficeNumber() + "', '" + newFaculty.getOfficeNumber() + "', '" + newFaculty.getPhoneNumber() + "', '"
                + newFaculty.getRank() + "')";

        // Call runDBQuery to execute the INSERT query
        runDBQuery(sqlQuery, 'c');

        System.out.println("Faculty member added successfully to the " + departmentName + " department!");
    }

    
    public static void deleteFaculty(ArrayList<Faculty> facultyList) {
        System.out.println("\n=== Delete Faculty Member ===");
        

        generateFacultyReport();
        
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
            // Delete from database
            String deleteQuery = "DELETE FROM FACULTY WHERE FACULTYID = " + universityID;
            runDBQuery(deleteQuery, 'c');  // Assuming 'c' is for executing queries that modify the database

            // Remove from facultyList
            facultyList.remove(facultyToRemove);
            System.out.println("Faculty member removed successfully from the database.");
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
                    generateFacultyReport();
                    break;
                case 3:
                    generateCourseReport(courses);
                    break;
                case 4:
                    generateDepartmentReport();
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
    public static void generateFacultyReport() {
        System.out.println("\n=== Faculty Report ===");

        // Query to fetch all faculty members with their details
        String query = "SELECT F.FACULTYID, F.FACULTYNAME, F.FACULTYEMAILADDRESS, D.DEPARTMENTNAME, F.OFFICENUMBER "
                     + "FROM FACULTY F "
                     + "JOIN DEPARTMENT D ON F.DEPARTMENTID = D.DEPARTMENTID";  // Assuming FACULTY table has a foreign key to DEPARTMENT table

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser";
            String pass = "javapass";
            oDS = new OracleDataSource();
            oDS.setURL(URL);
            jsqlConn = oDS.getConnection(user, pass);

            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("No faculty available.");
                return;
            }

            // Process the result set and display faculty details
            do {
                int facultyID = resultSet.getInt("FACULTYID");
                String facultyName = resultSet.getString("FACULTYNAME");
                String facultyEmail = resultSet.getString("FACULTYEMAILADDRESS");
                String departmentName = resultSet.getString("DEPARTMENTNAME");
                String officeNumber = resultSet.getString("OFFICENUMBER");

                // Print the faculty details
                System.out.println("Name: " + facultyName + " (" + facultyID + ")" );
                System.out.println("Department: " + departmentName);
                System.out.println("Office Location: " + officeNumber);
                System.out.println("Email: " + facultyEmail);
                System.out.println("--------------------------");

            } while (resultSet.next());

        } catch (SQLException e) {
            System.out.println("Error processing results: " + e.toString());
        }
    }


    // shows all courses 
    public static void generateCourseReport(ArrayList<Course> courses) {
        System.out.println("\n=== Course Report ===");

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        // Query to fetch all courses
        String query = "SELECT * FROM COURSE";

        // Executes the query
        runDBQuery(query, 'r');

        // Same logic as the previous method, but catered to returning course data
        try {
            if (jsqlResults != null) {
                while (jsqlResults.next()) {
                    int courseID = jsqlResults.getInt("COURSEID");
                    String coursePrefix = jsqlResults.getString("COURSEPREFIX");
                    String courseNumber = jsqlResults.getString("COURSENUMBER");
                    String courseName = jsqlResults.getString("COURSENAME");
                    String daysOfWeek = jsqlResults.getString("DAYSOFWEEK");
                    String startTime = jsqlResults.getString("STARTTIME");
                    String endTime = jsqlResults.getString("ENDTIME");
                    int creditHours = jsqlResults.getInt("CREDITHOURS");
                    String subject = jsqlResults.getString("SUBJECT");

                    // Print course data in the desired format
                    System.out.println("=====================");
                    System.out.println(coursePrefix + " " + courseNumber + " (" + courseID + ")");
                    System.out.println("Course Name: " + courseName);
                    System.out.println("Days of the Week: " + daysOfWeek);
                    System.out.println("Start Time: " + startTime);
                    System.out.println("End Time: " + endTime);
                    System.out.println("Credit Hours: " + creditHours);
                    System.out.println("Subject: " + subject);
                    System.out.println("=====================");
                    System.out.println(); // Adds a blank line between each course's information
                }
            } else {
                System.out.println("No results returned.");
            }
        } catch (SQLException sqlex) {
            System.out.println("Error processing results: " + sqlex.toString());
        }
    }

    
    // this method almost killed me. 
    // had to do a lot of research (and some working with AI) to get it working.
    // the members were previously being duplicated across every department
    // not anymore
    public static void generateDepartmentReport() {
        System.out.println("\n=== Department Report ===");

        // all departments and their faculty
        String query = "SELECT D.DEPARTMENTID, D.DEPARTMENTNAME, F.FACULTYID, F.FACULTYNAME, F.FACULTYEMAILADDRESS "
                     + "FROM DEPARTMENT D "
                     + "LEFT JOIN FACULTY F ON D.DEPARTMENTID = F.DEPARTMENTID";

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser";
            String pass = "javapass";
            oDS = new OracleDataSource();
            oDS.setURL(URL);
            jsqlConn = oDS.getConnection(user, pass);

            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // Variables to hold current department being processed
            String currentDepartment = "";
            List<Faculty> facultyList = new ArrayList<>();  // List to store faculty for each department

            // Process the result set
            while (resultSet.next()) {
                String departmentName = resultSet.getString("DEPARTMENTNAME");
                String facultyName = resultSet.getString("FACULTYNAME");
                String facultyEmail = resultSet.getString("FACULTYEMAILADDRESS");

                // If department name changes, print the department header and faculty list
                if (!departmentName.equals(currentDepartment)) {
                    // Print previous department's faculty if it's not the first one
                    if (!facultyList.isEmpty()) {
                        System.out.println("Faculty Members:");
                        for (Faculty faculty : facultyList) {
                            System.out.println(" - " + faculty.getName() + " (" + faculty.getEmail() + ")");
                        }
                    }

                    // Print the new department header
                    if (!departmentName.equals(currentDepartment)) {
                        if (!facultyList.isEmpty()) {
                            System.out.println("--------------------------");
                        }
                        currentDepartment = departmentName;
                        facultyList.clear();  // Clear faculty list for the new department

                        // Print department header
                        System.out.println("Department: " + departmentName);
                    }
                }

                // If faculty is found, create Faculty object and add to the list
                if (facultyName != null) {
                    Faculty faculty = new Faculty(facultyName, facultyEmail);
                    facultyList.add(faculty);
                }
            }

            if (!facultyList.isEmpty()) {
                System.out.println("Faculty Members:");
                for (Faculty faculty : facultyList) {
                    System.out.println(" - " + faculty.getName() + " (" + faculty.getEmail() + "), ID: " + faculty.getUniversityID());
                }
            }

            System.out.println("--------------------------");
            System.out.println();

        } catch (SQLException e) {
            System.out.println("Error processing results: " + e.toString());
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
    
    
    
    // NEWLY ADDED PART 3 METHODS --> 
    // WHY??:
    //  - i wanted to be able to have data in the database be the same as data in the heap
    //  - kinda hard to do but i think it makes the program run better - especially for modifications & deletions 
    // how??: 
    //  - created a method for each arrayList that was in part2
    //  - queried the db for all occurences from the respective table
    //  - created a new object for each record
    //      - didnt work at first so i had to modify the constructors of the CDFs to allow an inputted ID 
    //      - also had to check if it already existed (primarily for department) so thta there werent duplicates
    //  - populate the arrayList tht was passed into method 
    //  - pray that it didnt destroy everything
    
     
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

            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            //  set and create Student objects
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

                // create a new Student object
                Student student = new Student(studentID, studentName, email, gpa, homeAddress, emergencyContactName, emergencyContactPhone, emergencyContactAddress, ssn);

                // add the newly created student to the ArrayList
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return students;
    }
    
    public static ArrayList<Course> loadCoursesFromDatabase() {
        ArrayList<Course> courses = new ArrayList<>();
        String query = "SELECT * FROM Course";

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser";
            String pass = "javapass";
            oDS = new OracleDataSource();
            oDS.setURL(URL);
            jsqlConn = oDS.getConnection(user, pass);

            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // create and set course objects 
            while (resultSet.next()) {
                int courseID = resultSet.getInt("courseID");
                String coursePrefix = resultSet.getString("coursePrefix");
                String courseNumber = resultSet.getString("courseNumber");
                String courseName = resultSet.getString("courseName");
                String daysOfWeek = resultSet.getString("daysOfWeek");
                String startTime = resultSet.getString("startTime");
                String endTime = resultSet.getString("endTime");
                int creditHours = resultSet.getInt("creditHours");
                String subject = resultSet.getString("subject");

                Faculty faculty = new Faculty(); 

                Course course = new Course(courseID, coursePrefix, courseNumber, courseName, daysOfWeek, startTime, endTime, creditHours, subject, faculty);

                courses.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return courses;
    }

    public static ArrayList<Department> loadDepartmentsFromDatabase() {
        ArrayList<Department> departments = new ArrayList<>();
        String query = "SELECT * FROM DEPARTMENT";

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser";
            String pass = "javapass";
            oDS = new OracleDataSource();
            oDS.setURL(URL);
            jsqlConn = oDS.getConnection(user, pass);

            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // create and set department objects from results
            while (resultSet.next()) {
                int departmentID = resultSet.getInt("departmentID");
                String departmentName = resultSet.getString("departmentName");

                // check if the department already exists
                boolean exists = false;
                for (Department dept : departments) {
                    if (dept.getDepartmentName().equalsIgnoreCase(departmentName)) {
                        exists = true;
                        break;
                    }
                }

                // if the department does not exist --> create and add it to the list
                if (!exists) {
                    Department department = new Department(departmentName);
                    departments.add(department);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return departments;
    }

    
    public static ArrayList<Faculty> loadFacultyFromDatabase(ArrayList<Department> departments) {
        ArrayList<Faculty> facultyList = new ArrayList<>();
        String query = "SELECT * FROM FACULTY";

        try {
            String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
            String user = "javauser";
            String pass = "javapass";
            oDS = new OracleDataSource();
            oDS.setURL(URL);
            jsqlConn = oDS.getConnection(user, pass);

            PreparedStatement stmt = jsqlConn.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery();

            // Create and set Faculty objects based on query results
            while (resultSet.next()) {
                int facultyID = resultSet.getInt("facultyID");
                String name = resultSet.getString("facultyName");
                String email = resultSet.getString("facultyEmailAddress");
                String departmentName = resultSet.getString("departmentID");
                String officeBuilding = resultSet.getString("buildingName");
                String officeNumber = resultSet.getString("officeNumber");
                String phoneNumber = resultSet.getString("facultyPhoneNumber");
                String rank = resultSet.getString("position");

                // find or create the department
                Department department = null;
                for (Department dept : departments) {
                    if (dept.getDepartmentName().equalsIgnoreCase(departmentName)) {
                        department = dept;
                        break;
                    }
                }

                // if department is not found --> create it and add it to the departments list
                if (department == null) {
                    department = new Department(departmentName);
                    departments.add(department);
                }

                // create a new Faculty object
                Faculty faculty = new Faculty(facultyID, name, email, department, officeBuilding, officeNumber, phoneNumber, rank);

                // add the faculty to the department and faculty list
                department.addFaculty(faculty);
                facultyList.add(faculty);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }

        return facultyList;
    }

    
    
}
