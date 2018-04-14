package main.java;

/**
 *
 * @Author: Alex Brown
 * @version 04/14/18
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;

public abstract class Table {

    /** DB connection **/
    protected static Connection conn;

    /** Buffer reader for the csv file **/
    protected BufferedReader reader;

    /**
     * Constructs a new Table
     * @param c: The connection to the db
     * @param filename: Filename to populate table
     * @param populateTable: Whether the table needs to be populated
     */
    public Table(Connection c, String filename, boolean populateTable) {
        conn = c;
        String complete_file_path = new File("csci320/src/main/resources/"+filename).getAbsolutePath();
        try {
            this.reader = new BufferedReader(new FileReader(complete_file_path));
            if(populateTable) populateTables(c,complete_file_path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public abstract void populateTables(Connection c, String filename);
    public abstract String convertListToString(String [] kk);

}
