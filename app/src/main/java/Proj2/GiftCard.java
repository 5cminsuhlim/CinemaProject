package Proj2;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;

public class GiftCard {
    private String giftCardNumber; //need to gen this
    private boolean redeemed; //need to gen this

    public GiftCard(String giftCardNumber, boolean redeemed) {
        this.giftCardNumber = giftCardNumber;
        this.redeemed = redeemed;
    }

    public String getGiftCardNumber() {
        return giftCardNumber;
    }

    public void setGiftCardNumber(String giftCardNumber) {
        this.giftCardNumber = giftCardNumber;
    }

    public boolean isRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

    protected static ArrayList<GiftCard> readGiftCards(String filename){
        ArrayList<GiftCard> giftCardList = new ArrayList<>();

        try{
            File file = new File(filename);
            Scanner input = new Scanner(file);

            while (input.hasNextLine()) { //reads all lines of the file
                String[] line = input.nextLine().split(",");

                //need to double check ordering
                giftCardList.add(new GiftCard(line[0].substring(0, 16), line[1].equals("1")));
            }
        }
        catch (Exception e) {
            System.out.println("Error reading gift card file. Please try again.\n");
            return null;
        }

        return giftCardList;
    }

    protected static int saveGiftCards(String filename, ArrayList<GiftCard> cards){
        File f = new File(filename);
        if(f.exists() && !f.isDirectory()){
            f.delete();
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filename), StandardCharsets.UTF_8))) {
                for(GiftCard c : cards) {
                    int redeemed = c.isRedeemed()? 1 : 0;
                    writer.write(c.getGiftCardNumber() + "GC," + redeemed + "\n");
                }
            }
            catch(Exception e){
                System.out.println("Saving giftcard list failed.");
                return -2;
            }
            System.out.println("Gift cards saved.");
            return 1;
        } else{
            System.out.println("File does not exist. Please try again");
            return -1;
        }

    }
}
