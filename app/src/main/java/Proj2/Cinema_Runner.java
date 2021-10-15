package Proj2;

import java.util.*;

public class Cinema_Runner {
    public static void main(String[] args){
        Scanner atmInput = new Scanner(System.in);
        boolean running = true;
        boolean prompting = true;
        while(running){
            while(prompting){
                System.out.println("Welcome to Cinema! Select an option:" +
                "\nLogin = 1\nRegister = 2\nDisplay upcoming Movies & times");
                String option = atmInput.next();
                switch (option) {
                    case "1":
                        //login
                        break;
                    case "2":
                        //register
                        break;
                    case "3":
                        //upcoming stuff
                        break;
                    default:
                        System.out.println("Invalid Input. Please try again.");
                        break;
                }
            }
        }
    }
}