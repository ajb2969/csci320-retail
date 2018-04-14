package main.java.Command;


import main.java.InitRetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class History implements Command {

    /**
     * History execute command to view the history of a user
     * @param args: Given arguments for the command to be fully
     */
    public void execute(String [] args) {
        args = args[0].split(" ");
        //Print Sale Number, Store ID, cost and saleTime
        try{
            HashMap<Integer, String> sales = new HashMap<>();
            String getNameQuery = String.format("(select ID from Customer where fname =  \'%s\' and lname = \'%s\')",args[0],args[1]);
            String saleQuery = "Select saleID, storeID,cost, saleTime from Sale where customerID in" + getNameQuery;
            Connection c = InitRetail.getConnection();
            PreparedStatement ps = c.prepareStatement(saleQuery);
            ResultSet rs = ps.executeQuery();
            System.out.println("Which sale would you like to view: ");
            while(rs.next()){
                sales.put(rs.getInt("saleID"), String.format("%f - %s",rs.getFloat("cost"),rs.getTimestamp("saleTime")));
            }
            System.out.println("Sale ID - Cost - Time and Date");
            for(Integer i:sales.keySet()){
                System.out.println(i + " - " + sales.get(i));
            }
            System.out.println("\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
