/*
@Author: Alex Brown
 */
package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;



public class Inventory extends Table {
    public Inventory(Connection c,String filename) {
        super(c);
        populateTables(c,filename);
    }

    @Override
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Inventory("
                    + "store_ID INT NOT NULL PRIMARY KEY ,"
                    + "UPC INT NOT NULL,"
                    + "quantity INT NOT NULL"
                    + ");";
            Statement stmt = c.createStatement();
            stmt.execute(query);

            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            String insertQuery = "insert into Inventory values";
            while ((line = reader.readLine()) != null) {
                if(!(line.equals("") && line.contains("#"))){
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

    } catch (SQLException | IOException e) {
        e.printStackTrace();
    }
}

    @Override
    public String convertListToString(String[] kk) {
        int one = Integer.parseInt(kk[0]);
        int two;
        String newKK1 = "";
        if(kk[1].length() > 1){//makes sure every upc is of length 6(fits inside an integer)
            for(int i = 0; i< 6; i++){
                newKK1 +=kk[1].charAt(i);
            }
        }
        if(newKK1.equals("")){
            two = Integer.parseInt(kk[2]);
        }
        else{
            two = Integer.parseInt(newKK1);
        }
        int three = Integer.parseInt(kk[2]);
        return String.format("%d,%d,%d",one,two,three);
    }
}
