package main.java;

import java.sql.Connection;

public class PopulateInventory {
    private static Connection conn;
    public PopulateInventory(Connection c){
        this.conn = c;
    }
}
