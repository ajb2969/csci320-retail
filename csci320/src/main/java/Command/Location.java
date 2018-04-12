package main.java.Command;

import main.java.User;
import main.java.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Location implements Command{
    public void execute(String [] args) {
        User ur = User.identifyUser();
        try {

            String userName = ur.getUserName();
            String query = " SELECT id, address, city, state, zipcode, country " +
                            "FROM Store";
            Connection conn = InitRetail.InitConnection("~/h2/retail","user","password");
            Scanner input = new Scanner(System.in);
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " +
                        rs.getString("address") + " " + rs.getString("city") + " " +
                        rs.getString("state") + " " + rs.getString("zipcode") + " "
                        + rs.getString("country"));
            }
            System.out.println("Please select the Store Id that you're in:");
            System.out.print(">");
            int id = input.nextInt();
            Store.changeStore(ur.getUserName(), id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Unable to validate user. Try again.");
        }
    }
}
