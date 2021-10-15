package Proj2;

import java.util.ArrayList;

public class Schedule {
    String day;
    ArrayList<String> upcomingTimes;

    public Schedule(String day, ArrayList<String> upcomingTimes){
        this.day = day;
        this.upcomingTimes = upcomingTimes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public ArrayList<String> getUpcomingTimes() {
        return upcomingTimes;
    }

    public void setUpcomingTimes(ArrayList<String> upcomingTimes) {
        this.upcomingTimes = upcomingTimes;
    }
}
