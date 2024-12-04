package Final;

public class Course {
    private String coursePrefix;
    private String courseNumber;
    private String courseName;
    private String daysOfWeek;
    private String startTime;
    private String endTime;
    private int creditHours;
    private String subject;
    private Faculty faculty; // stores a faculty member with the course

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
    }

    // getters n setters
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    // course report 
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
