package main.java;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Sale extends Table{
    public Sale(Connection c) {
        super(c);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Sale("
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
