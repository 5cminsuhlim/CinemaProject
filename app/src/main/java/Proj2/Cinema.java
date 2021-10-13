package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Cinema {
    private String name; //need to gen this
    private String location; //need to gen this
    private ArrayList<Movie> movies;

    public Cinema(String name, String location, ArrayList<Movie> movies) {
        this.name = name;
        this.location = location;
        this.movies = movies;
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
                cinemaList.add(new Cinema(line[0], line[1], line[2]));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading cinema file. Please try again.\n");
            return null;
        }

        return cinemaList;
    }
}
