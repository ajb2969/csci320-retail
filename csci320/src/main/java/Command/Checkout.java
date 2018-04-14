package main.java.Command;


import main.java.InitRetail;
import main.java.ShoppingCart;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Checkout implements Command {
    public void execute(String [] args) {
        args = args[0].split(" ");
        System.out.println("Purchasing products.");
        HashMap<Integer, Integer> sc = new Add().getCart().getCart();


            try{
                Connection c = InitRetail.getConnection();
                Statement s = c.createStatement();
                double cost = 0.0;
                for(Integer i: sc.keySet()) {//iterates over UPCS
                    //remove each item's quantity from inventory
                    String removeItem = "Update Inventory SET Quantity = Quantity -" + String.valueOf(sc.get(i)) +
                            "where UPC = " + i;
                    s.executeUpdate(removeItem);
                    String getCost = "Select price from Product where UPC = " + i;
                    ResultSet rs = s.executeQuery(getCost);
                    while(rs.next()){
                        cost += rs.getInt("price");
                    }
                }


                //create Sale entry for sale


                String getStoreID = "Select hStoreID from Customer WHERE " +
                        "fname = \'"+ args[0] +"\' and lname = \'" + args[1] + "\'";
                int storeID = s.executeQuery(getStoreID).getInt("hStoreID");


                String getUserID = "Select ID from Customer where "+
                        "fname = \'"+ args[0] +"\' and lname = \'" + args[1] + "\'";
                String userID = s.executeQuery(getUserID).getString("ID");

                String createSale = "Insert into Sale values (0,";//0 is for auto increment
                createSale += getStoreID +",";
                createSale += cost + ",";
                createSale += "\'"+ userID + "\',";
                createSale += new Timestamp(System.currentTimeMillis()) + ",";


                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate localDate = LocalDate.now();
                dtf.format(localDate); //2016/11/16

                createSale += dtf.format(localDate) + ");";
                s.executeUpdate(createSale);
                int x = 0;

                //saleID, storeID, cost, customerID, saleTime(timestamp), date(Date)



                //add ProductSold entry connected to Sale





            } catch (SQLException e) { e.printStackTrace(); }
    }
}
