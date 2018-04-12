package main.java;

/*
@Author: Alex Brown
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import static main.java.Store.changeStore;

public abstract class Table {
    /** DB connection **/
    protected static Connection conn;

    /** Buffer reader for the csv file **/
    protected BufferedReader reader;
    /**
     * Constructs a new Table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Table(Connection c, String filename, boolean populateTable){
        conn = c;
        String complete_file_path = new File("csci320/src/main/resources/"+filename).getAbsolutePath();
        try {
            this.reader = new BufferedReader(new FileReader(complete_file_path));
            if(populateTable) populateTables(c,complete_file_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public abstract void populateTables(Connection c, String filename);

    public abstract String convertListToString(String [] kk);

    static void parseCommand(String curruser, List<String> commands, String command){
        Scanner input = new Scanner(System.in);
        String c = command.toLowerCase();
        if(!commands.contains(c.toLowerCase())){
            System.out.println("The command " + c + " is invalid please try again");
        }
        else if(c.equals("location")){
            changeStore(conn,curruser,input);

        }
        else if(c.equals("inventory")){
            Inventory.printInventory(conn,curruser);
        }
        else if(c.equals("sort")){
            System.out.println("What would you like to sort the store inventory by?");
            System.out.println("Price, Brand, or Product Type:");
            String answer = input.nextLine();
            parseCommand(curruser,commands, "inventory");
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
}
