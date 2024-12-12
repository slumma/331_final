package Final_tester;

/*
Programmer Name(s) : Sam Ogden, Amy Lee, Noemi Villar Glass
CIS 331
Purpose : create an application for a community college that allows a user to create/alter/delete university accounts and generate reports for specific members.
*/

public class Semester {
    private String period;  // e.g., "Fall", "Spring", "Summer"
    private int year;
    private int weekLength;
    public int semesterID;
    private static int nextID = 1; 

    public Semester() {
        this.period = "N/A";
        this.year = 0;
        this.weekLength = 0;
        this.semesterID = nextID++;
    }

    public Semester(String period, int year, int weekLength) {
        this.period = period;
        this.year = year;
        this.weekLength = weekLength;
        this.semesterID = nextID++;
    }

    // getters n setters
    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeekLength() {
        return weekLength;
    }

    public void setWeekLength(int weekLength) {
        this.weekLength = weekLength;
    }
    
    public int getSemesterID(){
        return semesterID;
    }
    
    
    // semester report 
    @Override
    public String toString() {
        return "======================== Semester Details ========================\n" +
               "Period           : " + this.period + "\n" +
               "Year             : " + this.year + "\n" +
               "Week Length      : " + this.weekLength + " weeks\n" +
               "==============================================================";
    }
}
