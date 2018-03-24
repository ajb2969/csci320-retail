package main.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductSold extends Table{
    public ProductSold(Connection c) {
        super(c);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Customer("
                    + "id INT PRIMARY KEY NOT NULL ,"
                    + "fname VARCHAR(150) NULL,"
                    + "mname VARCHAR(150) NULL,"
                    + "lname VARCHAR(150) NULL,"
                    + "address VARCHAR(150) NULL,"
                    + "city VARCHAR(150) NULL,"
                    + "state VARCHAR(150) NULL,"
                    + "zipcode VARCHAR(150) NULL,"
                    + "country VARCHAR(150) NULL,"
                    + "email VARCHAR(150) NULL,"
                    + "hstoreId INT NULL,"
                    + "customerType VARCHAR(45) NOT NULL,"
                    + ");";

            Statement stmt = c.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
