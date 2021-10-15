package Proj2;

import java.util.*;

public class Cinema_Runner {
    public static void main(String[] args){
        UserInput u = new UserInput(System.in, System.out);
        System.out.println("Initialising Cinema System...");

        //reads in all movies
        ArrayList<Movie> validMovies = new ArrayList<>();
        validMovies = u.movieInit();

        //read in all cinemas
        ArrayList<Cinema> validCinemas = new ArrayList<>();
        validCinemas = u.cinemaInit();

        //read in all cards
        //gonna need to make changes for JSON
        ArrayList<Cinema> validCinemas = new ArrayList<>();
        validCinemas = u.cinemaInit();

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
        while(running){
            System.out.println("Welcome to the Genkins Movie Booking System!");
            System.out.println("Movies");
            for(Cinema c : validCinemas){
                System.out.println("-------------------------------------------------------");
                System.out.println(c.getName() + '\n' + c.getLocation());
                System.out.println("-------------------------------------------------------");

                for(Movie m : c.getMovies()){
                    System.out.println(m.getName());

                    for(String s : m.getUpcomingTimes()){
                        System.out.println(s);
                    }
                }
            }

            //when prompting guest to login
            while(prompting){

            }
        }
    }
}