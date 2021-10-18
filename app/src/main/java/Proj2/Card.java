package Proj2;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;



public class Card{
    private String cardNumber; //need to gen this
    private String cardHolderName; //need to gen this

    public Card(String cardNumber, String cardHolderName) {
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    protected static ArrayList<Card> readCards(String filename){
        ArrayList<Card> cardList = new ArrayList<>();

        try{

            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file

                String[] line = input.nextLine().split(",");

                //need to double check ordering
                cardList.add(new Card(line[1], line[0]));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading card file. Please try again.\n");
            return null;
        }

        return cardList;
    }
}
