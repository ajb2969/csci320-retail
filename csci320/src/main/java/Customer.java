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
import java.util.Scanner;

public class Customer extends Table{
    public Customer(Connection c) {
        super(c);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Customer("
                        + "id INT PRIMARY KEY NOT NULL ,"
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
                    for(int i = 0; i< kk.length-1; i++){
                        insertQuery+=kk[i] + ",";
                    }
                    insertQuery+=kk[kk.length-1];
                    insertQuery+=")";
                }
                insertQuery+=",";
                break;
            }
            int rep = insertQuery.lastIndexOf(",");
            String fixString = insertQuery.substring(0,rep);
            fixString+=";";

            String [] l = fixString.split(",\\(");
            Statement s = c.createStatement();
            s.execute(fixString);
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
