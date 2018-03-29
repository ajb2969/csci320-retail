package main.java;

/*
@Author: Alex Brown
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductSold extends Table{
    public ProductSold(Connection c,String filename) {
        super(c);
        populateTables(c,filename);
    }


    /*Product Sold Table(Sale_ID,UPC,Quantity)*/
    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS ProductSold("
                    + "saleID int NOT NULL,"
                    + "upc INT NOT NULL,"
                    + "quantity INT NOT NULL "
                    + ")";
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
