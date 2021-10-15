package Proj2;

import java.util.*;

public class Cinema_Runner {

    public static ArrayList<Movie> movie_init() {
        boolean movieSuccess = false;
        ArrayList<Movie> validMovies = new ArrayList<>();

        Scanner reader = new Scanner(System.in);


        while (!movieSuccess) {
            System.out.println("Enter movie file name: "); // reads in movie file name
            String filename = reader.nextLine();
            validMovies = Movie.readMovies(filename);

            if(validMovies != null){
                movieSuccess = true;
            }

        }
        return validMovies;
    }

    public static void main(String[] args){
        System.out.println("Initialising Cinema System...");

        //reads in all movies
        ArrayList<Movie> validMovies = new ArrayList<>();
        validMovies = movie_init();

        //read in all cinemas
        boolean cinemaSuccess = false;
        ArrayList<Cinema> validCinemas = new ArrayList<>();

        while (!cinemaSuccess) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter cinema file name: "); // reads in cinema file name

            String filename = reader.nextLine();

            validCinemas = Cinema.readCinemas(filename);

            if(validCinemas != null){
                cinemaSuccess = true;
            }
        }


        //reads in all cards
        //gonna need to make changes for JSON
        boolean cardSuccess = false;
        ArrayList<Card> validCards = new ArrayList<>();

        while (!cardSuccess) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter card file name: "); // reads in card file name

            String filename = reader.nextLine();

            validCards = Card.readCards(filename);

            if(validCards != null){
                cardSuccess = true;
            }
        }


        //reads in all gift cards
        boolean giftCardSuccess = false;
        ArrayList<GiftCard> validGiftCards = new ArrayList<>();

        while (!giftCardSuccess) {
            Scanner reader = new Scanner(System.in);
            System.out.println("Enter gift card file name: "); // reads in gift card file name

            String filename = reader.nextLine();

            validGiftCards = GiftCard.readGiftCards(filename);

            if(validGiftCards != null){
                giftCardSuccess = true;
            }
        }


        System.out.println("Completed Genkins System Initialization.");


        Scanner cinemaInput = new Scanner(System.in);
        boolean running = true;
        boolean prompting = true;
        while(running){
            while(prompting){
                System.out.println("Welcome to the Genkins Movie Booking System!");
                System.out.println("Movies");
                for(Cinema c : validCinemas){
                    System.out.println("-------------------------------------------------------");
                    System.out.println(c.getName() + '\n' + c.getLocation());
                    System.out.println("-------------------------------------------------------");
                    for(Movie m: c.getMovies()){
                        System.out.println(m.getName() + '\n' + m.getUpcomingTimes());
                    }
                }
            }
        }
    }
}