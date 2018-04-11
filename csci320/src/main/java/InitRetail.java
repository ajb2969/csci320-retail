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
    private static String productSoldFile = "productsSoldList.csv";
    private static String saleFile = "saleList.csv";
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
        InitRetail ir = new InitRetail("~/h2/retail","user","password");
        Scanner input = new Scanner(System.in);
        Table customer = new Customer(conn,customerFile, false);
        Table store = new Store(conn,storeFile, false);
        Table vendor = new Vendor(conn,vendorFile, false);


        //Table inventory = new Inventory(conn,inventoryFile);
        //Table product = new Product(conn,productFile);
        //Table productSold = new ProductSold(conn,productSoldFile);
        //Table sale = new Sale(conn, saleFile);


        String query;
        System.out.println("Welcome to JAKE'S!");
        System.out.print("Are you a member(Y/N): ");
        query = input.nextLine();
        Boolean member = checkMemberInput(input,query);
        if(member){
            String user = checkMemberCredentials(conn,input);
            if(user == null){
                startGuestLoop();
            }
            else{
                startMemberLoop(user);
            }
        }
        else{
            startGuestLoop();
        }


    }
}
