package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

public class Course {
    private String coursePrefix;
    private String courseNumber;
    private String coursePreNum = coursePrefix + " " + courseNumber;
    private String courseName;
    private String daysOfWeek;
    private String startTime;
    private String endTime;
    private int creditHours;
    private String subject;
    private Faculty faculty; // stores a faculty member with the course

    public int  courseID;
    private static int nextID = 1; 

    public Course() {
        this.coursePrefix = "N/A";
        this.courseNumber = "N/A";
        this.courseName = "N/A";
        this.daysOfWeek = "N/A";
        this.startTime = "N/A";
        this.endTime = "N/A";
        this.creditHours = 0;
        this.subject = "N/A";
        this.faculty = new Faculty(); // creates new blank faculty member if the course is blank 

        this.courseID = nextID++;
    }

    public Course(String coursePrefix, String courseNumber, String daysOfWeek, String startTime, 
                  String endTime, int creditHours, String subject, Faculty faculty) {
        this.coursePrefix = coursePrefix;
        this.courseNumber = courseNumber;
        this.courseName = coursePrefix + courseNumber; // Concatenate prefix and number to form the course name
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.creditHours = creditHours;
        this.subject = subject;
        this.faculty = faculty;

        this.courseID = nextID++;
    }
    
    public Course(int courseID, String coursePrefix, String courseNumber, String courseName, String daysOfWeek, String startTime, String endTime, int creditHours, String subject, Faculty faculty){
        this.courseID = courseID;
        this.coursePrefix = coursePrefix;
        this.courseNumber = courseNumber;
        this.courseName = coursePrefix + courseNumber; // Concatenate prefix and number to form the course name
        this.daysOfWeek = daysOfWeek;
        this.startTime = startTime;
        this.endTime = endTime;
        this.creditHours = creditHours;
        this.subject = subject;
        this.faculty = faculty;
    }

    // getters n setters
    public Faculty getFaculty(){
        return faculty;
    }

    public int getCourseID(){
        return courseID;
    }

    public String getCoursePrefix() {
        return coursePrefix;
    }

    public void setCoursePrefix(String coursePrefix) {
        this.coursePrefix = coursePrefix;
        this.coursePreNum = coursePrefix + this.courseNumber; // sets it to updated name

    }
   

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
        this.coursePreNum = this.coursePrefix + courseNumber; 

    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(String daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }




    // course report 
    @Override
    public String toString() {
        String courseDetails = "";

        courseDetails += "======================== Course Details ========================\n";
        courseDetails += "Course Name      : " + this.courseName + "\n";
        courseDetails += "Professor        : " + this.faculty.getFirstName() + " " + this.faculty.getLastName() + "\n";
        courseDetails += "Room Number      : " + this.faculty.getOfficeNumber() + "\n";
        courseDetails += "Days of Week     : " + this.daysOfWeek + "\n";
        courseDetails += "Start Time       : " + this.startTime + "\n";
        courseDetails += "End Time         : " + this.endTime + "\n";
        courseDetails += "Credit Hours     : " + this.creditHours + "\n";
        courseDetails += "Subject          : " + this.subject + "\n";
        courseDetails += "==============================================================";

        return courseDetails;
    }
}
