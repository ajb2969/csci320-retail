package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Customer extends Table{
    public Customer(Connection c) {
        super(c);
    }

    @Override
    public String convertListToString(String [] kk){
        return String.format("%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%d\',\'%s\'",
                Integer.parseInt(kk[0]),
                kk[1],
                kk[2],
                kk[3],
                kk[4],
                kk[5],
                kk[6],
                kk[7],
                kk[8],
                kk[9],
                Integer.parseInt(kk[10]),
                kk[11]
                );
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Customer("
                        + "id INT PRIMARY KEY NOT NULL,"
                        + "fname VARCHAR(150) NULL,"
                        + "mname VARCHAR(150) NULL,"
                        + "lname VARCHAR(150) NULL,"
                        + "address VARCHAR(150) NULL,"
                        + "city VARCHAR(150) NULL,"
                        + "state VARCHAR(150) NULL,"
                        + "zipcode VARCHAR(150) NULL,"
                        + "country VARCHAR(150) NULL,"
                        + "email VARCHAR(150) NULL,"
                        + "hstoreId INT NULL,"
                        + "customerType VARCHAR(45) NOT NULL,"
                        + ");" ;

            Statement stmt = c.createStatement();
            stmt.execute(query);
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String insertQuery = "insert into Customer values";
            while ((line = reader.readLine()) != null) {
                if(line != null){
                    insertQuery += "(";
                    String [] kk = line.split("\\|");
                    insertQuery += convertListToString(kk);
                    insertQuery+=")";
                }
                insertQuery+=",";
            }
            int rep = insertQuery.lastIndexOf(",");
            String fixString = insertQuery.substring(0,rep);
            fixString+=";";

            Statement s = c.createStatement();
            //s.execute(fixString);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        System.out.println("Logout");
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
            return username;
        }
        return null;
    }

    public static void startGuestLoop(){
        printGuestHelp();
    }

    public static void parseCommand(String command){
        Scanner input = new Scanner(System.in);
        String c = command.toLowerCase();
        if(c.equals("location")){
            try{
                ArrayList<String []> l = new ArrayList<String []>();
                BufferedReader reader = new BufferedReader(new FileReader("/Users/alexbrown/IdeaProjects/csci320-retail/csci320/src/main/resources/storeList.csv"));
                String line;
                while ((line = reader.readLine()) != null) {
                    String [] a = line.split("\\|");
                    l.add(a);
                    System.out.println(Integer.parseInt(a[0]) + " - " + a[2] + ", " + a[3]);
                }
                System.out.println("Please select the Store Id that you're in:");
                System.out.print(">");
                int id = input.nextInt();
                System.out.println("You are in Jake's located at " + l.get(id)[2]);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        else if(c.equals("inventory")){
            System.out.println("You have just printed out the store inventory");
        }
        else if(c.equals("sort")){
            System.out.println("What would you like to sort the store inventory by?");
            System.out.println("Price, Brand, or Product Type:");
            String answer = input.nextLine();
            parseCommand("inventory");
            System.out.println("By " + answer);
        }
        else if(c.equals("add")){
            System.out.print("Enter the UPC of the item you would like to add:");
            int UPC = input.nextInt();
            System.out.println("You have now added 6-ct of Bananas to your cart");
        }
        else if(c.equals("cart")){
            System.out.println("Printed below is all the items in your cart currently");
        }
        else if(c.equals("history")){
            System.out.println("Printed below are all the items that you have purchased in the last 30 days");
        }
        else if(c.equals("checkout")){
            System.out.print("Please enter your Credit card number: ");
            int ccn  = input.nextInt();
            System.out.print("Please enter your CCV:");
            int ccv = input.nextInt();
            System.out.println("Printed below is your Receipt");
            System.out.println("GoodBye");
        }
        else{
            System.out.println("The command " + c + "that you have enterd is invalid");
        }
    }

    public static void startMemberLoop(String user){
        System.out.println("Hello " + user);
        printMemberHelp();
        Scanner input = new Scanner(System.in);
        String line = "";
        while(!(line.toLowerCase().equals("logout"))){
            System.out.print(">");
            String command = input.nextLine();
            parseCommand(command);
            printMemberHelp();
            System.out.print(">");
            command = input.nextLine();
        }
    }


    public  static boolean checkMemberInput(Scanner input, String query){
        if(query.toLowerCase().equals("yes") ||
                query.toLowerCase().equals("y"))
        {return true;}
        else if(query.toLowerCase().equals("no") ||
                query.toLowerCase().equals("n"))
        {return false;}
        else{
            while(!((query.toLowerCase().equals("yes") || query.toLowerCase().equals("y"))
                    || (query.toLowerCase().equals("no") || query.toLowerCase().equals("n")))){
                System.out.print("Are you a member(Y/N): ");
                query = input.nextLine();
            }
            if(query.toLowerCase().equals("yes") ||
                    query.toLowerCase().equals("y"))
            {return true;}
            else
            {return false;}
        }
    }
}
