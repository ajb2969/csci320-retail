package main.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
InitRetail: The main system client.

IT initializes the database, if specified, and it runs the system input loop.

@Author: Alex Brown
@Author: Kevin Bastian
 
*/

public class InitRetail {
    private static final String customerFile = "customersList.csv";
    private static final String inventoryFile = "inventoryList.csv";
    private static final String productFile = "productList.csv";
    private static final String storeFile = "storeList.csv";
    private static final String vendorFile = "vendorsList.csv";
    private static final String saleFile = "saleList.csv";
    private static final String producsSoldFile = "productsSoldList.csv";



    private static Connection conn;
    public static Connection InitConnection(String location, String user, String password){

        try {
            //This needs to be on the front of your location
            String url = "jdbc:h2:" + location;

            //This tells it to use the h2 driver
            Class.forName("org.h2.Driver");

            //creates the connection
            conn = DriverManager.getConnection(url, user, password);
            return conn;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("There was an error connecting to the database. Client will now close.");
            // System.exit(-1);
        }
        return null;
        
    }

    public static Connection getConnection(){
        return conn;
    }

    public static void main(String [] args){
        //initializes and fills the database
        Connection conn = InitConnection("~/h2/retail","user","password");
        final boolean popTables = false;
        new Customer(conn,customerFile, popTables);
        new Store(conn,storeFile, popTables);
        new Vendor(conn,vendorFile, popTables);
        new Product(conn,productFile, popTables);
        new Inventory(conn,inventoryFile, popTables);
        new Sale(conn,saleFile,popTables);
        new ProductSold(conn, producsSoldFile, popTables);
        start();
    }

    /**
     * Start UI
     */
    private static void start(){
        // Display welcome message and start user loop
        System.out.println("Welcome to JAKE'S!");
        System.out.print("Are you a member(Y/N): ");
        // By default, the user is a guest
        User user = User.createGuestUser();

        // Check if the user specifies to be a member, then try to identify it
        if(User.checkMemberInput()){
            user = User.identifyUser();
            User.setUserId();
        }
        // Begin Loop
        user.startLoop();

    }
}
