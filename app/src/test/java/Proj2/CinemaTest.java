/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package Proj2;

import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.math.BigDecimal;

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
        String name = "Steve Buscemi 10 Hour Compilation";
        String synopsis = "As above"; //plot
        String rating = "10"; //rating
        String releaseDate = "10/10/10"; //?
        ArrayList<String> cast = new ArrayList<String>(); //people
        //private ArrayList<String> upcomingTimes; //need to gen this
        Schedule schedule = null;
        String screenSize = ""; //need to gen this
        int f_seatsOpen = 0;  //need to gen this
        int f_seatsBooked = 0;  //need to gen this
        int m_seatsOpen = 0;  //need to gen this
        int m_seatsBooked = 0;  //need to gen this
        int r_seatsOpen = 0;  //need to gen this
        int r_seatsBooked = 0;  //need to gen this
        BigDecimal basePrice = new BigDecimal(0); //need to gen this
        BigDecimal ticketPrice = new BigDecimal(0); //0.8, 1.2, 1.6
        Movie plsman = new Movie(name, synopsis, rating, releaseDate, cast, //ArrayList<String> upcomingTimes
                schedule, screenSize, f_seatsOpen,
        f_seatsBooked, m_seatsOpen,m_seatsBooked,
        r_seatsOpen, r_seatsBooked, basePrice);

        ArrayList<Movie> movv = new ArrayList<Movie>();
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
}
