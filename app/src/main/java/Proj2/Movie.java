package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.math.BigDecimal;

public class Movie {
    private String name;
    private String synopsis; //plot
    private String rating; //rating
    private String releaseDate; //?
    private ArrayList<String> cast; //people
    //private ArrayList<String> upcomingTimes; //need to gen this
    private Schedule schedule;
    private String screenSize; //need to gen this
    private int f_seatsOpen;  //need to gen this
    private int f_seatsBooked;  //need to gen this
    private int m_seatsOpen;  //need to gen this
    private int m_seatsBooked;  //need to gen this
    private int r_seatsOpen;  //need to gen this
    private int r_seatsBooked;  //need to gen this
    private BigDecimal basePrice; //need to gen this
    private BigDecimal ticketPrice; //0.8, 1.2, 1.6

    public Movie(String name, String synopsis, String rating, String releaseDate, ArrayList<String> cast, //ArrayList<String> upcomingTimes
                 Schedule schedule, String screenSize, int f_seatsOpen,
                 int f_seatsBooked, int m_seatsOpen, int m_seatsBooked,
                 int r_seatsOpen, int r_seatsBooked, BigDecimal basePrice) {
        this.name = name;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.cast = cast;
        //this.upcomingTimes = upcomingTimes;
        this.schedule = schedule;
        this.screenSize = screenSize;
        this.f_seatsOpen = f_seatsOpen;
        this.f_seatsBooked = f_seatsBooked;
        this.m_seatsOpen = m_seatsOpen;
        this.m_seatsBooked = m_seatsBooked;
        this.r_seatsOpen = r_seatsOpen;
        this.r_seatsBooked = r_seatsBooked;
        this.basePrice = basePrice;
        setTicketPrice(this.screenSize);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public ArrayList<String> getCast() {
        return cast;
    }

    public void setCast(ArrayList<String> cast) {
        this.cast = cast;
    }

    /*
    public ArrayList<String> getUpcomingTimes() {
        return upcomingTimes;
    }

    public void setUpcomingTimes(ArrayList<String> upcomingTimes) {
        this.upcomingTimes = upcomingTimes;
    }*/

    public String getSchedule() {
        String s = "";

        s += schedule.getDay() + ":";

        for (String time : schedule.getUpcomingTimes()) {
            s += time + "\n";
        }

        return s;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public void setScreenSize(String screenSize) {
        this.screenSize = screenSize;
    }

    public int getF_seatsOpen() {
        return f_seatsOpen;
    }

    public void setF_seatsOpen(int f_seatsOpen) {
        this.f_seatsOpen = f_seatsOpen;
    }

    public int getF_seatsBooked() {
        return f_seatsBooked;
    }

    public void setF_seatsBooked(int f_seatsBooked) {
        this.f_seatsBooked = f_seatsBooked;
    }

    public int getM_seatsOpen() {
        return m_seatsOpen;
    }

    public void setM_seatsOpen(int m_seatsOpen) {
        this.m_seatsOpen = m_seatsOpen;
    }

    public int getM_seatsBooked() {
        return m_seatsBooked;
    }

    public void setM_seatsBooked(int m_seatsBooked) {
        this.m_seatsBooked = m_seatsBooked;
    }

    public int getR_seatsOpen() {
        return r_seatsOpen;
    }

    public void setR_seatsOpen(int r_seatsOpen) {
        this.r_seatsOpen = r_seatsOpen;
    }

    public int getR_seatsBooked() {
        return r_seatsBooked;
    }

    public void setR_seatsBooked(int r_seatsBooked) {
        this.r_seatsBooked = r_seatsBooked;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(String screenSize) {
        if(screenSize.equalsIgnoreCase("bronze")){
            ticketPrice = basePrice.multiply(BigDecimal.valueOf(0.8));
        }
        else if(screenSize.equalsIgnoreCase("silver")){
            ticketPrice = basePrice.multiply(BigDecimal.valueOf(1.2));
        }
        else if(screenSize.equalsIgnoreCase("gold")){
            ticketPrice = basePrice.multiply(BigDecimal.valueOf(1.6));
        }
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public String getMovieDetails() {
        return ("\n" + getName() + '\n' +
                "Synopsis: " + getSynopsis() + '\n' +
                "Rating: " + getRating() + '\n' +
                "Release Date: " + getReleaseDate() + '\n' +
                "Cast: " + String.join(", ", getCast()) + "\n" +
                "Screen Size: " + getScreenSize() + '\n' +
                "Next Showing: " + getSchedule()) + "\n";
    }

    protected static ArrayList<Movie> readMovies(String filename){
        ArrayList<Movie> movieList = new ArrayList<>();

        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");

               //name,synopsis,rating,releaseDate,cast,schedule,screenSize, f_seatsOpen,
                // f_seatsBooked,m_seatsOpen,m_seatsBooked,r_seatsOpen,r_seatsBooked,BasePrice
                String[] castList = line[4].split(";");
                ArrayList<String> cast = new ArrayList<>(Arrays.asList(castList));
                Schedule schedule = new Schedule(
                        "Monday", new ArrayList<>()); // needs to be implemented properly
                movieList.add(new Movie(
                        line[0], line[1], line[2], line[3], cast, schedule, line[6], Integer.parseInt(line[7]),
                        Integer.parseInt(line[8]), Integer.parseInt(line[9]), Integer.parseInt(line[10]),
                        Integer.parseInt(line[11]),Integer.parseInt(line[12]), new BigDecimal(line[13]))
                );
            }
        }
        catch (Exception e) {
            System.out.println("Error reading movie file. Please try again.\n");
            return null;
        }

        return movieList;
    }

}
