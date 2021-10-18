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

        boolean isGuest = false;
        boolean isCustomer = false;
        String selectedMovie = "";

        //check if guest or customer
        String input = u.checkUser();

        switch(input){
            case "1":
                isGuest = true;

            case "2":
                isCustomer = true;

            default:
                System.out.println("Invalid Input, please try again.\n");
        }


        //guest function
        while(isGuest && !isCustomer){
            //prompt guest to filter by movie, cinema, or screen size
            input = u.promptGuest();

            switch(input){
                case "1":
                    //prompt guest to look up a movie
                    input = u.findMovie();
                    selectedMovie = input;

                    for(Cinema c : validCinemas) {
                        for(Movie m : c.getMovies()){
                            //if movie is found
                            if(m.getName().equals(input)){
                                //print details
                                System.out.println(m.getMovieDetails());

                                //prompt guest if they want to book
                                input = u.bookMovie();

                                switch(input){
                                    case "1":
                                        //prompt guest to make an account
                                        System.out.println("To proceed with booking, please make an account.\n");


                                        //INCOMPLETE
                                        //NEED TO IMPLEMENT: if username is already taken, reprompt
                                        input = u.enterUsername();

                                        input = u.enterPassword();

                                        isCustomer = true;
                                        break;

                                    case "2":
                                        //don't book
                                        break;

                                    default:
                                        System.out.println("Invalid Input, please try again.\n");
                                }
                            }
                            else{
                                System.out.println("Movie not found\n");
                            }
                        }
                    }

                case "2":
                    //prompt guest to look up cinema
                    input = u.findCinema();

                    for(Cinema c : validCinemas) {
                        //if cinema is found
                        if(c.getName().equals(input)){
                            for(Movie m : c.getMovies()){
                                //print movie name
                                System.out.println(m.getName());
                            }
                        }
                        else{
                            System.out.println("Cinema not found\n");
                        }
                    }

                case "3":
                    //prompt guest to look up screen size
                    input = u.findScreen();

                    for(Cinema c : validCinemas) {
                        for(Movie m : c.getMovies()){
                            //if screen size is found
                            if(m.getScreenSize().equals(input)){
                                //print cinema name + location
                                System.out.println(c.getName() + "\n" + c.getLocation());

                                //print movie name
                                System.out.println(m.getName());
                            }
                        }

                    }

                default:
                    System.out.println("Invalid Input, please try again.\n");
            }


            //customer function
            while(isCustomer){


            }
        }
    }
}