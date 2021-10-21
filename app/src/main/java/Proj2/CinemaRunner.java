package Proj2;

import java.util.*;

public class CinemaRunner {
    public static ArrayList<Movie> validMovies;
    public static HashMap<String, String> customers = new HashMap<>();


    public static void main(String[] args) {
        UserInput u = new UserInput(System.in, System.out);
        System.out.println("Initialising Cinema System...");

        //reads in all movies
        validMovies = u.movieInit();

        //read in all cinemas
        ArrayList<Cinema> validCinemas;
        validCinemas = u.cinemaInit();

        //reads in all cards
        //gonna need to make changes for JSON
        ArrayList<Card> validCards = new ArrayList<>();
        validCards = u.cardInit();

        //read in all gift cards
        ArrayList<GiftCard> validGiftCards = new ArrayList<>();
        validGiftCards = u.giftCardInit();


        System.out.println("Completed Fancy Cinemas System Initialization.");

        //default page
        System.out.println("Welcome to the Fancy Cinemas Movie Booking System!");
        System.out.println("Movies");
        for (Cinema c : validCinemas) {
            System.out.println("-------------------------------------------------------");
            System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());
            System.out.println("-------------------------------------------------------");
            for (Movie m : c.getMovies()) {
                System.out.println(m.getName());
                System.out.println(m.getSchedule());
            }
        }

        boolean isGuest = false;
        boolean isCustomer = false;
        String selectedMovie = "";

        //check if guest or customerSO
        String input = u.checkUser();

        switch (input) {
            case "1":
                isGuest = true;
                break;
            case "2":
                isCustomer = true;
                boolean loggedin = u.login_func(customers);
                break;
            default:
                System.out.println("Invalid Input, please try again.\n");
                break;
        }


        //guest function
        while(isGuest && !isCustomer){
            //prompt guest to filter by movie, cinema, or screen size
            input = u.promptGuest();

            switch (input) {
                case "1":
                    //prompt guest to look up a movie
                    input = u.findMovie();
                    selectedMovie = input;
                    boolean found = false;

                    for (Cinema c : validCinemas) {
                        for (Movie m : c.getMovies()) {
                            //if movie is found
                            if (m.getName().toLowerCase().equals(input.toLowerCase())) {
                                found = true;
                                //print details
                                System.out.println(m.getMovieDetails());

                                //prompt guest if they want to book
                                input = u.bookMovie();

                                switch (input) {
                                    case "1":
                                        //prompt guest to make an account
                                        System.out.println("To proceed with booking, please make an account.\n");
                                        input = u.promptAccount();
                                        switch (input) {
                                            case "1":
                                                boolean signedUp = false;
                                                //prompt guest to make a new account
                                                while (!signedUp) {
                                                    //INCOMPLETE
                                                    //NEED TO IMPLEMENT: if username is already taken, reprompt
                                                    input = u.enterUsername();
                                                    if (input.equals("cancel")) {
                                                        break;
                                                    } else if (customers.containsKey(input)) {
                                                        System.out.println("Username taken. Please use another username\n");
                                                    } else {
                                                        String username = input;
                                                        input = u.enterPassword();
                                                        customers.put(username, input);
                                                        signedUp = true;
                                                        isCustomer = true;

                                                    }
                                                }
                                                break;
                                            case "2":
                                                //return guest to default page
                                                break;
                                            default:
                                                System.out.println("Invalid Input, please try again.\n");
                                        }
                                    case "2":
                                        //don't book
                                        break;
                                    default:
                                        System.out.println("Invalid Input, please try again.\n");
                                }
                            }
                        }
                    }
                    if (!found) {
                        System.out.println("Movie not found\n");
                    }
                    break;

                case "2":
                    //prompt guest to look up cinema
                    input = u.findCinema();
                    found = false;

                    for (Cinema c : validCinemas) {
                        //if cinema is found
                        if (c.getName().toLowerCase().equals(input.toLowerCase())) {
                            for (Movie m : c.getMovies()) {
                                //print movie name
                                System.out.println(m.getName());
                                found = true;
                            }
                        }
                    }
                    if (found = false) {
                        System.out.println("Cinema not found\n");
                    }
                    break;

                case "3":
                    //prompt guest to look up screen size
                    input = u.findScreen();
                    found = false;

                    for (Cinema c : validCinemas) {
                        for (Movie m : c.getMovies()) {
                            //if screen size is found
                            if (m.getScreenSize().toLowerCase().equals(input.toLowerCase())) {
                                //print cinema name + location
                                System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());

                                //print movie name
                                System.out.println(m.getName());
                                found = true;
                            }
                        }
                    }
                    if (found = false) {
                        System.out.println("Invalid screen size\n");
                    }
                    break;

                default:
                    System.out.println("Invalid Input, please try again.\n");
                    break;
            }
        }
        //customer function
        while (isCustomer) {
            //if user is a guest who has made an account to book a movie
            if (isGuest) {
                //finish booking
            }

            //prompt customer to filter by movie, cinema, or screen size
            input = u.promptCustomer();

            switch (input) {
                case "1":
                    //prompt user to look up a movie
                    input = u.findMovie();
                    selectedMovie = input;
                    boolean found = false;
                    for (Cinema c : validCinemas) {
                        for (Movie m : c.getMovies()) {
                            //if movie is found
                            if (m.getName().toLowerCase().equals(input.toLowerCase())) {
                                found = true;
                                //print details
                                System.out.println(m.getMovieDetails());

                                //prompt customer if they want to book
                                input = u.bookMovie();

                                switch (input) {
                                    case "1":
                                        //finish booking
                                        break;
                                    case "2":
                                        //don't book
                                        break;
                                    default:
                                        System.out.println("Invalid Input, please try again.\n");
                                        break;
                                }
                            }
                        }
                    }
                    if (!found) {
                        System.out.println("Movie not found\n");
                    }
                    break;
                case "2":
                    //prompt customer to look up cinema
                    input = u.findCinema();
                    found = false;
                    for (Cinema c : validCinemas) {
                        //if cinema is found
                        if (c.getName().toLowerCase().equals(input.toLowerCase())) {
                            for (Movie m : c.getMovies()) {
                                //print movie name
                                System.out.println(m.getName());
                            }
                            found = true;
                        }
                    }
                    if (found = false) {
                        System.out.println("Cinema not found\n");
                    }
                    break;
                case "3":
                    //prompt customer to look up screen size
                    input = u.findScreen();

                    for (Cinema c : validCinemas) {
                        for (Movie m : c.getMovies()) {
                            //if screen size is found
                            if (m.getScreenSize().toLowerCase().equals(input.toLowerCase())) {
                                //print cinema name + location
                                System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());

                                //print movie name
                                System.out.println(m.getName());
                            }
                        }

                    }
                    break;
                case "4":
                    System.out.println("Logging out...\n");
                    isCustomer = false;
                    break;
                default:
                    System.out.println("Invalid Input, please try again.\n");
                    break;
            }

        }
    }
}