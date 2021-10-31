package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.math.BigDecimal;

public class Movie {
    private final int id;
    private String name;
    private String synopsis; //plot
    private String rating; //rating
    private String releaseDate; //?
    private ArrayList<String> cast; //people

    public Movie(int id, String name, String synopsis, String rating, String releaseDate, ArrayList<String> cast){ //ArrayList<String> upcomingTimes) {
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.cast = cast;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id=id;
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

    public String getMovieDetails() {
        return ("\n" + getName() + '\n' +
                "Synopsis: " + getSynopsis() + '\n' +
                "Rating: " + getRating() + '\n' +
                "Release Date: " + getReleaseDate() + '\n' +
                "Cast: " + String.join(", ", getCast()));
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
                String[] castList = line[5].split(";");
                ArrayList<String> cast = new ArrayList<>(Arrays.asList(castList));
                movieList.add(new Movie(Integer.parseInt(line[0]),
                        line[1], line[2], line[3], line[4], cast));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading movie file. Please try again.\n");
            return null;
        }

        return movieList;
    }
}
