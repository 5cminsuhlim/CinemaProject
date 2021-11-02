/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Proj2;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

class CinemaTest {
    /*
    @Test
    public void mainTest(){
        InputStream stdin = System.in;
        System.setIn(new ByteArrayInputStream("3\n4\n".getBytes()));

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        PrintStream stdout = System.out;
        System.setOut(ps);

        UserInputExample.main(new String[0]);

        System.setIn(stdin);
        System.setOut(stdout);

        String outputText = byteArrayOutputStream.toString();
        String key = "output:";
        String output = outputText.substring(outputText.indexOf(key) + key.length()).trim();
        Assert.assertEquals(output, "7");
    }*/

    @Test
    public void testNameSearch(){
        int id = 1;
        String name = "Steve Buscemi 10 Hour Compilation";
        String synopsis = "As above"; //plot
        String rating = "10"; //rating
        String releaseDate = "10/10/10"; //?
        ArrayList<String> cast = new ArrayList<>(); //people
        Movie plsman = new Movie(id, name, synopsis, rating, releaseDate, cast);

        ArrayList<Movie> movv = new ArrayList<>();
        movv.add(plsman);
        Movie foundmov = Cinema.searchMovie("Steve Buscemi 10 Hour Compilation",movv);
        assertEquals(plsman,foundmov,"Movies are not Equal");
    }
    /*
    @Test
    public void userInputTestEverythingFIne() {
        //mimic userinput
        ByteArrayInputStream inputStream = new ByteArrayInputStream("3\n4\n".getBytes());
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        UserInputExample userInputExample = new UserInputExample(inputStream, ps);

        //call method
        moviearray = userInputExample.movie_init();
        // All output to prnt is sent to ps
        // Return output is the same
        String outputText = byteArrayOutputStream.toString();
        String key = "output:";
        String output = outputText.substring(outputText.indexOf(key) + key.length()).trim();

        //assert prnt output = expect
        Assert.assertEquals(output, "7");
        //assert actual return = expect

    }
    */

    @Test
    public void cardTest(){
        String cardNumber = "12345";
        String cardHolderName = "TestName";

        Card testCard = new Card(cardNumber, cardHolderName);
        assertEquals(testCard.getCardNumber(), "12345", "Card number returned is incorrect");
        testCard.setCardNumber("54321");
        assertEquals(testCard.getCardNumber(), "54321", "Card number returned is incorrect");
        assertEquals(testCard.getCardHolderName(), "TestName", "Card holder name returned is incorrect");
        testCard.setCardNumber("New TestName");
        assertEquals(testCard.getCardNumber(), "New TestName", "Card holder name returned is incorrect");
    }

    @Test
    public void giftcardTest(){
        String giftCardNumber = "1875890093350513";
        boolean redeemed = false;

        GiftCard testGiftCard = new GiftCard(giftCardNumber, redeemed);
        assertEquals(testGiftCard.getGiftCardNumber(),"1875890093350513", "Gift card number returned is incorrect");
        testGiftCard.setGiftCardNumber("1875890093351111");
        assertEquals(testGiftCard.getGiftCardNumber(),"1875890093351111", "Gift card number returned is incorrect");
        assertFalse(testGiftCard.isRedeemed(), "Gift card is considered to be redeemed, yet it has not been");
        testGiftCard.setRedeemed(true);
        assertTrue(testGiftCard.isRedeemed(), "Gift card is considered to not be redeemed, yet it has been");
    }

    @Test
    public void getUserTest(){
        String input = "Broskin\n"       // "Wrong number, try again."
                + "YOYO\n";
        InputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        UserInput u = new UserInput(is, System.out);
        ArrayList<Customer> customers = new ArrayList<Customer>();
        Customer g = new Customer("Broskin", "YOYO", null,null);
        customers.add(g);

        assertTrue(u.promptLogin("Broskin","YOYO",customers));

    }

    @Test
    public void movieTest(){
        int id = 1;
        String name = "Movie";
        String synopsis = "Synopsis";
        String rating = "G";
        String releaseDate = "10/10/2010";
        String actor1 = "Matt Damon";
        String actor2 = "Maaaaaatt Daaaaaamon";

        ArrayList<String> cast = new ArrayList<String>();
        cast.add(actor1);
        cast.add(actor2);

        Movie testMovie = new Movie(id, name, synopsis, rating, releaseDate, cast);

        assertEquals(testMovie.getId(), 1, "Movie ID returned is incorrect");
        assertEquals(testMovie.getName(), "Movie", "Movie name returned is incorrect");
        testMovie.setName("Finding Nemo");
        assertEquals(testMovie.getName(), "Finding Nemo", "Movie name returned is incorrect");
        assertEquals(testMovie.getSynopsis(), "Synopsis", "Movie synopsis returned is incorrect");
        testMovie.setSynopsis("This movie is about fish");
        assertEquals(testMovie.getSynopsis(), "This movie is about fish", "Movie synopsis returned is incorrect");
        assertEquals(testMovie.getRating(), "G", "Movie rating returned is incorrect");
        testMovie.setRating("M");
        assertEquals(testMovie.getRating(), "M", "Movie rating returned is incorrect");
        assertEquals(testMovie.getReleaseDate(), "10/10/2010","Movie release date returned is incorrect");
        testMovie.setReleaseDate("10/10/2021");
        assertEquals(testMovie.getReleaseDate(), "10/10/2021","Movie release date returned is incorrect");

        assertEquals(testMovie.getCast(), cast, "Cast returned is incorrect");
        ArrayList<String> updatedCast = new ArrayList<String>();
        String actor3 = "Chad";
        String actor4 = "Lachlan";
        updatedCast.add(actor3);
        updatedCast.add(actor4);
        testMovie.setCast(updatedCast);
        assertEquals(testMovie.getCast(), updatedCast, "Cast returned is incorrect");
    }

    @Test
    public void customerTest(){
        String username = "username";
        String password = "password";

        String cardNumber1 = "40691";
        String cardHolderName1 = "Charles";
        String cardNumber2 = "42689";
        String cardHolderName2 = "Sergio";
        Card testCard1 = new Card(cardNumber1, cardHolderName1);
        Card testCard2 = new Card(cardNumber2, cardHolderName2);
        ArrayList<Card> testCards = new ArrayList<Card>();
        testCards.add(testCard1);
        testCards.add(testCard2);
        String testTicket1 = "Ticket1";
        String testTicket2 = "Ticket2";
        ArrayList<String> testTickets = new ArrayList<String>();
        testTickets.add(testTicket1);
        testTickets.add(testTicket2);

        Customer testCustomer = new Customer(username, password, testCards, testTickets);

        assertEquals(testCustomer.getUsername(), "username", "Username returned is incorrect");
        testCustomer.setUsername("newUsername");
        assertEquals(testCustomer.getUsername(), "newUsername", "Username returned is incorrect");
        assertEquals(testCustomer.getPassword(), "password", "Password returned is incorrect");
        testCustomer.setPassword("newPassword");
        assertEquals(testCustomer.getPassword(), "newPassword", "Password returned is incorrect");

        assertEquals(testCustomer.getCards(), testCards, "Card list returned is incorrect");
        String cardNumber3= "12345";
        String cardHolderName3 = "Hamish";
        Card testCard3 = new Card(cardNumber3, cardHolderName3);
        ArrayList<Card> testCardsUpdated = new ArrayList<Card>();
        testCardsUpdated.add(testCard3);
        testCustomer.setCards(testCardsUpdated);
        assertEquals(testCustomer.getCards(), testCardsUpdated, "Card list returned is incorrect");
        String cardNumber4= "54321";
        String cardHolderName4 = "Jackson";
        Card testCard4 = new Card(cardNumber4, cardHolderName4);
        testCardsUpdated.add(testCard4);
        testCustomer.addCard(testCard4);
        assertEquals(testCustomer.getCards(), testCardsUpdated, "Card list returned is incorrect");

        assertEquals(testCustomer.getTickets(), testTickets, "Ticket list returned is incorrect");
        String testTicket3 = "Ticket3";
        ArrayList<String> testTicketsUpdated = new ArrayList<String>();
        testTicketsUpdated.add(testTicket3);
        testCustomer.setTickets(testTicketsUpdated);
        assertEquals(testCustomer.getTickets(), testTicketsUpdated, "Ticket list returned is incorrect");
        String testTicket4 = "Ticket4";
        testTicketsUpdated.add(testTicket4);
        testCustomer.addTicket(testTicket4);
        assertEquals(testCustomer.getTickets(), testTicketsUpdated, "Ticket list returned is incorrect");

    }

    // @Test
    // public void cinemaTest(){
    //     int c_id = 1;

    //     int m_id = 1;
    //     String name = "Movie";
    //     String synopsis = "Synopsis";
    //     String rating = "G";
    //     String releaseDate = "10/10/2010";
    //     String actor1 = "Matt Damon";
    //     String actor2 = "Maaaaaatt Daaaaaamon";
    //     ArrayList<String> cast = new ArrayList<String>();
    //     cast.add(actor1);
    //     cast.add(actor2);
    //     Movie testMovie = new Movie(m_id, name, synopsis, rating, releaseDate, cast);
    //     ArrayList<Movie> moviesParent = new ArrayList<Movie>();
    //     moviesParent.add(testMovie);

    //     String c_name = "Event";
    //     String location = "Bondi Junction";

    //     int f_seatsOpen = 40;
    //     int f_seatsCapacity = 50;
    //     int m_seatsOpen = 40;
    //     int m_seatsCapacity = 50;
    //     int r_seatsOpen = 40;
    //     int r_seatsCapacity = 50;
    //     String day = "Monday";
    //     LocalTime time = LocalTime.of(11, 30, 00, 00);
    //     String screenSize = "Gold";
    //     BigDecimal basePrice = new BigDecimal("20");
    //     BigDecimal ticketPrice = new BigDecimal("1.6");

    //     MovieInstance movInst = new MovieInstance(m_id, c_id, testMovie, f_seatsCapacity, m_seatsCapacity, r_seatsCapacity,
    //                                                 day, time, screenSize, basePrice);

    //     ArrayList<MovieInstance> movies = new ArrayList<MovieInstance>();
    //     movies.add(movInst);

    //     int transactionNo = 1;
    //     String username = "username";
    //     String password = "password";

    //     String cardNumber1 = "40691";
    //     String cardHolderName1 = "Charles";
    //     String cardNumber2 = "42689";
    //     String cardHolderName2 = "Sergio";
    //     Card testCard1 = new Card(cardNumber1, cardHolderName1);
    //     Card testCard2 = new Card(cardNumber2, cardHolderName2);
    //     ArrayList<Card> testCards = new ArrayList<Card>();
    //     testCards.add(testCard1);
    //     testCards.add(testCard2);
    //     String testTicket1 = "Ticket1";
    //     String testTicket2 = "Ticket2";
    //     ArrayList<String> testTickets = new ArrayList<String>();
    //     testTickets.add(testTicket1);
    //     testTickets.add(testTicket2);

    //     Customer testCustomer = new Customer(username, password, testCards, testTickets);
    //     ArrayList<Customer> customers = new ArrayList<Customer>();
    //     customers.add(testCustomer);

    //     Cinema testCinema = new Cinema(c_id, c_name, location,movies, customers, moviesParent);

    //     assertEquals(testCinema.getName(), c_name, "Cinema name returned is incorrect");
    //     testCinema.setName("Hoyts");
    //     assertEquals(testCinema.getName(), "Hoyts", "Cinema name returned is incorrect");
    //     assertEquals(testCinema.getTicketReceipt(), "Cinema ID: 1\nTransaction No: 1\n");
    //     assertEquals(testCinema.getLocation(), location, "Cinema location returned is incorrect");
    //     testCinema.setLocation("Wyoming");
    //     assertEquals(testCinema.getLocation(), "Wyoming", "Cinema location returned is incorrect");

    //     assertEquals(testCinema.searchMovie("Movie", moviesParent), testMovie, "Movie search failed");
    //     assertEquals(testCinema.searchMovie("Incorrect name", moviesParent), null, "Movie search failed");

    //     assertEquals(testCinema.getMovieParents(), moviesParent, "Movie parents list returned is incorrect");
    //     assertEquals(testCinema.getMovies(), movies, "Movies list returned is incorrect");

    //     int newc_id = 2;
    //     int newm_id = 2;
    //     String newName = "Movie";
    //     String newSynopsis = "Synopsis";
    //     String newRating = "G";
    //     String newReleaseDate = "10/10/2010";
    //     String actor3 = "Leo Di Caprio";
    //     String actor4 = "Ched";
    //     ArrayList<String> newCast = new ArrayList<String>();
    //     newCast.add(actor3);
    //     newCast.add(actor4);
    //     Movie newMovie = new Movie(newm_id, newName, newSynopsis, newRating, newReleaseDate, newCast);

    //     int f_seatsCapacity1 = 50;
    //     int m_seatsCapacity1 = 50;
    //     int r_seatsCapacity1 = 50;
    //     String newDay = "Monday";
    //     LocalTime newTime = LocalTime.of(11, 30, 00, 00);
    //     String newScreenSize = "Gold";
    //     BigDecimal newBasePrice = new BigDecimal("20");
    //     BigDecimal newTicketPrice = new BigDecimal("1.6");

    //     MovieInstance newMovInst = new MovieInstance(newm_id, newc_id, newMovie, f_seatsCapacity1, m_seatsCapacity1, r_seatsCapacity1,
    //             newDay, newTime, newScreenSize, newBasePrice);

    //     ArrayList<MovieInstance> movies1 = new ArrayList<MovieInstance>();
    //     movies1.add(newMovInst);
    //     testCinema.setMovies(movies1);
    //     assertEquals(testCinema.getMovies(), movies1, "Movies list returned is incorrect");

    //     assertEquals(testCinema.getId(), c_id, "Cinema ID returned is incorrect");


    // }

    @Test
    public void setCardHolderNameTest(){
        String cardNumber1 = "40691";
        String cardHolderName1 = "Charles";
        Card testCard1 = new Card(cardNumber1, cardHolderName1);
        testCard1.setCardHolderName("jeff");
        assertEquals(testCard1.getCardHolderName(), "jeff", "Name not changed");

    }

    @Test
    public void testCardsRead(){
        String cardNumber1 = "40691";
        String cardHolderName1 = "Charles";
        String cardNumber2 = "42689";
        String cardHolderName2 = "Sergio";
        Card testCard1 = new Card(cardNumber1, cardHolderName1);
        Card testCard2 = new Card(cardNumber2, cardHolderName2);
        ArrayList<Card> cardList = new ArrayList<>();
        cardList.add(testCard1);
        cardList.add(testCard2);
        ArrayList<Card> readCardList = testCard1.readCards("resources/test_cards.txt");
        boolean flag = false;
        for(int i=0; i<2; i++) {
            if(((cardList.get(i)).getCardNumber()).equals(((readCardList.get(i)).getCardNumber())) == false) {
                flag = true;
            }
            if(((cardList.get(i)).getCardHolderName()).equals(((readCardList.get(i)).getCardHolderName())) == false) {
                flag = true;
            }
        }
        assertFalse(flag, "Values don't match");
        assertEquals(testCard1.readCards("resources/null.txt"), null, "Read file successfully");
    }
}
