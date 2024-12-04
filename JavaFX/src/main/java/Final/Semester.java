package Final;

public class Semester {
    private String period;  // e.g., "Fall", "Spring", "Summer"
    private int year;
    private int weekLength;

    public Semester() {
        this.period = "N/A";
        this.year = 0;
        this.weekLength = 0;
    }

    public Semester(String period, int year, int weekLength) {
        this.period = period;
        this.year = year;
        this.weekLength = weekLength;
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
