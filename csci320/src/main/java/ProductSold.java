package main.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductSold extends Table{
    public ProductSold(Connection c) {
        super(c);
    }


    /*Product Sold Table(Sale_ID,UPC,Quantity)*/
    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS ProductSold("
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
