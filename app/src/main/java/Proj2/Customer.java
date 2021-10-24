package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {
    private int id;
    private String username;
    private String password;
    private ArrayList<Card> cards;
    private ArrayList<Integer> tickets;

    public Customer(int id, String username, String password, ArrayList<Card> cards, ArrayList<Integer> tickets) {
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

    public ArrayList<Integer> getTickets() {
        return tickets;
    }

    public void setTransactionNo(ArrayList<Integer> tickets) {
        this.tickets = tickets;
    }

    protected static ArrayList<Customer> readCustomers(String filename){
        ArrayList<Customer> customerList = new ArrayList<>();

        try{

            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file

                String[] line = input.nextLine().split(",");

                //need to double check ordering
                customerList.add(new Customer()); //ADD STUFF HERE
            }
        }
        catch (Exception e) {
            System.out.println("Error reading card file. Please try again.\n");
            return null;
        }

        return customerList;
    }
}
