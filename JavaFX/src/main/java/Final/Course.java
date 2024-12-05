package Final;

public class Course {
    private String coursePrefix;
    private String courseNumber;
    private String courseName;
    private String daysOfWeek;
    private String startTime;
    private String endTime;
    private String creditHours;
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
        this.creditHours = "0";
        this.subject = "N/A";
        this.faculty = new Faculty(); // creates new blank faculty member if the course is blank 

        this.courseID = nextID++;
    }

    public Course(String coursePrefix, String courseNumber, String daysOfWeek, String startTime, 
                  String endTime, String creditHours, String subject, Faculty faculty) {
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
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
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

    public String getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(String creditHours) {
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
