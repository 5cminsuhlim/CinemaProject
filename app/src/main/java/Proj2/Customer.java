package Proj2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer {
    private int id;
    private String username;
    private String password;
    private ArrayList<Card> cards;
    private ArrayList<String> tickets;

    public Customer(int id, String username, String password, ArrayList<Card> cards, ArrayList<String> tickets) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.cards = cards;
        this.tickets = tickets;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public ArrayList<String> getTickets() {
        return tickets;
    }

    public void setTickets(ArrayList<String> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(String ticket){
        tickets.add(ticket);
    }

    protected static ArrayList<Customer> readCustomers(String filename, ArrayList<Card> validCards){
        ArrayList<Customer> customerList = new ArrayList<>();

        try{

            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file

                String[] line = input.nextLine().split(",");
                String[] cardArr = line[3].split(";");
                ArrayList<Card> cards = new ArrayList<>();

                List<String> cardList = Arrays.asList(cardArr);
                for(Card c: validCards){
                    if(cardList.contains(c.getCardNumber())){
                        cards.add(c);
                    }
                }

                String[] ticketList = line[4].split(";");
                ArrayList<String> tickets = new ArrayList<>(Arrays.asList(ticketList));

                //public Customer(int id, String username, String password, ArrayList<Card> cards, ArrayList<Integer> tickets)
                customerList.add(new Customer(Integer.parseInt(line[0]), line[1],
                        line[2], cards, tickets)); //ADD STUFF HERE
            }
        }
        catch (Exception e) {
            System.out.println("Error reading customers file. Please try again.\n");
            return null;
        }

        return customerList;
    }

    protected static int saveCustomers(String filename, ArrayList<Customer> customers){
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()){
            f.delete();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), StandardCharsets.UTF_8))) {
                for(Customer c : customers) {
                    String tickets = c.getTickets().stream().map(Object::toString).collect(Collectors.joining(";"));
                    String cards = c.getCards().stream().map(Card::getCardNumber).collect(Collectors.joining(";"));
                    //public Customer(int id, String username, String password, ArrayList<Card> cards, ArrayList<Integer> tickets)
                    writer.write(c.getId() + "," + c.getUsername() + "," + c.getPassword() + "," + cards + "," + tickets + "\n");
                }
            }
            catch(Exception e){
                System.out.println("Saving cardList failed.");
                return -2;
            }
            System.out.println("Cards Saved.");
            return 1;
        } else{
            System.out.println("File does not exist. Please try again");
            return -1;
        }

    }
}
