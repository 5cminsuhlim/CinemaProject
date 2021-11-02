package Proj2;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;

public class UserInput {
    private Scanner scanner;
    private PrintStream printStream;

    public UserInput(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    public String getUsername(){
        printStream.print("Please enter your username:\n");
        return scanner.nextLine();
    }

    public String getPassword(){
        String prompt = "Please enter your password:\n";

        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();
        String password = "";

        try {
            password = scanner.nextLine();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        // stop masking
        et.stopMasking();
        printStream.print(" Input Accepted *\n\n");

        // return the password entered by the user
        return password;
    }

    public boolean promptLogin(String username, String password, ArrayList<Customer> customers){
        for(Customer c : customers){
            if(c.getUsername().equalsIgnoreCase(username)){
                if(c.getPassword().equalsIgnoreCase(password)){
                    return true;
                }
                else{
                    printStream.println("You have entered the incorrect password.");
                    return false;
                }
            }
        }
        printStream.println("You have entered a username that does not exist. You have been logged in as a Guest.");
        return false;
    }

    public ArrayList<Movie> movieInit() {
        boolean movieSuccess = false;
        ArrayList<Movie> validMovies = new ArrayList<>();


        while (!movieSuccess) {
            printStream.println("Enter movie file name: ");
            String filename = scanner.nextLine();
            validMovies = Movie.readMovies(filename);

            if(validMovies != null){
                movieSuccess = true;
            }

        }
        return validMovies;
    }

    public ArrayList<Cinema> cinemaInit(ArrayList<Movie> movies) {
        boolean cinemaSuccess = false;
        ArrayList<Cinema> validCinemas = new ArrayList<>();


        while (!cinemaSuccess) {
            printStream.println("Enter cinema file name: ");
            String filename = scanner.nextLine();
            validCinemas = Cinema.readCinemas(filename, movies);

            if(validCinemas != null){
                cinemaSuccess = true;
            }

        }
        return validCinemas;
    }

    public ArrayList<Card> cardInit() {
        boolean cardSuccess = false;
        ArrayList<Card> validCards = new ArrayList<>();


        while (!cardSuccess) {
            printStream.println("Enter credit card file name: ");
            String filename = scanner.nextLine();
            validCards = Card.readCards(filename);

            if (validCards != null) {
                cardSuccess = true;
            }

        }
        return validCards;
    }

    public ArrayList<GiftCard> giftCardInit() {
        boolean giftCardSuccess = false;
        ArrayList<GiftCard> validGiftCards = new ArrayList<>();


        while (!giftCardSuccess) {
            printStream.println("Enter gift card file name: ");
            String filename = scanner.nextLine();
            validGiftCards = GiftCard.readGiftCards(filename);

            if(validGiftCards != null){
                giftCardSuccess = true;
            }

        }
        return validGiftCards;
    }

    public void giftCardSave(ArrayList<GiftCard> cards) {
        boolean saveSuccess = false;
        while(!saveSuccess) {
            printStream.println("Enter gift card file name to save ur stuff!!: ");
            String filename = scanner.nextLine();
            if (GiftCard.saveGiftCards(filename, cards) == 1){
                saveSuccess = true;
            }
        }
    }

    public ArrayList<Customer> customerInit(ArrayList<Card> cards) {
        boolean customerSuccess = false;
        ArrayList<Customer> validCustomers = new ArrayList<>();


        while (!customerSuccess) {
            printStream.println("Enter customer file name: ");
            String filename = scanner.nextLine();
            validCustomers = Customer.readCustomers(filename,cards);
            if(validCustomers != null){
                customerSuccess = true;
            }

        }
        return validCustomers;
    }

    public void customerSave(ArrayList<Customer> customers) {
        boolean saveSuccess = false;
        while(!saveSuccess) {
            printStream.println("Enter customer file name to save ur stuff!!: ");
            String filename = scanner.nextLine();
            if (Customer.saveCustomers(filename, customers) == 1){
                saveSuccess = true;
            }
        }
    }

    public void movieSave(ArrayList<Movie> movies) {
        boolean saveSuccess = false;
        while(!saveSuccess) {
            printStream.println("Enter movie file name to save ur stuff!!: ");
            String filename = scanner.nextLine();
            if (Movie.saveMovies(filename, movies) == 1){
                saveSuccess = true;
            }
        }
    }

    public void cinemaSave(ArrayList<Cinema> cinemas) {
        boolean saveSuccess = false;
        while(!saveSuccess) {
            printStream.println("Enter cinema file name to save ur stuff!!: ");
            String filename = scanner.nextLine();
            if (Cinema.saveCinemas(filename, cinemas) == 1){
                saveSuccess = true;
            }
        }
    }

    public String checkUser(){
        printStream.println("\nAre you a guest or returning user?\n" +
                "1: Guest\n" +
                "2: Returning User\n" +
                "3: Exit\n");
        return scanner.nextLine();
    }

    public String promptUser(){
        printStream.println("Filter Options:\n" +
                            "1: Movie\n" +
                            "2: Cinema\n" +
                            "3: Screen Size\n" +
                            "4: Return\n");
        return scanner.nextLine();
    }

    public String promptAccount(){
        printStream.println("1: Make New Account\n" +
                            "2: Return\n");
        return scanner.nextLine();
    }

    public String findMovie(){
        printStream.println("Please enter the movie title:");
        return scanner.nextLine();
    }

    public String promptChoice(){
        printStream.println("Please enter the option number to proceed with booking (enter 'cancel' to exit):\n");
        return scanner.nextLine();
    }

    public String promptLocation(ArrayList<Cinema> cinemas){
        boolean isValid = false;
        String loc = "";

        while(!isValid){
            System.out.println("-------------------------------------------------------");
            System.out.println("Locations: ");
            System.out.println("-------------------------------------------------------");

            for(Cinema c : cinemas){
                System.out.println(c.getLocation());
            }

            printStream.println("Please enter the desired cinema location (enter 'cancel' to exit):\n");
            loc = scanner.nextLine();

            if(loc.equalsIgnoreCase("cancel")){
                return "cancel";
            }

            for(Cinema c : cinemas){
                if(loc.equalsIgnoreCase(c.getLocation())){
                    isValid = true;
                    break;
                }
            }

            if(!isValid){
                printStream.println("Location name does not exist. Please try again.\n");
            }
        }

        return loc;
    }

    // Add option to save card detail
    public void book(MovieInstance wantedMov, Cinema cinema, ArrayList<Card> validCards, ArrayList<GiftCard> validGiftCards, Customer customer){
        int numPeople = this.getNumPeople();

        int numF = this.promptFSeats(wantedMov.getF_seatsOpen());
        int numM = this.promptMSeats(wantedMov.getM_seatsOpen());
        int numR = this.promptRSeats(wantedMov.getR_seatsOpen());

        String input = this.promptPayment();

        switch (input) {
            case "1":
                input = this.promptCardPayment();

                switch(input){
                    case "1":
                        //pay with existing card
                        //pay by card
                        input = this.getCard();
                        String cardNo = input;

                        boolean cardFound = false;

                        for (Card card : validCards) {
                            if (card.getCardNumber().equalsIgnoreCase(input)) {
                                cardFound = true;
                            }

                            if (cardFound) {
                                input = this.getName();
                                String name = input;

                                Card paymentCard = new Card(cardNo, name);

                                if (card.getCardHolderName().equalsIgnoreCase(name)) {
                                    wantedMov.bookCustomerCard(customer, cinema, paymentCard, numPeople, numF, numM, numR);
                                }
                                else {
                                    System.out.println("Invalid name. Exiting payment...\n");
                                    break;
                                }

                            }

                            if (!cardFound) {
                                System.out.println("Card not found. Exiting payment...\n");
                                break;
                            }
                        }
                        break;

                    case "2":
                        //pay with new card
                        input = this.getCard();
                        cardNo = input;

                        input = this.getName();

                        Card newCard = new Card(cardNo, input);

                        if(!validCards.contains(newCard)){
                            validCards.add(newCard);
                            customer.addCard(newCard);

                            wantedMov.bookCustomerCard(customer, cinema, newCard, numPeople, numF, numM, numR);
                        }
                        else{
                            System.out.println("Card already exists. Exiting payment...\n");
                            break;
                        }
                        break;

                    case "cancel":
                        break;

                    default:
                        System.out.println("Invalid Input, please try again.\n");
                        break;
                }
                break;

            case "2":
                //pay by gc
                input = this.getGiftCard();
                boolean giftCardFound;

                for (GiftCard g : validGiftCards) {
                    //if found and not redeemed
                    if (g.getGiftCardNumber().equalsIgnoreCase(input) && !g.isRedeemed()) {
                        giftCardFound = true;

                        if (giftCardFound) {
                            wantedMov.bookCustomerGiftCard(customer, cinema, g, numPeople, numF, numM, numR);
                            break;
                        }
                    } else if (g.getGiftCardNumber().equalsIgnoreCase(input) && g.isRedeemed()) {
                        System.out.println("This gift card has already been redeemed. Exiting payment...\n");
                        break;
                    }
                }
                System.out.println("Gift Card not found. Exiting payment...\n");
                break;

            case "cancel":
                break;

            default:
                System.out.println("Invalid Input, please try again.\n");
                break;
        }
    }

    public int getNumPeople(){
        int count = 0;
            try {
                printStream.println("Please enter the number of children (under 12 years old):");
                count += Integer.parseInt(scanner.nextLine());

                printStream.println("Please enter the number of students:");
                count += Integer.parseInt(scanner.nextLine());

                printStream.println("Please enter the number of adults:");
                count += Integer.parseInt(scanner.nextLine());

                printStream.println("Please enter the number of seniors / pensioners:");
                count += Integer.parseInt(scanner.nextLine());

                return count;
            }
            catch(Exception e){
                return -1;
            }
    }

    public int promptFSeats(int openF){
        boolean isValid = false;
        int count = 0;

        while(!isValid){
            printStream.println("Number of Front Seats Available: " + openF);
            printStream.println("Please enter the number of front seats to book:");
            try {
                count = Integer.parseInt(scanner.nextLine());

                if (count <= openF) {
                    isValid = true;
                } else {
                    printStream.println("Invalid input, please try again.");
                }
            } catch(Exception e){
                printStream.println("Invalid input, please try again.");
            }
        }

        return count;
    }

    public int promptMSeats(int openM){
        boolean isValid = false;
        int count = 0;

        while(!isValid){
            printStream.println("Number of Middle Seats Available: " + openM);
            printStream.println("Please enter the number of front seats to book:");
            try {
                count = Integer.parseInt(scanner.nextLine());

                if (count <= openM) {
                    isValid = true;
                } else {
                    printStream.println("Invalid input, please try again.");
                }
            }catch(Exception e){
                printStream.println("Invalid input, please try again.");
            }
        }

        return count;
    }

    public int promptRSeats(int openR){
        boolean isValid = false;
        int count = 0;

        while(!isValid){
            printStream.println("Number of Rear Seats Available: " + openR);
            printStream.println("Please enter the number of front seats to book:");
            try{
                count = Integer.parseInt(scanner.nextLine());

                if(count <= openR){
                    isValid = true;
                }
                else{
                    printStream.println("Invalid input, please try again.");
                }
            }catch(Exception e){
                printStream.println("Invalid input, please try again.");
            }
        }

        return count;
    }

    public String promptTime(String t){
        printStream.println("Would you like to book for " + t + "?\n" +
                            "1: Yes\n" +
                            "2: No\n");
        return scanner.nextLine();
    }

    public String promptPayment(){
        printStream.println("Please select a payment method (enter 'cancel' to exit):\n" +
                            "1: Card\n" +
                            "2: Gift Card\n");
        return scanner.nextLine();
    }

    public String promptCardPayment(){
        printStream.println("Please select a card payment option (enter 'cancel' to exit):\n" +
                "1: Pay with Existing Card\n" +
                "2: Pay with New Card\n");
        return scanner.nextLine();
    }

    public String getCard(){
        String prompt = "Please enter your card number:\n";

        EraserThread et = new EraserThread(prompt);
        Thread mask = new Thread(et);
        mask.start();
        String cardDetail = "";

        try {
            cardDetail = scanner.nextLine();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        // stop masking
        et.stopMasking();
        printStream.print(" Input Accepted *\n\n");

        // return the password entered by the user
        return cardDetail;
    }

    public String getName(){
        printStream.println("Please enter your name associated with the card:\n");
        return scanner.nextLine();
    }

    public String getGiftCard(){
        printStream.println("Please enter the gift card code:\n");
        return scanner.nextLine();
    }

    public String bookMovie(){
        printStream.println("Would you like to book this movie?\n" +
                            "1: Yes\n" +
                            "2: No\n");
        return scanner.nextLine();
    }

    public String enterUsernameGuest(ArrayList<Customer> customers){
        boolean isValid = false;
        boolean exists = false;
        String desiredUsername = "";

        while(!isValid){
            exists = false;

            printStream.println("Please enter your desired username (enter 'cancel' to exit):\n");

            desiredUsername = scanner.nextLine();

            for(Customer c : customers){
                if(c.getUsername().equalsIgnoreCase(desiredUsername)){
                    exists = true;
                }
            }

            if(exists){
                printStream.println("Username taken. Please use another username.\n");
            }
            else{
                isValid = true;
            }
        }

        return desiredUsername;
    }

    public String enterPasswordGuest(){ //need to somehow hide password with *****
        boolean isValid = false;
        String pw = "";

        while(!isValid){
            printStream.println("Please enter your desired password (at least 6 characters):\n");
            pw = scanner.nextLine();

            if(pw.length() < 6){
                printStream.println("Insufficient password length. Please try again.");
            }
            else{
                isValid = true;
            }
        }

        return pw;
    }

    public String findCinema(){
        printStream.println("Please enter the cinema name:\n");
        return scanner.nextLine();
    }

    public String findScreen(){
        printStream.println("Please enter the screen size:\n");
        return scanner.nextLine();
    }

    public String promptAdmin(){
        printStream.println("Please enter desired action:\n" +
                "1: Insert Movie Data\n" +
                "2: Delete Movie Data\n" +
                "3: Modify Movie Data\n" +
                "4: Add New Show\n" +
                "5: Insert New Giftcard\n" +
                "6: Add Cinema Staff\n" +
                "7: FIRE Cinema Staff\n" +
                "8: Get Report\n" +
                "9: Return\n");
        return scanner.nextLine();
    }

    public String promptModifyMovie(ArrayList<Movie> movies){
        boolean found = false;
        String movie = "";

        while(!found){
            printStream.println("Please enter the movie to modify:\n");
            movie = scanner.nextLine();

            for(Movie m : movies){
                if(m.getName().equalsIgnoreCase(movie)){
                    found = true;
                    break;
                }
            }
        }

        return movie;
    }

    public Movie promptDeleteMovie(ArrayList<Movie> movies){
        boolean found = false;
        String movie = "";
        Movie mov = null;
        while(!found){
            printStream.println("Please enter the movie to delete:\n");
            movie = scanner.nextLine();
            for(Movie m : movies){
                if(m.getName().equalsIgnoreCase(movie)){
                    found = true;
                    mov = m;
                    break;
                }
            }
        }
        return mov;
    }

    public String promptDeleteShowingOrMovie(){
        while(true) {
            printStream.println("Delete whole movie [1] or a single showing [2] (or 'cancel')?\n");
            String selection = scanner.nextLine();
            if (selection.equalsIgnoreCase("cancel")) {
                return "cancel";
            } else if (selection.equalsIgnoreCase("1")) {
                return "1";
            } else if (selection.equalsIgnoreCase("2")) {
                return "2";
            } else {
                printStream.println("Invalid selection, please try again.\n");
            }
        }
    }

    public ArrayList<Cinema> promptSingleInstanceToDelete(ArrayList<Cinema> cinemas){
        while(true) {
            printStream.println("Which instance would you like to delete? Please use the form\n" +
                    "[cinema_location]:[movie_name]:[day]:[time_HH] or 'cancel':\n");
            try{
                String[] selection = scanner.nextLine().split(":");
                if(selection[0].equalsIgnoreCase("cancel")){
                    return null;
                }
                String timeStr = selection[3] + ":00:00";
                LocalTime time = LocalTime.parse(timeStr);
                for (Cinema c : cinemas) {
                    if (c.getLocation().equalsIgnoreCase(selection[0])) {
                        for (MovieInstance m : c.getMovies()) {
                            if (m.getName().equalsIgnoreCase(selection[1])
                                    && m.getDay().equalsIgnoreCase(selection[2])
                                    && (m.getTime().compareTo(time) == 0)) {
                                c.getMovies().remove(m);
                                return cinemas;
                            }
                        }
                    }
                }
            } catch (Exception e){
                printStream.println("Invalid Input, please try again.\n");
            }
        }
    }

    public String promptWhatToChange(String movieToChange){
        printStream.println("Please enter what to change about " + movieToChange + ":\n" +
                "1: Modify Movie Name\n" +
                "2: Modify Movie Synopsis\n" +
                "3: Modify Movie Rating\n" +
                "4: Modify Movie Release Date\n" +
                "5: Modify Movie Cast\n" +
                "6: Modify Movie Screen Size\n" +
                "7: Modify Movie Ticket Price\n" +
                "8: Return\n");
        return scanner.nextLine();
    }


    public String changeName(){
        boolean isValid = false;
        String change = "";

        while(!isValid){
            printStream.println("Please enter the new movie name ('cancel' to exit):");
            change = scanner.nextLine();

            if(change.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(change.length() == 0){
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
            }
        }
        return change;
    }

    public String changeSynopsis(){
        boolean isValid = false;
        String change = "";

        while(!isValid){
            printStream.println("Please enter the new movie synopsis ('cancel' to exit):");
            change = scanner.nextLine();

            if(change.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(change.length() == 0){
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
            }
        }
        return change;
    }

    public String changeRating(){
        boolean isValid = false;
        String change = "";
        List<String> ratings = Arrays.asList("G", "PG", "M", "MA15+", "R18+");

        while(!isValid){
            printStream.println("Please enter the new movie rating from the following list [G, PG, M, MA15+, R18+] ('cancel' to exit):");
            change = scanner.nextLine();

            if(change.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(change.length() == 0 || !ratings.contains(change)) {
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
            }
        }
        return change;
    }

    public String changeDate(){
        boolean isValid = false;
        String change = "";

        while(!isValid){
            printStream.println("Please enter the new movie release date [DD/MM/YYYY] ('cancel' to exit):");
            change = scanner.nextLine();

            if(change.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(change.length() == 0){
                printStream.println("Invalid input, please try again.");
            }
            else{
                try {
                    LocalDate.parse(change);
                    isValid = true;
                } catch(Exception e){
                    printStream.println("Invalid input, please try again.");
                    printStream.println("Please enter the release date [DD/MM/YYYY]: ");
                    change = scanner.nextLine();
                }
            }

        }
        return change;
    }

    public ArrayList<String> changeCast() {
        ArrayList<String> castList = new ArrayList<>();
        printStream.println("Please enter the new movie cast (enter 'cancel' to exit): ");
        while(true) {
            String cast = scanner.nextLine();
            if (cast.equalsIgnoreCase("cancel")) {
                break;
            } else{
                castList.add(cast);
            }
        }

        return castList;
    }

    public String changeScreenSize(){
        boolean isValid = false;
        String change = "";

        while(!isValid){
            printStream.println("Please enter the new movie screen size ('cancel' to exit):");
            change = scanner.nextLine();

            if(change.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(change.length() == 0){
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
            }
        }
        return change;
    }

    public String getCinema(ArrayList<Cinema> cinemas){
        boolean isValid = false;
        String cinema = "";

        while(!isValid){
            printStream.println("Please select the cinema in which the selected movie's screen size should be changed ('cancel' to exit):");
            cinema = scanner.nextLine();

            if(cinema.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(cinema.length() == 0){
                printStream.println("Invalid input, please try again.");
            }

            for(Cinema c : cinemas){
                //finds cinema with matching name
                if(cinema.equalsIgnoreCase(c.getName())){
                    isValid = true;
                    break;
                }
            }
        }
        return cinema;
    }

    public String changeTicketPrice(){
        boolean isValid = false;
        String change = "";

        while(!isValid){
            printStream.println("Please enter the new movie ticket price ('cancel' to exit):");
            change = scanner.nextLine();

            if(change.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(change.length() == 0){
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
            }
        }
        return change;
    }

    public String findMovieScreenings(ArrayList<Cinema> cinemas){
        boolean isValid = false;
        String movie = "";

        while(!isValid){
            printStream.println("Please enter the movie title for which you want to add showings ('cancel' to exit):");
            movie = scanner.nextLine();

            if(movie.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(movie.length() == 0){
                printStream.println("Invalid input, please try again.");
            }

            for(Cinema c : cinemas){
                //finds cinema with matching name
                for(MovieInstance m : c.getMovies()){
                    if(movie.equalsIgnoreCase(m.getName())){
                        isValid = true;
                        break;
                    }
                }
            }
        }
        return movie;
    }

    public String promptScreeningDay(){
        boolean isValid = false;
        String day = "";
        List<String> days = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

        while(!isValid){
            printStream.println("Please enter the new showing day [Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday] ('cancel' to exit):");
            day = scanner.nextLine();

            if(day.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(day.length() == 0 || !days.contains(day)){
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
            }
        }
        return day;
    }

    public String promptScreeningTime(String day){
        boolean isValid = false;
        String time = "";

        while(!isValid){
            printStream.println("Please enter the screening time for " + day + " [HH:MM] ('cancel' to exit):");
            time = scanner.nextLine();

            if(time.equalsIgnoreCase("cancel")){
                return "cancel";
            }
            else if(time.length() == 0 || time.length() != 5){
                printStream.println("Invalid input, please try again.");
            }
            else{
                isValid = true;
                break;
            }
        }
        return time;
    }

    public String promptGiftCard(ArrayList<GiftCard> giftCards){
        boolean isNew = false;
        boolean isValid = false;
        String code = "";

        //check if code format is valid
        while(!isValid && !isNew){
            isValid = false;

            printStream.println("Please enter the gift card code:\n");
            code = scanner.nextLine();

            if(code.length() < 18){
                printStream.println("Invalid gift card code length. Please try again.\n");
                continue;
            }

            String nums = code.substring(0, 16);
            String end = code.substring(16);

            if(nums.matches("[0-9]+") && end.equalsIgnoreCase("GC")){
                isValid = true;
            }

            if(!isValid){
                printStream.println("Invalid gift card format.");
            }
            else{
                //check if gc already exists
                for(GiftCard gc : giftCards){
                    isNew = true;

                    if(gc.getGiftCardNumber().equalsIgnoreCase(code)) {
                        printStream.println("Gift Card already exists.");
                        isNew = false;
                        break;
                    }
                }
            }
        }

        return code;
    }

    public String promptAddStaff(ArrayList<Customer> customers){
        boolean isNew = false;
        boolean isValid = false;
        String username = "";

        //check if username format is valid
        while(!isValid && !isNew){
            isValid = false;

            printStream.println("Please enter the username for the new staff:\n");
            username = scanner.nextLine();

            if(username.matches("^Staff+[0-9]+")){
                isValid = true;
            }

            if(!isValid){
                printStream.println("Invalid staff username format.");
            }
            else{
                //check if username already exists
                for(Customer c : customers){
                    isNew = true;

                    if(c.getUsername().equalsIgnoreCase(username)){
                        printStream.println("Staff username already exists.");
                        isNew = false;
                        break;
                    }
                }
            }
        }

        return username;
    }

    public String promptRemoveStaff(ArrayList<Customer> customers){
        boolean isFound = false;
        boolean isValid = false;
        String username = "";

        //check if username format is valid
        while(!isValid && !isFound){
            isValid = false;

            printStream.println("Please enter the username for the staff being fired:\n");
            username = scanner.nextLine();

            if(username.matches("^Staff+[0-9]+")){
                isValid = true;
            }

            if(!isValid){
                printStream.println("Invalid staff username format.");
            }
            else{
                //check if username already exists
                for(Customer c : customers){
                    if(c.getUsername().equalsIgnoreCase(username)){
                        isFound = true;
                        break;
                    }
                }

                if(!isFound){
                    printStream.println("Staff username does not exist.");
                }
            }
        }

        return username;
    }

    public ArrayList<Movie> addMovieData(ArrayList<Movie> movies){
        List<String> ratings = Arrays.asList("G", "PG", "M", "MA15+", "R18+");

        printStream.println("Please enter movie name: ");
        String name = scanner.nextLine();
        printStream.println("Please enter the movie's synopsis: ");
        String synopsis = scanner.nextLine();
        printStream.println("Please select the movie's rating from the following list [G, PG, M, MA15+, R18+]: ");
        String rating = scanner.nextLine();
        while(!ratings.contains(rating)){
            printStream.println("Incorrect input");
            printStream.println("Please select a rating from the following list [G, PG, M, MA15+, R18+]: ");
            rating = scanner.nextLine();
        }
        printStream.println("Please enter the movie's release date [DD/MM/YYYY]: ");
        boolean success = false;
        String date = scanner.nextLine();
        while(!success){
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate.parse(date, formatter);
                success = true;
            } catch(Exception e){
                printStream.println("Invalid date");
                printStream.println("Please enter the release date [DD/MM/YYYY]: ");
                date = scanner.nextLine();
            }
        }
        ArrayList<String> castList = new ArrayList<>();
        printStream.println("Please enter the movie's cast (enter 'cancel' to exit): ");
        while(true) {
            String cast = scanner.nextLine();
            if (cast.equalsIgnoreCase("cancel")) {
                break;
            } else{
                castList.add(cast);
            }
        }

        for(int i = 1; i <= 99; i++){
            if(movies.get(i).getId() != i){
                Movie newMovie = new Movie(i, name, synopsis, rating, date, castList);
                movies.add(i, newMovie);
                break;
            }
        }
        return movies;
    }
}