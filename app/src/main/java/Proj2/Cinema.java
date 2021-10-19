package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cinema {
    private String name; //need to gen this
    private String location; //need to gen this
    private ArrayList<Movie> movies;
    private int transactionNo;
    private ArrayList<Customer> customers;

    public Cinema(String name, String location, ArrayList<Movie> movies, ArrayList<Customer> customers) {
        this.name = name;
        this.location = location;
        this.movies = movies;
        this.transactionNo = 0;
        this.customers = customers;
    }

    public String getName() {
        return name;
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

                //need to double check ordering
                //Convert list input string to ArrayList
                ArrayList<Movie> input_movies = new ArrayList<Movie>();
                ArrayList<Customer> input_cinemas = new ArrayList<Customer>(); //FIX THIS LINE - NOT READING THE RIGHT DATA
                cinemaList.add(new Cinema(line[0], line[1], input_movies, input_cinemas));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading cinema file. Please try again.\n");
            return null;
        }

        return cinemaList;
    }
}
