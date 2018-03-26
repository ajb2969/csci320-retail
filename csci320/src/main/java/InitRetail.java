package main.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;
import static main.java.Customer.*;

//@todo populate the database
//@todo check member credentials
//@todo handle employee credentials
//@todo handle parsing and conversion to SQL queries


public class InitRetail {
    private static Connection conn;
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
        Table customer = new Customer(conn);
        //customer.populateTables(conn,"/Users/alexbrown/IdeaProjects/csci320-retail/csci320/src/main/resources/customersList.csv");
        Table store = new Store(conn);
        //store.populateTables(conn, "/Users/alexbrown/IdeaProjects/csci320-retail/csci320/src/main/resources/storeList.csv");



        String query = "";
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
