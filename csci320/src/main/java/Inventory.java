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
                    + getCurrentStoreInventoryUPC(first, last) + ") "
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
        try {
            String query = "SELECT UPC " +
                    "FROM (" + getCurrentStoreInventory(first, last) +
                    ") as currStoreUPCs WHERE quantity < 15";

            String getVendors = "SELECT DISTINCT vendorName FROM vendor WHERE vendorID IN ( SELECT vendorID FROM product where UPC IN ( " + query +" ) ) " +
                    "ORDER BY vendorName ";

            Statement sa = conn.createStatement();
            ResultSet rs = sa.executeQuery(getVendors);
            System.out.println("Restocked from vendors:");
            while(rs.next()){
                System.out.println("\tVendor: " + rs.getString("vendorName"));
            }
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
        return "Select UPC, quantity from Inventory where store_ID in(" +
                "Select hStoreID from Customer WHERE " +
                "fname = \'"+ fName +"\' and lname = \'" + lName + "\')";
    }

    public static String getCurrentStoreInventoryUPC(String fName, String lName) {
        return "Select UPC from Inventory where store_ID in(" +
                "Select hStoreID from Customer WHERE " +
                "fname = \'"+ fName +"\' and lname = \'" + lName + "\')";
    }

    public static void printInventory(String fName, String lName){
        try{
            if(fName == null && lName == null){//guest
                String getHstoreID = "Select hstoreID from Customer where id = 0";
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(getHstoreID);
                int hStoreID = 0;//guestHomestoreID
                while(rs.next()){
                    hStoreID = rs.getInt(1);
                }


                String getGuestInventory = "with storeInventory as (Select UPC,Quantity from Inventory where store_ID = "+ hStoreID + ")";
                String query = getGuestInventory + " SELECT * from Product NATURAL JOIN storeInventory";
                s = conn.createStatement();
                rs = s.executeQuery(query);

                while(rs.next()){
                    System.out.println(rs.getString("UPC") + " - " + rs.getString("ProductType") + " - " + rs.getString("Quantity"));
                }
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

    /**
     * Adds a certain amunt of items of a given UPC to the inventory
     * @param upc
     * @param quantity
     */
    public static void inventoryAdd(int upc, int quantity) {

        String username = User.getUserName();
        String[] names = username.split(" ");
        // get current quantity
        try {
            String query =  " SELECT UPC, quantity from inventory" +
            " WHERE UPC in (" + Inventory.getCurrentStoreInventory(names[0], names[1]) +
                    ") and UPC = " + upc;
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);

            boolean validUpc = false;
            int curr_quantity  = 0;
            while(rs.next()){
                validUpc = true;
                curr_quantity = Integer.parseInt(rs.getString("quantity"));
                break;
            }
            if(validUpc){
                // Update the quantity
                int new_quantity = curr_quantity + quantity;


                String query_update =  " UPDATE Inventory set quantity = " + new_quantity +
                        " WHERE UPC in (" + Inventory.getCurrentStoreInventory(names[0], names[1]) +
                        ") and UPC = " + upc;
                Statement s_update = conn.createStatement();
                boolean rs_update = s_update.execute(query_update);

                System.out.println("Old quantity is " + curr_quantity + ", new is "+ new_quantity);

            }else{
                System.out.println("Invalid UPC, not found in your store");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
//        SELECT UPC, quantity from inventory
//        WHERE UPC in (Select UPC from Inventory where store_ID in(
//                Select hStoreID from Customer WHERE
//                fname = 'Abe' and lname =  'Aalbers')) and UPC = 111953
    }


    /**
     * Adds a certain amunt of items of a given UPC to the inventory
     * @param upc
     * @param quantity
     */
    public static void inventoryRemove(int upc, int quantity) {

        String username = User.getUserName();
        String[] names = username.split(" ");
        // get current quantity
        try {
            String query =  " SELECT UPC, quantity from inventory" +
                    " WHERE UPC in (" + Inventory.getCurrentStoreInventory(names[0], names[1]) +
                    ") and UPC = " + upc;
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);

            boolean validUpc = false;
            int curr_quantity  = 0;
            while(rs.next()){
                validUpc = true;
                curr_quantity = Integer.parseInt(rs.getString("quantity"));
                break;
            }
            if(validUpc){
                // Update the quantity
                int new_quantity = curr_quantity - quantity;


                String query_update =  " UPDATE Inventory set quantity = " + new_quantity +
                        " WHERE UPC in (" + Inventory.getCurrentStoreInventory(names[0], names[1]) +
                        ") and UPC = " + upc;
                Statement s_update = conn.createStatement();
                boolean rs_update = s_update.execute(query_update);

                System.out.println("Old quantity is " + curr_quantity + ", new is "+ new_quantity);

            }else{
                System.out.println("Invalid UPC, not found in your store");
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
