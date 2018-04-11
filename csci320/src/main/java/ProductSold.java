package main.java;

/*
@Author: Alex Brown
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductSold extends Table{

    /**
     * Constructs a new Customer table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public ProductSold(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
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
