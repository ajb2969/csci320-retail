package main.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ShoppingCart {
    private static HashMap<Integer, Integer> cart;
    private String [] currUser;
    public ShoppingCart (String [] username){
        this.cart = new HashMap<Integer, Integer>();
        this.currUser = username[0].split(" ");
    }

    public void addToCart(int upc, int quantity){
        try{
            /*
            String query = "Select * from Inventory where "+ String.valueOf(upc) + "  (Select UPC from inventory where store_id in (" +
                    "Select hStoreID from Customer WHERE " + "fname = \'"+ currUser[0] +"\' and lname = \'" + currUser[1] +" \'))";
            */

            String query = "Select UPC,Quantity from Inventory where store_ID in(" +
                    "Select hStoreID from Customer WHERE " +
                    "fname = \'"+ currUser[0].trim() +"\' and lname = \'" + currUser[1].trim() + "\')";

            /*String query = "with upcList as (Select UPC from inventory where store_id in (" +
                    "Select hStoreID from Customer WHERE " + "fname = \'"+ currUser[0] +"\' and lname = \'" + currUser[1].trim() +" \')) " +
                    "Select * from Inventory NATURAL JOIN upcList;";*/


            Connection c = InitRetail.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                if(rs.getString("UPC").equals(String.valueOf(upc))){
                    if(Integer.parseInt(rs.getString("quantity")) >= quantity){
                        this.cart.put(upc,quantity);
                        System.out.println("Successfully added to cart \n");
                    }
                    else{
                        System.err.println("Can't add " + String.valueOf(quantity) + " of " + String.valueOf(upc) + " to cart");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printCart(){
        for(Integer i: cart.keySet()){
            System.out.println(i + " - " + cart.get(i));
        }
        System.out.println("\n");
    }
}
