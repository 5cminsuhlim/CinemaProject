package Proj2;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInput {
    private Scanner scanner;
    private PrintStream printStream;

    public UserInput(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
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
            printStream.println("Enter movie file name: ");
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
            printStream.println("Enter movie file name: ");
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
            printStream.println("Enter movie file name: ");
            String filename = scanner.nextLine();
            validGiftCards = GiftCard.readGiftCards(filename);

            if(validGiftCards != null){
                giftCardSuccess = true;
            }

        }
        return validGiftCards;
    }

    public String promptGuest(){
        printStream.println("Options: " +
                            "\n1: View Movie Details" +
                            "\n2: Change Cinema");
        return scanner.nextLine();
    }

    public String findMovie(){
        printStream.println("Please enter the movie title:\n");
        return scanner.nextLine();
    }

    public String findCinema(){
        printStream.println("Please enter the cinema name:\n");
        return scanner.nextLine();
    }
}