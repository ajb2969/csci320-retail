package main.java.Command;

import main.java.User;
import main.java.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * PrintLocation Command that prints all of the location
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 *
 */
public class PrintLocation implements Command{

    /**
     * Execute - prints all of the available locations
     * @param args: printlocation command
     */
    public void execute(String [] args) {
        Store.printStores();
    }
}
