package Proj2;

import java.util.*;

public class CinemaRunner {
    public static ArrayList<Movie> validMovies;
    public static HashMap<String, String> customers = new HashMap<>();


    public static void main(String[] args) {
        UserInput u = new UserInput(System.in, System.out);
        System.out.println("Initialising Cinema System...");
        //Hardcoded boy
        customers.put("Feedback","Assign1?");
        //reads in all movies
        validMovies = u.movieInit();

        //read in all cinemas
        ArrayList<Cinema> validCinemas;
        validCinemas = u.cinemaInit();

        //reads in all cards
        //gonna need to make changes for JSON
        ArrayList<Card> validCards;
        validCards = u.cardInit();

        //read in all gift cards
        ArrayList<GiftCard> validGiftCards;
        validGiftCards = u.giftCardInit();

        //read in all customers
        ArrayList<Customer> validCustomers;
        validCustomers = u.customerInit(validCards);


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
        boolean loggedIn;
        String selectedMovie = "";

        boolean running = true;
        while(running) {
            //check if guest or customer
            String input = u.checkUser();
            boolean notQuit = true;
            switch (input) {
                case "1":
                    isGuest = true;
                    isCustomer = false;
                    break;
                case "2":
                    loggedIn = u.promptLogin(customers);

                    //if log in was successful

                    if (loggedIn) {
                        isCustomer = true;

                        //MAKE CUSTOMER OBJECT FROM CORRESPONDING CUSTOMER FROM CUSTOMERS FILE
                        Customer customer;
                    } else {
                        isCustomer = false;
                    }
                
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    notQuit = false;
                    System.out.println("Invalid Input, please try again.\n");
                    break;
            }

            //guest function
            while (notQuit) {

                //prompt USER to filter by movie, cinema, or screen size
                input = u.promptUser();

                switch (input) {
                    case "1":
                        //prompt guest to look up a movie
                        input = u.findMovie();
                        selectedMovie = input;
                        boolean found = false;

                        for (Cinema c : validCinemas) {
                            for (Movie m : c.getMovies()) {
                                //if movie is found
                                if (m.getName().equalsIgnoreCase(input)) {
                                    found = true;
                                    //print details
                                    System.out.println(m.getMovieDetails());

                                    //prompt guest if they want to book
                                    if (isCustomer) {
                                        System.out.println("Placeholder Print for Customer Fucntionality");
                                        int numPeople = u.getNumPeople();

                                        //NEED TO DISPLAY NUMBER OF SEATS AVAILABLE FOR EACH
                                        int numF = u.promptFSeats(m.getF_seatsOpen());
                                        int numM = u.promptMSeats(m.getM_seatsOpen());
                                        int numR = u.promptRSeats(m.getR_seatsOpen());

                                        Schedule s = m.getScheduleObj();

                                        for(String time : s.getUpcomingTimes()){
                                            input = u.promptTime(time);

                                            switch(input){
                                                case "1":
                                                    //FIX ACCORDINGLY
                                                    m.bookCustomer(customer, input, numPeople, numF, numM, numR);
                                                case "2":
                                                    break;
                                                default:
                                                    System.out.println("Invalid Input, please try again.\n");
                                            }


                                        }
                                    }
                                    else {
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
                                                            input = u.enterUsername();

                                                            if (input.equalsIgnoreCase("cancel")) {
                                                                break;
                                                            } else if (customers.containsKey(input)) {
                                                                System.out.println("Username taken. Please use another username.\n");
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
                            if (c.getName().equalsIgnoreCase(input)) {
                                for (Movie m : c.getMovies()) {
                                    //print movie name
                                    System.out.println(m.getName());
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
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
                                if (m.getScreenSize().equalsIgnoreCase(input)) {
                                    //print cinema name + location
                                    System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());

                                    //print movie name
                                    System.out.println(m.getName());
                                    found = true;
                                }
                            }
                        }
                        if (!found) {
                            System.out.println("Invalid screen size\n");
                        }
                        break;
                    case "4":
                        notQuit = false;
                        break;

                    default:
                        System.out.println("Invalid Input, please try again.\n");
                        break;
                }
            }

        }
        u.giftCardSave(validGiftCards);
        u.customerSave(validCustomers);
    }
}