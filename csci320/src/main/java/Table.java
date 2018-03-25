package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;

public abstract class Table {
    private static Connection conn;
    public Table(Connection c){
        this.conn = c;
    }

    public abstract void populateTables(Connection c, String filename);

    public abstract String convertListToString(String [] kk);
}
