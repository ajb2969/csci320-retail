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

    /**
     * Checks if the given username and password match.
     * If it does, it returns the name of the user.
     * @param username: The username to look for
     * @param password: The pass to verify credentials
     * @return The name of the user, if verified
     */
    public static String checkMemberCredentials(String username, String password){
        try{
            String query = "Select fname,lname,zipcode from Customer where UserType = " +
                    String.valueOf(UserType.valueOf(UserType.Member.toString()).ordinal()); //2 is a member
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);//pulls all members
            while(rs.next()){
                String user = rs.getString("fname").toLowerCase() +
                              rs.getString("lname").toLowerCase();
                int p = rs.getInt("zipcode");
                if(user.equals(username.toLowerCase()) && Integer.parseInt(password) == p){
                    return rs.getString("fname");
                }
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Given an username, return the type
     */
    public static UserType checkUserType(String username){
        return UserType.Member;
    }







}
