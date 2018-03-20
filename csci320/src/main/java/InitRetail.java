package main.java;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

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

    private static void printMemberHelp(){
        System.out.println("How can we help you today?");
        System.out.println("Location");
        System.out.println("Inventory");
        System.out.println("Sort");
        System.out.println("Add");
        System.out.println("Cart");
        System.out.println("History");
        System.out.println("Checkout");
    }

    private static void printEmployeeHelp(){
        printMemberHelp();
        System.out.println("InventoryAdd");
        System.out.println("InventoryRemove");
        System.out.println("Restock");

    }

    private static void printGuestHelp(){
        printMemberHelp();
        System.out.println("RegisterAccount");


    }

    private static String checkMemberCredentials(Scanner input){
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


    public static void startGuestLoop(){
        printGuestHelp();
    }

    public static void startMemberLoop(String user){
        System.out.println("Hello " + user);
        printMemberHelp();
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
