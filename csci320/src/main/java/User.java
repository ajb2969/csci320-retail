package main.java;
import com.sun.org.apache.xml.internal.security.Init;
import main.java.Command.*;
import main.java.Command.Inventory;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.HashMap;

/**
 * User class. 
 * This is an static class that will hold state of the current session
 * and the behavior for handling the user's actions.
 * @author Kevin Bastian
 */
public class User {

    // The type of user
    private UserType type;

    // Username's name
    private static String userName;

    private static String homeStoreID;

    // HashMap of commands available to the user
    private HashMap<String, Command> commands;

    /**
     * Private constructor for security
     * Constructs a user given the type
     */
    private User(UserType type, HashMap<String, Command> commands){
        this.type = type;
        this.commands = commands;
        this.homeStoreID = homeStoreID;
    }

    /**
     * Creates a Guess-type user
     * @return A guest User object
     */
    public static User createGuestUser(){
        return new User(UserType.Guest, createGuestCommands());
    }

    /**
     * Creates a user given a type
     * @param type: The user type enum
     * @return the User object
     */
    private static User createUser(UserType type){
        switch (type) {
            case Guest:
                return createGuestUser();
            case Employee:
                return new User(type, createEmployeeCommands());
            case Member:
                return new User(type, createMemberCommands());
            default:
                System.out.println("Incorrect user type, defaulting to guest");
        }
        return createGuestUser();
    }

    /**
     * Getter for the username
     * @return the username
     */
    public static String getUserName() {
        return userName;
    }
    /**
     * Asks the user if it is a member or not
     * @return whether the user specified to be a registered
     *  member or not
     */
    public static boolean checkMemberInput(){
        Scanner input = new Scanner(System.in);
        String  query = input.nextLine();
        return query.toLowerCase().matches("yes|y");
    }

    public static User identifyUser(){
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your username: ");//username is first + lastname
        String username = input.nextLine();
        System.out.print("Enter your password: ");//password is zipcode
        String password = input.nextLine();
        // Get the Name of the User
        String userName = Customer.checkMemberCredentials(username, password);//fname , lname
        try{
            // Create a user by checking its type
            User new_user = createUser(Customer.checkUserType(userName));
            String [] uN = userName.split(":");
            new_user.setName(uN[0] + " " + uN[1]);
            return new_user;
        }catch(IllegalArgumentException e){
            System.out.println("Error identifying user type. System will exit now");
            System.exit(-1);
        }
        // Should not get executed:
        return null;
        
    }

    /**
     * Initializes the right loop
     */
    public void startLoop(){
        try{
            Scanner input = new Scanner(System.in);
            if(this.userName != null)
                System.out.println("Hello " + this.userName);
            printHelp(this.type);
            String line = input.nextLine();
            while(!(line.toLowerCase().equals("logout"))){
                parseCommand(line);
                printHelp(this.type);
                System.out.print(">");
                line = input.next();
            }
        }
        catch(NoSuchElementException e){
            Scanner input = new Scanner(System.in);
            String line = input.nextLine();
            while(!(line.toLowerCase().equals("logout"))){
                parseCommand(line);
                printHelp(this.type);
                System.out.print(">");
                line = input.next();
            }
        }
    }

    private void parseCommand(String line){
        String[] args = line.trim().toLowerCase().split(" ");
        if(args.length > 0){
            if(this.commands.containsKey(args[0])){
                if(this.type == UserType.Guest){
                    this.commands.get(args[0]).execute(new String[]{null});
                }
                else{
                    this.commands.get(args[0]).execute(new String[]{this.userName});
                }
            }
            else System.out.println("Undefined command: " + line);
                
        }
    }

    /**
     * Prints a The command list
     */
    private void printHelp(UserType cT) {
        for (String i: this.commands.keySet()){
            System.out.println(i);
        }
    }
    /**
     * Sets the name for the user
     */
    private void setName(String name){
        this.userName = name;
    }


    /**
     * Registers and returns a hashmap with the commands of a guest
     * TODO: Change Logout commands for real commands
     * @return: HashMap of the guest commands
     */
    private static HashMap<String, Command> createGuestCommands(){
        HashMap<String, Command> commands = new HashMap<String, Command>();
        commands.put("registeraccount", new RegisterAccount());
        commands.put("history", new History());
        commands.put("printlocation", new PrintLocation());
        commands.put("changelocation", new ChangeLocation());
        commands.put("inventory", new Inventory());
        commands.put("sort", new Sort());
        commands.put("add", new Add());
        commands.put("cart", new Cart());
        commands.put("checkout", new Checkout());
        commands.put("logout", new Logout());
        return commands;
    }

    /**
     * Registers and returns a hashmap with the commands of a member customer
     * TODO: Change Logout commands for real commands
     * @return: HashMap of the guest commands
     */
    private static HashMap<String, Command> createMemberCommands(){
        HashMap<String, Command> commands = new HashMap<String, Command>();
        commands.put("history", new History());
        commands.put("printlocation", new PrintLocation());
        commands.put("changelocation", new ChangeLocation());
        commands.put("inventory", new Inventory());
        commands.put("sort", new Sort());
        commands.put("add", new Add());
        commands.put("cart", new Cart());
        commands.put("checkout", new Checkout());
        commands.put("logout", new Logout());
        return commands;
    }

    /**
     * Registers and returns a hashmap with the commands of an employee
     * TODO: Change Logout commands for real commands
     * @return: HashMap of the guest commands
     */
    private static HashMap<String, Command> createEmployeeCommands(){
        HashMap<String, Command> commands = new HashMap<String, Command>();
        commands.put("history", new History());
        commands.put("printlocation", new PrintLocation());
        commands.put("changelocation", new ChangeLocation());//can work at any location
        commands.put("inventory", new Inventory());
        commands.put("sort", new Sort());
        commands.put("add", new Add());
        String [] u = userName.split(" ");
        commands.put("cart", new Cart());
        commands.put("checkout", new Checkout());
        commands.put("logout", new Logout());
        commands.put("inventoryadd", new InventoryAdd());
        commands.put("inventoryremove", new InventoryRemove());
        commands.put("restock", new Restock());
        return commands;
    }
    
    
}