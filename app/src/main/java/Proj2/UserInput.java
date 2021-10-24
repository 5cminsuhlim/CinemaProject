package Proj2;

import java.io.InputStream;
import java.io.PrintStream;
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

    public boolean promptLogin(HashMap<String, String> customers){
        printStream.println("Hello! Welcome Back! \n Please Enter your Login Deatil Below!");
        printStream.print("Username: ");
        String username = scanner.nextLine();
        printStream.print("Password: ");
        String password = scanner.nextLine();
        if (customers.containsKey(username)){
            if (customers.get(username).equalsIgnoreCase(password)){
                return true;
            }
            printStream.println("You have entered the incorrect password: ");
            return false;
        }
        printStream.println("You have entered a username that does not exist.");
        return true;

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

    public ArrayList<Cinema> cinemaInit() {
        boolean cinemaSuccess = false;
        ArrayList<Cinema> validCinemas = new ArrayList<>();


        while (!cinemaSuccess) {
            printStream.println("Enter cinema file name: ");
            String filename = scanner.nextLine();
            validCinemas = Cinema.readCinemas(filename);

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

    public String checkUser(){
        printStream.println("Are you a guest or returning user?\n" +
                "1: Guest\n" +
                "2: Returning User\n");
        return scanner.nextLine();
    }

    public String promptGuest(){
        printStream.println("Filter Options:\n" +
                            "1: Movie\n" +
                            "2: Cinema\n" +
                            "3: Screen Size\n" +
                            "4: Logout\n");
        return scanner.nextLine();
    }

    public String promptCustomer(){
        printStream.println("Filter Options:\n" +
                            "1: Movie\n" +
                            "2: Cinema\n" +
                            "3: Screen Size\n" +
                            "4: Log out");
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

    public int promptFSeats(){
        printStream.println("Please enter the number of front seats to book:");
        return Integer.parseInt(scanner.nextLine());
    }

    public int promptMSeats(){
        printStream.println("Please enter the number of middle seats to book:");
        return Integer.parseInt(scanner.nextLine());
    }

    public int promptRSeats(){
        printStream.println("Please enter the number of rear seats to book:");
        return Integer.parseInt(scanner.nextLine());
    }

    public String bookMovie(){
        printStream.println("Would you like to book this movie?\n" +
                            "1: Yes\n" +
                            "2: No\n");
        return scanner.nextLine();
    }

    public String enterUsername(){
        printStream.println("Please enter your desired username (enter 'cancel' to exit):\n");
        return scanner.nextLine();
    }

    public String enterPassword(){ //need to somehow hide password with *****
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
}