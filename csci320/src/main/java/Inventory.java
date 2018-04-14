/*
@Author: Alex Brown
 */
package main.java;

import main.java.Command.Command;

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

    /**
     * Sort the inventory
     * @param sort sort type
     */
    public static void sortBy(Command.SortBy sort, String first, String last) {
        try {
            String query = "SELECT DISTINCT * FROM product WHERE UPC in ("
                    + getCurrentStoreInventory(first, last) + ") "
                    + "ORDER BY ";
            switch (sort) {
                case brandAsc:
                    query += "brand";
                    break;
                case brandDesc:
                    query += "brand DESC";
                    break;
                case priceAsc:
                    query += "price";
                    break;
                case priceDesc:
                    query += "price DESC";
                    break;
                case productAsc:
                    query += "productType";
                    break;
                case productDesc:
                    query += "productType DESC";
                    break;
            }
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                System.out.println("UPC: " + rs.getString("UPC") + " - " + "Product: " +
                        rs.getString("ProductType") + " - " + "Price: " + rs.getString("Price")
                        + " - " + "Brand: "+ rs.getString("Brand"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR in SORTBY QUERY!");
        }

    }
    public static void restock(String first, String last) {
        // TODO PRINT UP
        try {
            String query = "SELECT UPC " +
                    "FROM (" + Inventory.getCurrentStoreInventory(first, last) +
                    ") WHERE quantity = 0";
            String query2 = "UPDATE inventory " +
                    "SET quantity = 50 " +
                    "WHERE UPC in (" + query + ")";
            Statement s = conn.createStatement();
            s.execute(query2);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("ERROR in RESTOCK QUERY!");
        }
    }
    public static String getCurrentStoreInventory(String fName, String lName) {
        return "Select UPC from Inventory where store_ID in(" +
                "Select hStoreID from Customer WHERE " +
                "fname = \'"+ fName +"\' and lname = \'" + lName + "\')";
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
                String query = "with storeInventory as (Select UPC,Quantity from Inventory where store_ID in(" +
                        "Select hStoreID from Customer WHERE " +
                        "fname = \'"+ fName +"\' and lname = \'" + lName + "\')) SELECT * from Product NATURAL JOIN storeInventory";

                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(query);
                while(rs.next()){
                    System.out.println(rs.getString("UPC") + " - " + rs.getString("ProductType") + " - " + rs.getString("Quantity"));
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
