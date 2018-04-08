package main.java;

/*
@Author: Alex Brown
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Product extends Table{
    public Product(Connection c, String filename) {
        super(c);
        populateTables(c,filename);
    }

    @Override
    public String convertListToString(String[] kk) {
        return String.format("%d,'%s','%s',%d,%d,'%s',%d");
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Product("
                    + "upc INT NOT NULL,"
                    + "productType VARCHAR(150) NOT NULL ,"
                    + "brand VARCHAR(150) NOT NULL ,"
                    + "price NUMERIC(8,2) NOT NULL ,"
                    + "quantityAmount INT NOT NULL ,"
                    + "quantityType VARCHAR(150) NOT NULL ,"
                    + "vendorID INT NOT NULL"
                    + ");";
            Statement stmt = c.createStatement();
            stmt.execute(query);

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String insertQuery = "insert into Product values";
            while ((line = reader.readLine()) != null) {
                if((!line.equals("")) && (!line.contains("#"))){
                    insertQuery += "(";
                    String [] kk = line.split("\\|");
                    insertQuery += convertListToString(kk);
                    insertQuery+="),";
                }

            }
            int rep = insertQuery.lastIndexOf(",");
            String fixString = insertQuery.substring(0,rep);
            fixString+=";";

            Statement s = c.createStatement();
            s.execute(fixString);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
