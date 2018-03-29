package main.java;

/*
@Author: Alex Brown
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Product extends Table{
    public Product(Connection c, String filename) {
        super(c);
        populateTables(c,filename);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Product("
                    + "upc INT NOT NULL,"
                    + "productType VARCHAR(150) NOT NULL ,"
                    + "brand VARCHAR(150) NOT NULL ,"
                    + "price INTEGER(2) NOT NULL ,"
                    + "quantityAmount INT NOT NULL ,"
                    + "quantityType VARCHAR(150) NOT NULL ,"
                    + "vendorID INT NOT NULL"
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
