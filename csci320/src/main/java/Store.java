package main.java;
/*
@Author: Alex Brown
 */
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Store extends Table{

    /**
     * Constructs a new Store table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Store(Connection c, String filename, boolean populateTable) {
        super(c, filename, populateTable);
    }

    @Override
    public String convertListToString(String[] kk) {
        kk[2] = kk[2].substring(1,kk[2].length()-1);
        return String.format("%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d,%d",
                Integer.parseInt(kk[0]),
                kk[1],
                kk[2],
                kk[3],
                kk[4],
                kk[5],
                kk[6],
                Integer.parseInt(kk[7]),
                Integer.parseInt(kk[8]),
                Integer.parseInt(kk[9]),
                Integer.parseInt(kk[10]),
                Integer.parseInt(kk[11]),
                Integer.parseInt(kk[12]),
                Integer.parseInt(kk[13]),
                Integer.parseInt(kk[14]),
                Integer.parseInt(kk[15]),
                Integer.parseInt(kk[16]),
                Integer.parseInt(kk[17]),
                Integer.parseInt(kk[18]),
                Integer.parseInt(kk[19]),
                Integer.parseInt(kk[20])
        );
    }

    @Override
    //store 30 is the online store
    public void populateTables(Connection c, String filename) {
        try {
            String query = "CREATE TABLE IF NOT EXISTS Store("
                    + "id INT PRIMARY KEY NOT NULL,"
                    + "name VARCHAR(150) NOT NULL,"
                    + "address VARCHAR(150) NULL,"
                    + "city VARCHAR(150) NULL,"
                    + "state VARCHAR(150) NULL,"
                    + "zipcode VARCHAR(150) NULL,"
                    + "country VARCHAR(150) NULL,"
                    + "m_open INT NULL,"
                    + "m_close INT NULL,"
                    + "tue_open INT NULL,"
                    + "tue_close INT NULL,"
                    + "w_open INT NULL,"
                    + "w_close INT NULL,"
                    + "thur_open INT NULL,"
                    + "thur_close INT NULL,"
                    + "fri_open INT NULL,"
                    + "fri_close INT NULL,"
                    + "sat_open INT NULL,"
                    + "sat_close INT NULL,"
                    + "sun_open INT NULL,"
                    + "sun_close INT NULL"
                    + ");";

            Statement stmt = c.createStatement();
            stmt.execute(query);
            String line;
            String insertQuery = "insert into Store values";

            while ((line = reader.readLine()) != null) {
                if(line != null){
                    insertQuery += "(";
                    String [] kk = line.split("\\|");
                    insertQuery += convertListToString(kk);
                    insertQuery+=")";
                }
                insertQuery+=",";
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

    /**
     * Prints all of the stores and its locations
     */
    public static void printStores() {
        try {
            String query = " SELECT id, address, city, state, zipcode, country " +
                    "FROM Store";
            Scanner input = new Scanner(System.in);
            Statement s = Sale.getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " - " +
                        rs.getString("address") + " " + rs.getString("city") + " " +
                        rs.getString("state") + " " + rs.getString("zipcode") + " "
                        + rs.getString("country"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Unable to validate user. Try again.");
        }
    }
    public static void changeStore(String curruser, int id) {
        try {

            Connection conn = InitRetail.InitConnection("~/h2/retail","user","password");
            Statement s = conn.createStatement();
            String query = String.format("SELECT id " +
                                        "FROM Customer " +
                                        "WHERE fname = \'%s\';",curruser);
            ResultSet r  = s.executeQuery(query);
            int getUserId = 0;
            while(r.next()) {
                getUserId = r.getInt("id");
            }
            query = "UPDATE Customer SET hstoreID = "+ id +" WHERE id = " + getUserId;
            s.execute(query);
            System.out.println("You are in Jake's located at ");//+ + l.get(id)[2]);//update homestore in db
        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
