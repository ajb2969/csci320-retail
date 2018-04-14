/*
@Author: Alex Brown
 */
package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Inventory extends Table {

    /**
     * Constructs a new Customer table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Inventory(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Inventory("
                    + "store_ID INT NOT NULL,"
                    + "UPC INT NOT NULL PRIMARY KEY,"
                    + "quantity INT NOT NULL"
                    + ");";
            Statement stmt = c.createStatement();
            stmt.execute(query);

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String insertQuery = "insert into Inventory values";
            while ((line = reader.readLine()) != null) {
                if(!(line.equals("") && line.contains("#"))){
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

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void printInventory(String fName, String lName){
        try{
            if(fName == null && lName == null){
                ArrayList<String> inventory = new ArrayList<>();
                String query = "Select ProductType from Product,Inventory where store_ID = 1";
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(query);
                while(rs.next()){
                    if (rs.getString(1) != null){
                        inventory.add(rs.getString(1));
                    }
                }
                int x = 0;
            }
            else{
                String query = "Select ProductType from Product where UPC in (" +
                        "Select UPC from Inventory where store_ID in (" +
                        "Select hStoreID from Customer where fname = \'" + fName + "\' and lname = \'" + lName + "\'))";
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(query);
                while(rs.next()){
                    System.out.println(rs.getString("ProductType"));
                }
                System.out.println("\n");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    @Override
    public String convertListToString(String[] kk) {
        return String.format("%d,%d,%d",Integer.parseInt(kk[0]),Integer.parseInt(kk[1]),Integer.parseInt(kk[2]));
    }
}
