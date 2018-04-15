package main.java.Command;


import main.java.InitRetail;

import java.sql.*;
import java.text.NumberFormat;
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

                int storeID = 30;
                String getStoreID = "Select hStoreID from Customer WHERE " +
                        "fname = \'"+ args[0] +"\' and lname = \'" + args[1] + "\'";
                ResultSet rs = s.executeQuery(getStoreID);
                while(rs.next()){
                     storeID = rs.getInt("hStoreID");
                }



                String getUserID = "Select ID from Customer where "+
                        "fname = \'"+ args[0] +"\' and lname = \'" + args[1] + "\'";

                String userID = "";
                rs = s.executeQuery(getUserID);
                while(rs.next()){
                    userID = rs.getString("ID");
                }
                String getSaleID = "Select max(saleID) from Sale";
                PreparedStatement ps = c.prepareStatement(getSaleID);
                rs = ps.executeQuery();
                int saleID = 0;
                while(rs.next()){
                    saleID = rs.getInt(1);
                }
                saleID +=1;
                String createSale = "Insert into Sale values (";//0 is for auto increment
                createSale += String.valueOf(saleID) + ",";
                createSale += storeID +",";
                createSale += cost + ",";
                createSale += userID + ",";
                createSale += "?,?);";
                ps = c.prepareStatement(createSale);

                ps.setTimestamp(1,new Timestamp(System.currentTimeMillis()));
                ps.setDate(2,new Date(System.currentTimeMillis()));

                ps.executeUpdate();

                //saleID, storeID, cost, customerID, saleTime(timestamp), date(Date)

/*
                saleID = 0;
                ps = c.prepareStatement("Select max(saleID) from Sale");
                rs = ps.executeQuery();
                while(rs.next()){
                    saleID = rs.getInt(1);
                }
*/

                //add ProductSold entry connected to Sale
                String insertProducts = "Insert into ProductSold values(";

                for (Integer i : sc.keySet()){
                    insertProducts+= String.valueOf(saleID) + ","+
                            String.valueOf(i) + ","+
                            String.valueOf(sc.get(i)) + "),(";
                }
                insertProducts = insertProducts.substring(0,insertProducts.length()-2);
                ps = c.prepareStatement(insertProducts);
                ps.executeUpdate();
                NumberFormat format = NumberFormat.getCurrencyInstance();
                System.out.println("Your total is " + format.format(cost) +"\n");
                new Add().getCart().emptyCart();

            } catch (SQLException e) { e.printStackTrace(); }
    }
}
