package main.java;

/*
@Author: Alex Brown
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Sale extends Table{

    /**
     * Constructs a new Customer table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Sale(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Sale("
                    + "saleID int NOT NULL, "
                    + "storeID int NOT NULL ,"
                    + "cost int NOT NULL ,"
                    + "customerID int NOT NULL ,"
                    + "saleTime TIMESTAMP NOT NULL ,"
                    + "date DATE NOT NULL"
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
