package Proj2;

import java.io.*;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Cinema {
    private final int id;
    private ArrayList<Movie> moviesParent;
    private String name; //need to gen this
    private String location; //need to gen this
    private ArrayList<MovieInstance> movies;
    private int transactionNo;

    public Cinema(int id, String name, String location, ArrayList<MovieInstance> movies, ArrayList<Movie> moviesParent) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.movies = movies;
        this.transactionNo = 0;
        this.moviesParent = moviesParent;
    }

    public String getName() {
        return name;
    }




    public String getTicketReceipt(){
        transactionNo++;

        return "Cinema ID: " + id + "\nTransaction No.: " + transactionNo + "\n";
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
        ArrayList<String> output = new ArrayList<>();
        for(Movie m : moviesParent){
            ArrayList<String> movieList = new ArrayList<>();
            boolean first = true;
            for(MovieInstance mov : movies){
                if(mov.getM_id() == m.getId()){
                    if(first){
                        movieList.add(m.getName() + ": " + mov.getSchedule());
                        first = false;
                    } else{
                        movieList.add(mov.getSchedule());
                    }
                }
            }
            output.add(String.join(" | ", movieList)) ;
        }
        if(output.size() == 0){
            return "No showings left for this week.";
        }
        output.removeIf(s -> s.length() < 5);
        output.sort(String::compareToIgnoreCase);
        return String.join("\n", output);
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

    public static ArrayList<MovieInstance> sortInstances(ArrayList<MovieInstance> unsorted){
        ArrayList<MovieInstance> sorted = new ArrayList<>();
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
        LocalTime[] times = {LocalTime.parse("10:00:00"), LocalTime.parse("12:00:00"), LocalTime.parse("14:00:00"),
                LocalTime.parse("16:00:00"), LocalTime.parse("18:00:00"), LocalTime.parse("20:00:00"),
                LocalTime.parse("22:00:00")};
        for(String s : days){
            for(LocalTime t : times){
                for(MovieInstance m : unsorted){
                    if(m.getDay().equals(s) && m.getTime().equals(t)){
                        sorted.add(m);
                    }
                }
            }
        }
        return sorted;
    }

    protected static ArrayList<Cinema> readCinemas(String filename, ArrayList<Movie> validMovies){
        ArrayList<Cinema> cinemaList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        LocalTime currentTime = LocalTime.now();
        if(dayOfWeek == 0){
            dayOfWeek = 7;
        }
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");

                ArrayList<MovieInstance> movieList = new ArrayList<>();
                ArrayList<Movie> moviesParent = new ArrayList<>();

                    String[] movieArr = line[3].split(";");
                    for (String movie : movieArr) {
                        String[] detail = movie.split(":");
                        String timeStr = detail[9] + ":00:00";
                        LocalTime time = LocalTime.parse(timeStr);

                        boolean playable = false;
                        for (int i = 0; i < 7; i++) {
                            if (detail[8].equals(days[i])) {
                                playable = (i + 1 > dayOfWeek || (i + 1 == dayOfWeek && time.compareTo(currentTime) > 0));
                                break;
                            }
                        }
                        if (!playable) {
                            continue;
                        }

                        Movie parent = null;

                        for (Movie mov : validMovies) {
                            //System.out.println(mov.getId());
                            //System.out.println(detail[0]);
                            if (mov.getId() == Integer.parseInt(detail[0])) {
                                parent = mov;
                                if (!moviesParent.contains(parent)) {
                                    moviesParent.add(parent);
                                }
                                //System.out.println("found parent for ");
                                //System.out.println(detail[0]);
                                break;
                            }
                        }

                        MovieInstance instance = new MovieInstance(Integer.parseInt(detail[0]), Integer.parseInt(detail[1]),
                                parent, Integer.parseInt(detail[2]), Integer.parseInt(detail[4]), Integer.parseInt(detail[6]),
                                detail[8], time, detail[10], new BigDecimal(detail[11]));
                        instance.setF_seatsOpen(Integer.parseInt(detail[3]));
                        instance.setM_seatsOpen(Integer.parseInt(detail[5]));
                        instance.setR_seatsOpen(Integer.parseInt(detail[7]));
                        movieList.add(instance);
                    }


                ArrayList<Customer> customers = new ArrayList<>();
                //Implement fucntionality to create/read movie objects and customers objects
                ArrayList<MovieInstance> sortedMovies = new ArrayList<>();
                cinemaList.add(new Cinema(Integer.parseInt(line[0]), line[1], line[2], sortInstances(movieList), moviesParent));
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

    protected static int saveCinemas(String filename, ArrayList<Cinema> cinemas){
        cinemas.sort(Comparator.comparing(Cinema::getId));
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()){
            f.delete();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), StandardCharsets.UTF_8))) {
                for(Cinema c : cinemas) {
                    // int id, String name, String location, ArrayList<MovieInstance> movies, ArrayList<Movie> moviesParent
                    ArrayList<String> movieList = new ArrayList<>();
                    for(MovieInstance m : c.getMovies()){
                        movieList.add(m.getM_id() + ":" + m.getC_id() + ":" + m.getF_seatsOpen() + ":" + m.getF_seatsCapacity() + ":" +
                                m.getM_seatsOpen() + ":" + m.getM_seatsCapacity() + ":" + m.getR_seatsOpen() + ":" + m.getR_seatsCapacity()
                                + ":" + m.getDay() + ":" + m.getTime().toString().substring(0,2) + ":" + m.getScreenSize() + ":" + m.getBasePrice());
                    }
                    writer.write(c.getId() + "," + c.getName() + "," + c.getLocation() + "," +
                            String.join(";", movieList) +  "\n");
                }
            }
            catch(Exception e){
                System.out.println("Saving cinema details failed.");
                return -2;
            }
            System.out.println("Cinema details saved.");
            return 1;
        } else{
            System.out.println("File does not exist. Please try again");
            return -1;
        }

    }

}
