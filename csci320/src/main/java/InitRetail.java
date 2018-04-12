package main.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import static main.java.Customer.*;

/*
@Author: Alex Brown
 */

public class InitRetail {
    private static Connection conn;
    private static String customerFile = "customersList.csv";
    private static String inventoryFile = "inventoryList.csv";
    private static String productFile = "productList.csv";
    private static String storeFile = "storeList.csv";
    private static String vendorFile = "vendorsList.csv";

    public InitRetail(String location,
                      String user,
                      String password){
        try {
            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url,
                    user,
                    password);
        } catch (SQLException | ClassNotFoundException e) {
            //You should handle this better
            e.printStackTrace();
        }
    }

    public static void main(String [] args){
        //initializes and fills the database
        new InitRetail("~/h2/retail","user","password");
        final boolean popTables = false;
        new Customer(conn,customerFile, popTables);
        new Store(conn,storeFile, popTables);
        new Vendor(conn,vendorFile, popTables);
        new Product(conn,productFile, popTables);
        new Inventory(conn,inventoryFile, popTables);
        start();


    }

    /**
     * Start UI
     */
    private static void start(){
        System.out.println("Welcome to JAKE'S!");
        System.out.print("Are you a member(Y/N): ");
        Scanner input = new Scanner(System.in);
        String  query = input.nextLine();
        Boolean member = checkMemberInput(input,query);
        if(member){
            String user = checkMemberCredentials(conn,input);
            if(user == null){
                startGuestLoop();
            }
            else{
                startMemberLoop(user);
            }
        }else{
            startGuestLoop();
        }

    }
}
