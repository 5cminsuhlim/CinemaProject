package Proj2;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cinema {
    private final int id;
    private ArrayList<Movie> moviesParent;
    private String name; //need to gen this
    private String location; //need to gen this
    private ArrayList<MovieInstance> movies;
    private int transactionNo;
    private ArrayList<Customer> customers;

    public Cinema(int id, String name, String location, ArrayList<MovieInstance> movies, ArrayList<Customer> customers, ArrayList<Movie> moviesParent) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.movies = movies;
        this.transactionNo = 0;
        this.customers = customers;
        this.moviesParent = moviesParent;
    }

    public String getName() {
        return name;
    }

    public String getTicketReceipt(){
        transactionNo++;

        return String.valueOf(id) + String.valueOf(transactionNo);
    }

    public static Movie searchMovie(String search_name, ArrayList<Movie> movieslist){
        for (Movie mov: movieslist){
            String retname = String.valueOf(mov.getName());
            if(search_name.equalsIgnoreCase(retname)){
                return mov;
            }
        }
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSchedule(){
        StringBuilder a = new StringBuilder();
        for(Movie m : moviesParent){
            a.append(m.getName()).append(": ");
            StringBuilder times = new StringBuilder();
            for(MovieInstance mov : movies){
                if(mov.getM_id() == m.getId()){
                    times.append(mov.getSchedule()).append(" ");
                }
            }
            a.append(times).append("\n");
        }
        return a.toString();
    }

    public ArrayList<Movie> getMovieParents() {
        return moviesParent;
    }

    public ArrayList<MovieInstance> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<MovieInstance> movies) {
        this.movies = movies;
    }

    protected static ArrayList<Cinema> readCinemas(String filename, ArrayList<Movie> validMovies){
        ArrayList<Cinema> cinemaList = new ArrayList<>();

        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");

                String[] movieArr = line[3].split(";");
                ArrayList<MovieInstance> movieList = new ArrayList<>();
                ArrayList<Movie> moviesParent = new ArrayList<>();

                for(String movie : movieArr){
                    String[] detail = movie.split(":");
                    String timeStr = detail[6] + ":00:00";
                    LocalTime time = LocalTime.parse(timeStr);

                    Movie parent = null;
                    for(Movie mov : validMovies){
                        //System.out.println(mov.getId());
                        //System.out.println(detail[0]);
                        if(mov.getId() == Integer.parseInt(detail[0])){
                            parent = mov;
                            if(!moviesParent.contains(parent)){
                                moviesParent.add(parent);
                            }
                            //System.out.println("found parent for ");
                            //System.out.println(detail[0]);
                            break;
                        }
                    }

                    MovieInstance instance = new MovieInstance(Integer.parseInt(detail[0]), Integer.parseInt(detail[1]),
                            parent, Integer.parseInt(detail[2]), Integer.parseInt(detail[3]), Integer.parseInt(detail[4]),
                            detail[5], time, detail[7], new BigDecimal(detail[8]));

                    movieList.add(instance);
                }
                
                ArrayList<Customer> customers = new ArrayList<>();
                //Implement fucntionality to create/read movie objects and customers objects
                cinemaList.add(new Cinema(Integer.parseInt(line[0]), line[1], line[2], movieList, customers, moviesParent));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading cinema file. Please try again.\n");
            System.out.println(e);
            return null;
        }

        return cinemaList;
    }

    public int getId() {
        return id;
    }
}
