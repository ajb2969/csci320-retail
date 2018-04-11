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
    /**
     * Constructs a new Customer table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Product(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
    }
    @Override
    public String convertListToString(String[] kk) {
        if(kk[2].equals("") || kk[3].equals("")){
            return String.format("'%s',%f,%f,'%s',%d,%d",
                    kk[0],
                    Float.parseFloat(kk[1]),
                    null,
                    null,
                    Integer.parseInt(kk[4]),
                    Integer.parseInt(kk[5])
                    );
        }
        else{
            if(kk[2].contains("/")){
                String [] unit = kk[2].split("/");
                double num = Double.parseDouble(unit[0]) / Double.parseDouble(unit[1]);
                return String.format("'%s',%f,%f,'%s',%d,%d",
                        kk[0],
                        Float.parseFloat(kk[1]),
                        num,
                        kk[3],
                        Integer.parseInt(kk[4]),
                        Integer.parseInt(kk[5])
                );
            }
            else{
                return String.format("'%s',%f,%d,'%s',%d,%d",
                        kk[0],
                        Float.parseFloat(kk[1]),
                        Integer.parseInt(kk[2]),
                        kk[3],
                        Integer.parseInt(kk[4]),
                        Integer.parseInt(kk[5])
                );
            }
        }

    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Product("
                    + "productType VARCHAR(150) NOT NULL ,"
                    + "price NUMERIC(8,2) NOT NULL,"
                    + "quantityAmount INT,"
                    + "quantityType VARCHAR(150),"
                    + "upc INT NOT NULL,"
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
