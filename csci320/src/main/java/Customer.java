package main.java;

/*
@Author: Alex Brown
 */

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Customer extends Table{

    /**
     * Constructs a new Customer table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Customer(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
        populateCommands();
    }
    enum customerType{
        Guest,
        Member,
        Employee,
    }

    private static HashMap<customerType,List<String>> commands;
    private static String currUser;
    private void populateCommands(){
        commands = new HashMap<customerType,List<String>>();
        commands.put(customerType.Guest, Arrays.asList(
                "registeraccount","location","inventory","sort","add","cart", "checkout","logout"));
        commands.put(customerType.Member, Arrays.asList(
                "location","inventory","sort","add","cart","history","checkout","logout"));
        commands.put(customerType.Employee, Arrays.asList(
                "registeraccount","location","inventory","sort","add","cart","history","checkout","logout","inventoryadd","inventoryremove", "restock"));
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
            String line;
            String insertQuery = "insert into Customer values";
            while ((line = reader.readLine()) != null) {
                if((!line.equals("")) && (!line.contains("#"))){
                    insertQuery += "(";
                    String [] kk = line.split("\\|");
                    insertQuery += convertListToString(kk);
                    insertQuery+="),";
                }

            }
            int rep = insertQuery.lastIndexOf(",");
            String fixString = insertQuery.substring(0,rep);
            fixString+=";";

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

    static String checkMemberCredentials(Connection conn, Scanner input){
        System.out.print("Enter your username: ");//username is first + lastname
        String username = in    put.nextLine();
        System.out.print("Enter your password: ");//password is zipcode
        String password = input.nextLine();

        try{
            String query = "Select fname,lname,zipcode from Customer where customerType = " +
                    String.valueOf(customerType.valueOf(customerType.Member.toString()).ordinal()); //2 is a member
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);//pulls all members
            while(rs.next()){
                String user = rs.getString("fname").toLowerCase() +
                              rs.getString("lname").toLowerCase();
                int p = rs.getInt("zipcode");
                if(user.equals(username.toLowerCase()) && Integer.parseInt(password) == p){
                    currUser = rs.getString("fname");
                    return rs.getString("fname");
                }
            }
            currUser = null;
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    static boolean checkMemberInput(Scanner input, String query){
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


    static void startMemberLoop(String user){
        System.out.println("Hello " + user);
        printHelp(customerType.Member);
        Scanner input = new Scanner(System.in);
        String line = "";
        while(!(line.toLowerCase().equals("logout"))){
            System.out.print(">");
            line = input.nextLine();
            parseCommand(currUser,commands.get(customerType.Member),line);
            printHelp(customerType.Member);
            System.out.print(">");
            line = input.nextLine();
        }
        input.close();
    }





    static void startGuestLoop(){
        printHelp(customerType.Guest);
        Scanner input = new Scanner(System.in);
        String line = "";
        while(!(line.toLowerCase().equals("logout"))){
            System.out.print(">");
            String command = input.nextLine();
            parseCommand(currUser,commands.get(customerType.Guest), command);
            printHelp(customerType.Guest);
            System.out.print(">");
            command = input.nextLine();
        }
        input.close();
        //change guest entry(id = 0) back to online store
    }

    private static void printHelp(customerType cT) {

        for (String i: commands.get(cT)){
            System.out.println(i);
        }
    }






}
