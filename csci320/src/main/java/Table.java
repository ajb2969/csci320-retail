package main.java;

import java.sql.Connection;

public class Table {
    private static Connection conn;
    public Table(Connection c){
        conn = c;
    }
}
