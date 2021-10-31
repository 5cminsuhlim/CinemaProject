package Proj2;

import java.math.BigDecimal;
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
                            boolean cinemaFound = false;
                            int count = 0;
                            ArrayList<MovieInstance> foundMovieInstance = new ArrayList<MovieInstance>();
                            HashMap<MovieInstance, Cinema> foundMCInstance = new HashMap<>();

                            for (Cinema c : validCinemas) {
                                cinemaFound = false;
                                System.out.println("-------------------------------------------------------");
                                System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());
                                System.out.println("-------------------------------------------------------");

                                for (MovieInstance m : c.getMovies()) {
                                    //if movie is found
                                    if (m.getName().equalsIgnoreCase(input)) {
                                        count++;
                                        found = true;
                                        cinemaFound = true;
                                        foundMCInstance.put(m, c);
                                        foundMovieInstance.add(m);
                                        System.out.println("Option " + count + ":");
                                        System.out.println(m.getSchedule() + ", " + m.getScreenSize() + "\n");
                                    }
                                }
                                if (!cinemaFound) {
                                    System.out.println("Movie unavailable at this Cinema.\n");
                                }
                            }
                            if (!found) {
                                System.out.println("Invalid input, please try another movie.\n");
                                break;
                            }

                            if(foundMovieInstance.size() > 0) {
                                System.out.println(foundMovieInstance.get(0).getMovieDetails() + "\n");
                            }

                            boolean isValid = false;

                            while(!isValid) {
                                input = u.promptChoice();

                                if(input.length() == 0){
                                    System.out.println("Invalid Input, please try again.\n");
                                    break;
                                }

                                if (input.equalsIgnoreCase("cancel")) {
                                    break;
                                }
                                else if (Integer.parseInt(input) < 1 || Integer.parseInt(input) > foundMovieInstance.size()) {
                                    System.out.println("Invalid input, please try again.\n");
                                }
                                else {
                                    isValid = true;
                                }
                            }

                            if(!isValid) {
                                break;
                            }
                            MovieInstance wantedMov = foundMovieInstance.get(Integer.parseInt(input) - 1);

                            //start booking for movieinstance at input - 1
                            //people
                            //movieinstance
                            //cards / giftcards
                            if (isCustomer) {
                                u.book(wantedMov, foundMCInstance.get(wantedMov), validCards, validGiftCards, customer);
                            }
                            //guest
                            else {
                                System.out.println("-------------------------------------------------------");
                                System.out.println("Cinema Name: " + foundMCInstance.get(wantedMov).getName() +
                                        "\nLocation: " + foundMCInstance.get(wantedMov).getLocation());
                                System.out.println("-------------------------------------------------------");
                                System.out.println(wantedMov.getSchedule() + ", " + wantedMov.getScreenSize() + "\n");

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
                                                    }
                                                    else {
                                                        String username = input;

                                                        input = u.enterPasswordGuest();

                                                        Customer newCustomer = new Customer(username, input, null, null);
                                                        validCustomers.add(newCustomer);
                                                        signedUp = true;
                                                        isCustomer = true;

                                                        u.book(wantedMov, foundMCInstance.get(wantedMov), validCards, validGiftCards, newCustomer);
                                                    }
                                                }
                                                break;
                                            case "2":
                                                //return guest to default page
                                                break;
                                            default:
                                                System.out.println("Invalid Input, please try again.\n");
                                        }
                                        break;
                                    case "2":
                                        //don't book
                                        break;
                                    default:
                                        System.out.println("Invalid Input, please try again.\n");
                                }

                            }
                            break;

                        case "2":
                            //prompt guest to look up cinema
                            input = u.findCinema();
                            Cinema wantedCinema = null;
                            cinemaFound = false;
                            ArrayList<Cinema> foundCinemas = new ArrayList<Cinema>();

                            for (Cinema c : validCinemas) {
                                if (c.getName().equalsIgnoreCase(input)) {
                                    foundCinemas.add(c);
                                }
                            }

                            if(foundCinemas.size() == 0) {
                                System.out.println("Cinema not found.\n");
                                break;
                            }
                            else if (foundCinemas.size() == 1){
                                wantedCinema = foundCinemas.get(0);
                            }
                            else {
                                // ask user to specify location and repeat above
                                input = u.promptLocation(foundCinemas);

                                if(input.equalsIgnoreCase("cancel")){
                                    break;
                                }

                                int index = 0;

                                for(Cinema c : foundCinemas){
                                    if(c.getLocation().equalsIgnoreCase(input)){
                                        index = foundCinemas.indexOf(c);
                                    }
                                }

                                wantedCinema = foundCinemas.get(index);
                            }

                            count = 0;
                            foundMovieInstance = new ArrayList<MovieInstance>();
                            found = false;

                            System.out.println("-------------------------------------------------------");
                            System.out.println("Cinema Name: " + wantedCinema.getName() + "\nLocation: " + wantedCinema.getLocation());
                            System.out.println("-------------------------------------------------------");

                            for (MovieInstance m : wantedCinema.getMovies()) {
                                //if movie is found
                                count++;
                                found = true;
                                foundMovieInstance.add(m);
                                System.out.println("Option " + count + ":");
                                System.out.println(m.getSchedule() + ", " + m.getScreenSize() + "\n");
                            }

                            if(!found){
                                System.out.println("No movies found.\n");
                                break;
                            }

                            isValid = false;

                            while(!isValid) {
                                input = u.promptChoice();

                                if(input.length() == 0){
                                    System.out.println("Invalid Input, please try again.\n");
                                    break;
                                }

                                if (input.equalsIgnoreCase("cancel")) {
                                    break;
                                }
                                else if (Integer.valueOf(input) < 1 || Integer.valueOf(input) > foundMovieInstance.size()) {
                                    System.out.println("Invalid input, please try again.\n");
                                }
                                else {
                                    isValid = true;
                                }
                            }

                            if(!isValid) {
                                break;
                            }

                            wantedMov = foundMovieInstance.get(Integer.valueOf(input) - 1);

                            if(foundMovieInstance.size() > 0) {
                                System.out.println(wantedMov.getMovieDetails() + "\n");
                            }

                            if (isCustomer) {
                                u.book(wantedMov, wantedCinema, validCards, validGiftCards, customer);
                            }
                            //guest
                            else {
                                System.out.println("-------------------------------------------------------");
                                System.out.println("Cinema Name: " + wantedCinema.getName() +
                                        "\nLocation: " + wantedCinema.getLocation());
                                System.out.println("-------------------------------------------------------");
                                System.out.println(wantedMov.getSchedule() + ", " + wantedMov.getScreenSize() + "\n");

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
                                                    }
                                                    else {
                                                        String username = input;

                                                        input = u.enterPasswordGuest();

                                                        Customer newCustomer = new Customer(username, input, null, null);
                                                        validCustomers.add(newCustomer);
                                                        signedUp = true;
                                                        isCustomer = true;

                                                        //add guest payment

                                                        u.book(wantedMov, wantedCinema, validCards, validGiftCards, newCustomer);
                                                    }
                                                }
                                                break;
                                            case "2":
                                                //return guest to default page
                                                break;
                                            default:
                                                System.out.println("Invalid Input, please try again.\n");
                                        }
                                        break;
                                    case "2":
                                        //don't book
                                        break;
                                    default:
                                        System.out.println("Invalid Input, please try again.\n");
                                }

                            }
                            break;

                        case "3":
                            //prompt guest to look up screen size
                            input = u.findScreen();
                            found = false;

                            if(input.length() == 0){
                                System.out.println("Invalid screen size\n");
                                break;
                            }

                            for (Cinema c : validCinemas) {
                                ArrayList<String> seen = new ArrayList<String>();
                                String curr = "";

                                //print cinema name + location
                                System.out.println("Cinema Name: " + c.getName() + "\nLocation: " + c.getLocation());

                                for (MovieInstance m : c.getMovies()) {
                                    //if screen size is found
                                    if (m.getScreenSize().equalsIgnoreCase(input)) {
                                        found = true;

                                        //print movie name
                                        curr = m.getName();

                                        if(!seen.contains(curr)){
                                            System.out.println(curr);

                                            seen.add(curr);
                                        }
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
                            ArrayList<Movie> moviesWithNewMovie = u.addMovieData(validMovies);
                            if(moviesWithNewMovie != null){
                                validMovies = moviesWithNewMovie;
                            } else {
                                System.out.println("ERROR");
                            }
                        case "2":
                            //delete movie data
                            // make sure to delete from cinema instances too

                        case "3":
                            //modify movie data
                            input = u.promptModifyMovie(validMovies);

                            if(input.length() == 0){
                                System.out.println("Invalid Input, please try again.\n");
                                break;
                            }
                            String movie = input;
                            Movie movieObj = null;

                            for(Movie m : validMovies){
                                if(m.getName().equalsIgnoreCase(movie)){
                                    movieObj = m;
                                    break;
                                }
                            }

                            input = u.promptWhatToChange(movie);

                            switch(input){
                                case "1":
                                    input = u.changeName();

                                    if(input.equalsIgnoreCase("cancel")){
                                        break;
                                    }

                                    movieObj.setName(input);
                                    break;

                                case "2":
                                    input = u.changeSynopsis();

                                    if(input.equalsIgnoreCase("cancel")){
                                        break;
                                    }

                                    movieObj.setSynopsis(input);
                                    break;

                                case "3":
                                    input = u.changeRating();

                                    if(input.equalsIgnoreCase("cancel")){
                                        break;
                                    }

                                    movieObj.setRating(input);
                                    break;

                                case "4":
                                    input = u.changeDate();

                                    if(input.equalsIgnoreCase("cancel")){
                                        break;
                                    }

                                    movieObj.setReleaseDate(input);
                                    break;

                                case "5":
                                    ArrayList<String> inputAL = u.changeCast();

                                    if(inputAL.size() > 0){
                                        movieObj.setCast(inputAL);
                                    }

                                    break;

                                case "6":
                                    input = u.changeScreenSize();
                                    String ss = input;

                                    input = u.getCinema(validCinemas);
                                    ArrayList<Cinema> foundCinemas = new ArrayList<>();
                                    Cinema wantedCinema = null;

                                    if(input.equalsIgnoreCase("cancel")){
                                        break;
                                    }

                                    for (Cinema c : validCinemas) {
                                        if (c.getName().equalsIgnoreCase(input)) {
                                            foundCinemas.add(c);
                                        }
                                    }

                                    if(foundCinemas.size() == 0) {
                                        System.out.println("Cinema not found.\n");
                                        break;
                                    }
                                    else if (foundCinemas.size() == 1){
                                        wantedCinema = foundCinemas.get(0);
                                    }
                                    else {
                                        // ask user to specify location and repeat above
                                        input = u.promptLocation(foundCinemas);

                                        if(input.equalsIgnoreCase("cancel")){
                                            break;
                                        }

                                        int index = 0;

                                        for(Cinema c : foundCinemas){
                                            if(c.getLocation().equalsIgnoreCase(input)){
                                                index = foundCinemas.indexOf(c);
                                            }
                                        }

                                        wantedCinema = foundCinemas.get(index);
                                    }

                                    for(MovieInstance m : wantedCinema.getMovies()){
                                        if(m.getName().equalsIgnoreCase(movie)){
                                            m.setScreenSize(ss);
                                        }
                                    }
                                    break;

                                case "7":
                                    input = u.changeTicketPrice();

                                    if(input.equalsIgnoreCase("cancel")){
                                        break;
                                    }

                                    for(Cinema c : validCinemas){
                                        for(MovieInstance m : c.getMovies()){
                                            //m_id or c_id?
                                            if(movieObj.getId() == m.getM_id()){
                                                m.setBasePrice(BigDecimal.valueOf(Integer.valueOf(input)));
                                            }
                                        }

                                    }
                                    break;

                                case "8":
                                    //return to default page
                                    notQuit = false;
                                    break;

                                default:
                                    System.out.println("Invalid Input, please try again.\n");
                                    break;
                            }


                        case "4":
                            //add new showings

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
                            int indexToRemove = -1;
                            for(Customer c : validCustomers){
                                if(c.getUsername().equalsIgnoreCase(input)){
                                    indexToRemove = validCustomers.indexOf(c);
                                }
                            }
                            if(indexToRemove >= 0){
                                validCustomers.remove(indexToRemove);
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
        u.cinemaSave(validCinemas);
        u.movieSave(validMovies);
    }
}