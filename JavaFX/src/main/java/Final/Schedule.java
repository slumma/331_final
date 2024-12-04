package Final;

import java.util.ArrayList;

public class Schedule {
    private Student student;
    private Semester semester;
    private ArrayList<Course> courses;

    public Schedule() {
        this.student = new Student();
        this.semester = new Semester();
        this.courses = new ArrayList<>();
    }

    public Schedule(Student student, Semester semester, ArrayList<Course> courses) {
        this.student = student;
        this.semester = semester;
        this.courses = courses;
    }

    // Getters n Setters
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    // adds a course to the schedule
    public void addCourse(Course course) {
        this.courses.add(course);
    }

    // schedule report 
    @Override
    public String toString() {
       
        // starter string to add stuff to it 
        String scheduleDetails = "";

        // includes universityID next to the student name in parentheses 
        scheduleDetails += "Student: " + this.student.getFirstName() + " " + this.student.getLastName() + " (" + this.student.universityID + ")\n";
        scheduleDetails += "Semester: " + this.semester.getPeriod() + " " + this.semester.getYear() + "\n";

        scheduleDetails += "Courses:\n";

        // checks if the courses are empty, if true ==> prints messages
        if (courses.isEmpty()) {
            scheduleDetails += "No courses registered.\n";
        } else {
            for (Course course : courses) {
                scheduleDetails += course.toString() + "\n";
            }
        }

        return scheduleDetails;
    }


}
