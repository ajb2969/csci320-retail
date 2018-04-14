package main.java.Command;


import main.java.Customer;
import main.java.User;

import java.sql.SQLException;

public class History implements Command {

    /**
     * History execute command to view the history of a user
     * @param args: Given arguments for the command to be fully
     */
    public void execute(String [] args) {
        System.out.println("Viewing History");
        try {
            Customer.displayHistory();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
