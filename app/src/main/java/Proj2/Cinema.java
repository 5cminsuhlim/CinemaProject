package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cinema {
    private final int id;
    private String name; //need to gen this
    private String location; //need to gen this
    private ArrayList<Movie> movies;
    private int transactionNo;
    private ArrayList<Customer> customers;

    public Cinema(int id, String name, String location, ArrayList<Movie> movies, ArrayList<Customer> customers) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.movies = movies;
        this.transactionNo = 0;
        this.customers = customers;
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
            String retname = mov.getName();
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

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    protected static ArrayList<Cinema> readCinemas(String filename){
        ArrayList<Cinema> cinemaList = new ArrayList<>();

        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");
                String[] movieArr = line[3].split(";");
                ArrayList<Movie> movieList = new ArrayList<>();

                for(String movie : movieArr){
                    Movie mov = searchMovie(movie, CinemaRunner.validMovies);
                    movieList.add(mov);
                }
                
                ArrayList<Customer> customers = new ArrayList<>();
                //Implement fucntionality to create/read movie objects and customers objects

                cinemaList.add(new Cinema(Integer.parseInt(line[0]), line[1], line[2], movieList, customers));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading cinema file. Please try again.\n");
            return null;
        }

        return cinemaList;
    }

    public int getId() {
        return id;
    }
}
