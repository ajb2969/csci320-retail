package main.java;
/*
@Author: Alex Brown
 */
import java.io.*;
import java.sql.*;
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
        kk[2] = kk[2].substring(1,kk[2].length());

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
                    + "address VARCHAR(150),"
                    + "city VARCHAR(150),"
                    + "state VARCHAR(150),"
                    + "zipcode VARCHAR(150),"
                    + "country VARCHAR(150),"
                    + "m_open INT,"
                    + "m_close INT,"
                    + "tue_open INT,"
                    + "tue_close INT,"
                    + "w_open INT,"
                    + "w_close INT,"
                    + "thur_open INT,"
                    + "thur_close INT,"
                    + "fri_open INT,"
                    + "fri_close INT,"
                    + "sat_open INT,"
                    + "sat_close INT,"
                    + "sun_open INT,"
                    + "sun_close INT"
                    + ");";

            Statement stmt = c.createStatement();
            stmt.execute(query);
            String line;
            String insertQuery = "insert into Store values";

            while ((line = reader.readLine()) != null) {
                insertQuery += "(";
                String [] kk = line.split("\\|");
                if (kk[1].equals("JAKES-ONLINE")){
                    insertQuery = insertQuery.substring(0,insertQuery.length()-1);
                    //String insertOnline = String.format("insert into Store values( %d,'%s'",Integer.parseInt(kk[0]),kk[1]);
                    String insertOnline = String.format("insert into Store values(");
                    for(int i = 0; i<20; i++){
                        insertOnline += "?,";
                    }
                    insertOnline+="?)";
                    PreparedStatement ps = conn.prepareStatement(insertOnline);
                    ps.setInt(1,Integer.parseInt(kk[0]));
                    ps.setString(2,kk[1]);
                    for(int i = 3; i<22;i++){
                        if(i >= 3 && i <= 7){
                            ps.setNull(i, Types.VARCHAR);
                        }
                        else{
                            ps.setNull(i,Types.INTEGER);
                        }

                    }
                    ps.executeUpdate();
                }
                else{
                    insertQuery += convertListToString(kk);
                    insertQuery+=")";
                    insertQuery+=",";
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

    /**
     * Prints all of the stores and its locations
     */
    public static void printStores(String uN) {
        try {
            String query = " SELECT id, name, address, city, state, zipcode, country " +
                    "FROM Store";
            Statement s = Sale.getConnection().createStatement();
            ResultSet rs = s.executeQuery(query);
            while (rs.next()) {
                if(rs.getInt("id") != 30){
                    System.out.println(rs.getInt("id") + " - " +
                            rs.getString("address") + " " + rs.getString("city") + " " +
                            rs.getString("state") + " " + rs.getString("zipcode") + " "
                            + rs.getString("country"));
                }
                else{
                    System.out.println(rs.getInt("id") + " - " + rs.getString("name"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException ne) {
            System.out.println("Unable to validate user. Try again.");
        }
    }
    public static void changeStore(String curruser, int id) {
        try {

            Connection conn = InitRetail.getConnection();
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
