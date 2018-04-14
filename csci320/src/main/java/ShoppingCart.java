package main.java;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class ShoppingCart {
    private static HashMap<Integer, Integer> cart;
    public ShoppingCart (){
        this.cart = new HashMap<Integer, Integer>();

    }

    public void addToCart(int upc, int quantity, String [] username){
        try{
            username = username[0].split(" ");
            String query = "Select UPC,Quantity from Inventory where store_ID in(" +
                    "Select hStoreID from Customer WHERE " +
                    "fname = \'"+ username[0].trim() +"\' and lname = \'" + username[1].trim() + "\')";

            Connection c = InitRetail.getConnection();
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(query);
            while(rs.next()){
                if(rs.getString("UPC").equals(String.valueOf(upc))){
                    if(cart.containsKey(upc)){
                        if(Integer.parseInt(rs.getString("quantity")) >= (quantity + cart.get(upc)) && quantity > 0){
                            cart.put(upc,(quantity + cart.get(upc)));
                            System.out.println("Successfully added to cart \n");
                        }
                        else{
                            System.err.println("Can't add " + String.valueOf((quantity + cart.get(upc))) + " of " + String.valueOf(upc) + " to cart");
                        }
                    }
                    else{
                        if(Integer.parseInt(rs.getString("quantity")) >= quantity && quantity > 0){
                            cart.put(upc,quantity);
                            System.out.println("Successfully added to cart \n");
                        }
                        else{
                            System.err.println("Can't add " + String.valueOf(quantity) + " of " + String.valueOf(upc) + " to cart");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printCart(){
        System.out.println("Size of cart: " + cart.keySet().size());
        for(Integer i: cart.keySet()){
            System.out.print(i + " - " );
            try{
                String query = "Select productType from Product where upc = " + String.valueOf(i);
                Connection c = InitRetail.getConnection();
                Statement s = c.createStatement();
                ResultSet rs = s.executeQuery(query);
                while(rs.next()){
                    System.out.print(rs.getString("productType") + " - ");
                }
                System.out.print(cart.get(i) + "\n");
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        System.out.println("\n");
    }

    public HashMap<Integer, Integer> getCart(){
        return this.cart;
    }

    public void emptyCart(){
        this.cart = new HashMap<Integer,Integer>();
    }

}
