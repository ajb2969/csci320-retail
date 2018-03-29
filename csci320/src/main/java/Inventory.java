/*
@Author: Alex Brown
 */
package main.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class Inventory extends Table {
    public Inventory(Connection c,String filename) {
        super(c);
        populateTables(c,filename);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Inventory("
                    + "storeID INT NOT NULL PRIMARY KEY ,"
                    + "quantity INT NOT NULL, "
                    + "UPC INT NOT NULL"
                    + ");";
            Statement stmt = c.createStatement();
            stmt.execute(query);


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String convertListToString(String[] kk) {
        return null;
    }
}
