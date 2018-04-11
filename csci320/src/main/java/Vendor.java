package main.java;

/*
@Author: Alex Brown
 */

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Vendor extends Table{
    /**
     * Constructs a new Vendor table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Vendor(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
    }

    private String removeQuotes(String s){
        if(!(s.equals("")) && s.charAt(0) == 34){
            return s.substring(1,s.length()).replace("\'","");
        }
        else{
            return s.replace("\'","");
        }

    }

    @Override
    public String convertListToString(String[] kk) {
        return String.format("%d,'%s','%s','%s','%s',%d,'%s'",
                Integer.parseInt(kk[0]),
                removeQuotes(kk[1]).trim(),
                kk[2],
                kk[3],
                kk[4],
                Integer.parseInt(kk[5]),
                kk[6]);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Vendor("
                    + "vendorID INT NOT NULL ,"
                    + "vendorName VARCHAR(150) NOT NULL ,"
                    + "address VARCHAR(150) ,"
                    + "city VARCHAR(150),"
                    + "state VARCHAR(150),"
                    + "zipCode int,"
                    + "country VARCHAR(150)"
                    + ");";
            Statement stmt = c.createStatement();
            stmt.execute(query);
            String line;
            String insertQuery = "insert into Vendor values";
            while ((line = reader.readLine()) != null) {
                if(line != null){
                    insertQuery += "(";
                    String [] kk = line.split("\\|");
                    insertQuery += convertListToString(kk);
                    insertQuery+=")";
                }
                insertQuery+=",";
            }
            int rep = insertQuery.lastIndexOf(",");
            String fixString = insertQuery.substring(0,rep);
            fixString+=";";

            Statement s = c.createStatement();
            s.execute(fixString);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
