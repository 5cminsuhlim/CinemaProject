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
        validCinemas = u.cinemaInit(validMovies);

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
            System.out.println(c.getSchedule());
        }

        boolean isGuest = false;
        boolean isCustomer = false;
        boolean isStaff = false;
        boolean isManager = false;
        boolean firstLogin = false;
        boolean loggedIn;
        String selectedMovie = "";
        Customer customer = null;

        boolean running = true;
        while(running) {
            isGuest = false;
            isCustomer = false;
            isStaff = false;
            isManager = false;
            firstLogin = true;


            //check if guest or customer
            String input = u.checkUser();
            boolean notQuit = true;
            switch (input) {
                case "1":
                    isGuest = true;
                    break;

                case "2":
                    String username = u.getUsername();
                    String password = u.getPassword();

                    //staff usernames must start with "Staff" followed by 1+ ints
                    if(username.matches("^Staff+[0-9]+") && password.equalsIgnoreCase("defstaffnotsus")){
                        isStaff = true;
                        break;
                    }
                    else if(username.equalsIgnoreCase("Manager") && password.equalsIgnoreCase("iambigman")){
                        isManager = true;
                        break;
                    }

                    loggedIn = u.promptLogin(username, password, validCustomers);

                    //if log in was successful
                    if (loggedIn) {
                        isCustomer = true;

                        for(Customer c : validCustomers){
                            if(c.getUsername().equalsIgnoreCase(username)) {
                                if (c.getPassword().equalsIgnoreCase(password)) {
                                    customer = c;
                                }
                            }
                        }
                    }
                    else {
                        isCustomer = false;
                        isGuest = true;
                    }
                
                    break;

                case "3":
                    running = false;
                    notQuit = false;
                    break;

                default:
                    notQuit = false;
                    System.out.println("Invalid Input, please try again.\n");
                    break;
            }

            while (notQuit) {
                //guest and customer functionality
                if(isGuest || isCustomer) {
                    //prompt USER to filter by movie, cinema, or screen size
                    input = u.promptUser();

                    switch (input) {
                        case "1":
                            //prompt guest to look up a movie
                            input = u.findMovie();
                            selectedMovie = input;
                            boolean found = false;

                            for (Cinema c : validCinemas) {
                                for (MovieInstance m : c.getMovies()) {
                                    //if movie is found
                                    if (m.getName().equalsIgnoreCase(input)) {
                                        found = true;
                                        //print details
                                        System.out.println(m.getMovieDetails());

                                        //prompt guest if they want to book
                                        if (isCustomer) {
                                            System.out.println("Placeholder Print for Customer Fucntionality");
                                            int numPeople = u.getNumPeople();

                                            int numF = u.promptFSeats(m.getF_seatsOpen());
                                            int numM = u.promptMSeats(m.getM_seatsOpen());
                                            int numR = u.promptRSeats(m.getR_seatsOpen());
                                            ArrayList<String> times = new ArrayList<>();
                                            for (MovieInstance mov : c.getMovies()) {
                                                if(mov.getName().equalsIgnoreCase(input)){
                                                    times.add(mov.getSchedule());
                                                }
                                            }
                                            for (String t : times) {
                                                input = u.promptTime(t);
                                                String time = input;

                                                switch (input) {
                                                    case "1":
                                                        //prompt card vs. gift card
                                                        input = u.promptPayment();

                                                        switch (input) {
                                                            case "1":
                                                                //pay by card
                                                                input = u.getCard();
                                                                String cardNo = input;

                                                                boolean cardFound = false;

                                                                for (Card card : validCards) {
                                                                    if (card.getCardNumber().equalsIgnoreCase(input)) {
                                                                        cardFound = true;
                                                                    }

                                                                    if (cardFound) {
                                                                        input = u.getName();
                                                                        String name = input;

                                                                        Card paymentCard = new Card(cardNo, name);

                                                                        if (card.getCardHolderName().equalsIgnoreCase(name)) {
                                                                            m.bookCustomerCard(customer, c, time, paymentCard, numPeople, numF, numM, numR);
                                                                        } else {
                                                                            System.out.println("Invalid name. Exiting payment...\n");
                                                                        }
                                                                        break;

                                                                    }

                                                                    if (!cardFound) {
                                                                        System.out.println("Card not found. Exiting payment...\n");
                                                                        break;
                                                                    }
                                                                }

                                                            case "2":
                                                                //pay by gc
                                                                input = u.getGiftCard();
                                                                boolean giftCardFound = false;

                                                                for (GiftCard g : validGiftCards) {
                                                                    //if found and not redeemed
                                                                    if (g.getGiftCardNumber().equalsIgnoreCase(input) && !g.isRedeemed()) {
                                                                        giftCardFound = true;

                                                                        if (giftCardFound) {
                                                                            m.bookCustomerGiftCard(customer, c, time, g, numPeople, numF, numM, numR);
                                                                            break;
                                                                        }
                                                                    } else if (g.getGiftCardNumber().equalsIgnoreCase(input) && g.isRedeemed()) {
                                                                        System.out.println("This gift card has already been redeemed. Exiting payment...\n");
                                                                        break;
                                                                    }
                                                                }

                                                            case "cancel":
                                                                break;
                                                            default:
                                                                System.out.println("Invalid Input, please try again.\n");
                                                        }

                                                    case "2":
                                                        break;
                                                    default:
                                                        System.out.println("Invalid Input, please try again.\n");
                                                }


                                            }
                                        } else {
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
                                                                input = u.enterUsernameGuest(validCustomers);

                                                                if (input.equalsIgnoreCase("cancel")) {
                                                                    break;
                                                                } else {
                                                                    String username = input;

                                                                    input = u.enterPasswordGuest();

                                                                    Customer newCustomer = new Customer(username, input, null, null);
                                                                    validCustomers.add(newCustomer);
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
                                    for (MovieInstance m : c.getMovies()) {
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
                                for (MovieInstance m : c.getMovies()) {
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
                            //return to default page
                            notQuit = false;
                            break;

                        default:
                            System.out.println("Invalid Input, please try again.\n");
                            break;
                    }
                }

                //staff + manager functionality
                else if(isStaff || isManager){
                    //obtain report upon logging in
                    if(firstLogin) {
                        firstLogin = false;
                        for (Cinema c : validCinemas) {
                            System.out.println("-------------------------------------------------------");
                            System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());
                            System.out.println("-------------------------------------------------------");
                            for (MovieInstance m : c.getMovies()) {
                                System.out.println(m.getName());
                                System.out.println(m.getSchedule());
                                System.out.println("Number of bookings: " + m.getBookings());
                                int avail = m.getF_seatsOpen() + m.getM_seatsOpen() + m.getR_seatsOpen();
                                int booked = m.getF_seatsCapacity() + m.getM_seatsCapacity() + m.getR_seatsCapacity() - avail;
                                System.out.println("Seats booked: " + booked);
                                System.out.println("Seats available: " + avail);

                                if (isManager) {
                                    //report containing date + time of cancelled transactions
                                }
                            }
                        }
                    }

                    input = u.promptAdmin();

                    switch(input){
                        case "1":
                            //insert movie data

                        case "2":
                            //delete movie data

                        case "3":
                            //modify movie data

                        case "4":
                            //add new show

                        case "5":
                            //insert new giftcard
                            input = u.promptGiftCard(validGiftCards);

                            GiftCard gc = new GiftCard(input, false);
                            validGiftCards.add(gc);
                            break;

                        case "6":
                            //if staff tries to perform manager action
                            if(!isManager){
                                System.out.println("Nice try, guy. You're merely a staff.\n");
                                break;
                            }

                            //add cinema staff
                            input = u.promptAddStaff(validCustomers);

                            Customer staff = new Customer(input, "defstaffnotsus", null, null);
                            validCustomers.add(staff);
                            break;

                        case "7":
                            //if staff tries to perform manager action
                            if(!isManager){
                                System.out.println("Nice try, guy. You're merely a staff.\n");
                                break;
                            }

                            //remove cinema staff
                            input = u.promptRemoveStaff(validCustomers);

                            for(Customer c : validCustomers){
                                if(c.getUsername().equalsIgnoreCase(input)){
                                    validCustomers.remove(c);
                                }
                            }
                            break;

                        case "8":
                            for (Cinema c : validCinemas) {
                                System.out.println("-------------------------------------------------------");
                                System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());
                                System.out.println("-------------------------------------------------------");
                                for (MovieInstance m : c.getMovies()) {
                                    System.out.println(m.getName());
                                    System.out.println(m.getSchedule());
                                    System.out.println("Number of bookings: " + m.getBookings());
                                    int avail = m.getF_seatsOpen() + m.getM_seatsOpen() + m.getR_seatsOpen();
                                    int booked = m.getF_seatsCapacity() + m.getM_seatsCapacity() + m.getR_seatsCapacity() - avail;
                                    System.out.println("Seats booked: " + booked);
                                    System.out.println("Seats available: " + avail);

                                    if (isManager) {
                                        //report containing date + time of cancelled transactions
                                    }
                                }
                            }

                            break;

                        case "9":
                            //return to default page
                            notQuit = false;
                            break;

                        default:
                            System.out.println("Invalid Input, please try again.\n");
                            break;
                    }
                }
            }

        }
        u.giftCardSave(validGiftCards);
        u.customerSave(validCustomers);
    }
}