package Proj2;

import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

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
        printStream.print("Please enter your password:\n");
        return scanner.nextLine();
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
            validMovies = Movie.readMovies("../movies.txt");

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
            validCinemas = Cinema.readCinemas("../cinemas.txt", movies);

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
            validCards = Card.readCards("../cards.txt");

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
            validGiftCards = GiftCard.readGiftCards("../giftcards.txt");

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
            validCustomers = Customer.readCustomers("../customers.txt",cards);

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

    public String checkUser(){
        printStream.println("Are you a guest or returning user?\n" +
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

    public String getCard(){
        printStream.println("Please enter your card number:\n");
        return scanner.nextLine();
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
}