package main.java.Command;

import main.java.Store;
import main.java.User;

import java.util.Scanner;

/**
 * @author jahongiramirkulov
 * ChangeLocation will change the location of the user
 *
 * The user needs to input:
 *          changelocation <storeid>
 */
public class ChangeLocation implements Command{

    /**
     * ChangeLocation execute command that changes the location
     * @param args: change command
     *
     */
    @Override
    public void execute(String[] args) {
        try {
            Store.changeStore(User.getUserName(), Integer.parseInt(args[1]));
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: changelocation <storeid>");
        }
    }
}
