package Proj2;

import java.io.File;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;
import java.math.BigDecimal;
import java.time.*;


public class MovieInstance{
    private final int m_id;
    private final int c_id;
    private Movie parent;
    private int f_seatsOpen;  //need to gen this
    private int f_seatsCapacity;  //need to gen this
    private int m_seatsOpen;  //need to gen this
    private int m_seatsCapacity;  //need to gen this
    private int r_seatsOpen;  //need to gen this
    private int r_seatsCapacity;  //need to gen this
    private String day;
    private LocalTime time;
    private String screenSize; //need to gen this
    private BigDecimal basePrice; //need to gen this
    private BigDecimal ticketPrice; //0.8, 1.2, 1.6
    private int bookings;

    public MovieInstance(int m_id, int c_id, Movie parent, int f_seatsCapacity, int m_seatsCapacity,
                         int r_seatsCapacity, String day, LocalTime time, String screenSize, BigDecimal basePrice){
        this.m_id=m_id;
        this.c_id=c_id;
        this.parent = parent;
        this.f_seatsOpen=f_seatsCapacity;
        this.f_seatsCapacity=f_seatsCapacity;
        this.m_seatsOpen=m_seatsCapacity;
        this.m_seatsCapacity=m_seatsCapacity;
        this.r_seatsOpen = r_seatsCapacity;
        this.r_seatsCapacity = r_seatsCapacity;
        this.day = day;
        this.time= time;
        this.screenSize = screenSize;
        this.basePrice = basePrice;
        this.ticketPrice = setTicketPrice(this.screenSize);
        this.bookings = 0;
    }

    public String getName(){
        return this.parent.getName();
    }

    public String getMovieDetails(){
        return this.parent.getMovieDetails();
    }

    public String getSchedule(){
        String timeStr = time.format(DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH));
        return day + timeStr;
    }

    public String getScreenSize() {
        return screenSize;
    }

    public int getBookings(){
        return bookings;
    }

    public int getM_id() {
        return m_id;
    }

    public int getC_id() {
        return c_id;
    }

    public int getF_seatsOpen() {
        return f_seatsOpen;
    }

    public void setF_seatsOpen(int f_seatsOpen) {
        this.f_seatsOpen = f_seatsOpen;
    }

    public int getF_seatsCapacity() {
        return f_seatsCapacity;
    }

    public void setF_seatsCapacity(int f_seatsCapacity) {
        this.f_seatsCapacity = f_seatsCapacity;
    }

    public int getM_seatsOpen() {
        return m_seatsOpen;
    }

    public void setM_seatsOpen(int m_seatsOpen) {
        this.m_seatsOpen = m_seatsOpen;
    }

    public int getM_seatsCapacity() {
        return m_seatsCapacity;
    }

    public void setM_seatsCapacity(int m_seatsCapacity) {
        this.m_seatsCapacity = m_seatsCapacity;
    }

    public int getR_seatsOpen() {
        return r_seatsOpen;
    }

    public void setR_seatsOpen(int r_seatsOpen) {
        this.r_seatsOpen = r_seatsOpen;
    }

    public int getR_seatsCapacity() {
        return r_seatsCapacity;
    }

    public void setR_seatsCapacity(int r_seatsCapacity) {
        this.r_seatsCapacity = r_seatsCapacity;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public LocalTime getTime() {
        return time;
    }

    public void updateSeatsOpen(int numF, int numM, int numR){
        this.setF_seatsOpen(this.getF_seatsOpen() - numF);
        this.setM_seatsOpen(this.getM_seatsOpen() - numM);
        this.setR_seatsOpen(this.getR_seatsOpen() - numR);
    }

    public void bookCustomerCard(Customer customer, Cinema cinema, String time, Card card, int numPeople, int numF, int numM, int numR){

        this.updateSeatsOpen(numF, numM, numR);

        customer.addTicket(cinema.getTicketReceipt());
        this.bookings++;

        //if using card
        boolean found = false;

        //if a customer's card is found, don't save
        for(Card c : customer.getCards()){
            if(c.getCardNumber().equalsIgnoreCase(card.getCardNumber())){
                found = true;
                break;
            }
        }
        //if not found, save
        if(!found){
            customer.addCard(card);
        }
    }

    public void bookCustomerGiftCard(Customer customer, Cinema cinema, String time, GiftCard giftCard, int numPeople, int numF, int numM, int numR){
        this.updateSeatsOpen(numF, numM, numR);

        customer.addTicket(cinema.getTicketReceipt());
        this.bookings++;

        //if using giftcard
        //set giftcard to redeemed
        giftCard.setRedeemed(true);
    }

    public BigDecimal setTicketPrice(String screenSize) {
        if(screenSize.equalsIgnoreCase("bronze")){
            return basePrice.multiply(BigDecimal.valueOf(0.8));
        }
        else if(screenSize.equalsIgnoreCase("silver")){
            return basePrice.multiply(BigDecimal.valueOf(1.2));
        }
        else{
            return basePrice.multiply(BigDecimal.valueOf(1.6));
        }
    }
}