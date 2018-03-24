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





    private static boolean checkMemberInput(Scanner input, String query){
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




    public static void main(String [] args){
        //initializes and fills the database
        //InitRetail ir = new InitRetail("","","");//connects to db
        //PopulateInventory pi = new PopulateInventory(conn);//populates db
        Scanner input = new Scanner(System.in);
        String query = "";
        System.out.println("Welcome to JAKE'S!");
        System.out.print("Are you a member(Y/N): ");
        query = input.nextLine();
        Boolean member = checkMemberInput(input,query);
        if(member){
            String user = checkMemberCredentials(input);
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
