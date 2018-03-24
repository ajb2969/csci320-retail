package main.java;

import java.sql.Connection;
import java.util.Scanner;

public class Customer extends Table{
    public Customer(Connection c) {
        super(c);
    }

    public static void printMemberHelp(){
        System.out.println("How can we help you today?");
        System.out.println("Location");
        System.out.println("Inventory");
        System.out.println("Sort");
        System.out.println("Add");
        System.out.println("Cart");
        System.out.println("History");
        System.out.println("Checkout");
    }

    public static void printEmployeeHelp(){
        printMemberHelp();
        System.out.println("InventoryAdd");
        System.out.println("InventoryRemove");
        System.out.println("Restock");

    }

    public static void printGuestHelp(){
        printMemberHelp();
        System.out.println("RegisterAccount");


    }

    public static String checkMemberCredentials(Scanner input){
        System.out.print("Enter your username: ");
        String username = input.nextLine();
        System.out.print("Enter your password: ");
        String password = input.nextLine();


        if(false){
            /*combination not correct
            System.out.print("Are you **actually** a member?: ");
            String query = input.nextLine();
            Boolean m = checkMemberInput(input,query);
            if(m){
                while(username and password are not correct){
                    reask for username and password
                }
                return user's name
            }
            else{
                return null;
            }


            */
        }
        else{//combination correct
            //return user's name
        }
        return null;
    }

    public static void startGuestLoop(){
        printGuestHelp();
    }

    public static void startMemberLoop(String user){
        System.out.println("Hello " + user);
        printMemberHelp();
    }
}
