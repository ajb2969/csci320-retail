package main.java;

/*
@Author: Alex Brown
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Vendor extends Table{
    public Vendor(Connection c,String filename) {
        super(c);
        populateTables(c,filename);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Vendor("
                    + "vendorID INT NOT NULL ,"
                    + "vendorName VARCHAR(150) NOT NULL ,"
                    + "address VARCHAR(150) NOT NULL ,"
                    + "city VARCHAR(150) NOT NULL,"
                    + "state VARCHAR(150) NOT NULL ,"
                    + "zipCode int NOT NULL ,"
                    + "country VARCHAR(150) NOT NULL"
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
