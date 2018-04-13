package main.java.Command;

import main.java.User;
import main.java.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class PrintLocation implements Command{
    public void execute(String [] args) {
        try {

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

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Unable to validate user. Try again.");
        }
    }
}