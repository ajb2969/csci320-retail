package main.java;

/*
@Author: Alex Brown
 */

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer extends Table{

    /**
     * Constructs a new Customer table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Customer(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
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
                        + "UserType VARCHAR(45) NOT NULL,"
                        + ");" ;

            Statement stmt = c.createStatement();
            stmt.execute(query);
            String line;
            String insertQuery = "insert into Customer values";
            while ((line = reader.readLine()) != null) {
                if((!line.equals("")) && (!line.contains("#"))){
                    insertQuery += "(";
                    String [] kk = line.split("\\|");
                    if(kk[0].equals("0")){//guest user
                        insertQuery += convertListToString(new String[]{kk[0],kk[1],kk[2],kk[3],"","","","","","",kk[10],kk[11]});
                    }
                    else {
                        insertQuery += convertListToString(kk);
                    }
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

    /**
     * Checks if the given username and password match.
     * If it does, it returns the name of the user.
     * @param username: The username to look for
     * @param password: The pass to verify credentials
     * @return The name of the user, if verified
     */
    public static String checkMemberCredentials(String username, String password){
        try{
            String query = "Select fname,lname,zipcode,hstoreID from Customer where UserType = " +
                    String.valueOf(UserType.valueOf(UserType.Member.toString()).ordinal()) + " or UserType = " +
                            String.valueOf(UserType.valueOf(UserType.Employee.toString()).ordinal()); //2 is a member
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);//pulls all members
            while(rs.next()){
                String user = rs.getString("fname").toLowerCase() +
                              rs.getString("lname").toLowerCase();
                int p = rs.getInt("zipcode");
                if(user.equals(username.toLowerCase()) && Integer.parseInt(password) == p){
                    return rs.getString("fname" ) + ":" + rs.getString("lname" );
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException | NumberFormatException e) {
            System.err.println("Incorrect member credentials. Try again.");
        }
        return null;
    }
    /**
     * Given an username, return the type
     */
    public static UserType checkUserType(String username){
        if(username == null){
            return UserType.Guest;
        }
        String [] name = username.split(":");//[fname,lname]
        try{
            //gets usertype of person
            String query = String.format("select UserType from Customer where fname =  \'%s\' and lname = \'%s\'",name[0],name[1]);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                int type = Integer.parseInt(rs.getString("UserType"));
                switch (type) {
                    case 1:return UserType.Member;
                    case 2:return UserType.Employee;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return UserType.Guest;
    }

    /**
     * Queries and displays the current user's
     * purchase history
     */
    public static void displayHistory(){
        String UserId = User.getUserID();


        try{
            //gets usertype of person
            String query = String.format("select SaleID, StoreID, Cost from Customer where customerID =  \'%s\'", UserId);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            int total_cost = 0;
            while(rs.next()) {
                int sale_id = Integer.parseInt(rs.getString("SaleID"));
                int store_id = Integer.parseInt(rs.getString("StoreID"));
                int cost = Integer.parseInt(rs.getString("Cost"));
                String saleTime = rs.getString("SaleTime");
                System.out.printf("Sale Id: %d, Store Id: %d, Cost: %d, Time of Sale %s \n",
                        sale_id, store_id, cost, saleTime);
                total_cost += cost;
            }
            System.out.println("Total cost of all sales is " + total_cost);
        } catch (Exception e) {
            e.printStackTrace();
        }



    }







}
