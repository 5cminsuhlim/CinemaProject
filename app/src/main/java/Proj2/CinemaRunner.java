package Proj2;

import java.util.*;

public class CinemaRunner {
    public static void main(String[] args){
        UserInput u = new UserInput(System.in, System.out);
        System.out.println("Initialising Cinema System...");

        //reads in all movies
        ArrayList<Movie> validMovies = new ArrayList<>();
        validMovies = u.movieInit();

        //read in all cinemas
        ArrayList<Cinema> validCinemas = new ArrayList<>();
        validCinemas = u.cinemaInit();

        //reads in all cards
        //gonna need to make changes for JSON
        ArrayList<Card> validCards = new ArrayList<>();
        validCards = u.cardInit();

        //read in all gift cards
        ArrayList<GiftCard> validGiftCards = new ArrayList<>();
        validGiftCards = u.giftCardInit();


        System.out.println("Completed Genkins System Initialization.");


        Scanner cinemaInput = new Scanner(System.in);
        boolean running = true;
        boolean prompting = true;

        //default page
        System.out.println("Welcome to the Genkins Movie Booking System!");
        System.out.println("Movies");
        for(Cinema c : validCinemas){
            System.out.println("-------------------------------------------------------");
            System.out.println(c.getName() + '\n' + c.getLocation());
            System.out.println("-------------------------------------------------------");

            for(Movie m : c.getMovies()){
                System.out.println(m.getName());
                System.out.println(m.getSchedule());
            }
        }


        //guest function
        while(running){
            String guestChoice = u.promptGuest();

            switch(guestChoice){
                case "1":
                    String guestMovie = u.findMovie();

                    for(Cinema c : validCinemas) {
                        for(Movie m : c.getMovies()){
                            if(m.getName().equals(guestMovie)){
                                m.getMovieDetails();
                            }
                        }
                    }
                case "2":
                    String guestCinema = u.findCinema();

                    for(Cinema c : validCinemas) {
                        if(c.getName().equals(guestCinema)){
                            for(Movie m : c.getMovies()){
                                System.out.println(m.getName());
                                System.out.println(m.getSchedule());
                            }
                        }
                    }
                default:
                    System.out.println("Invalid Input, please try again.\n");
            }


            //when prompting guest to login (customer function)
            while(prompting){

            }
        }
    }
}