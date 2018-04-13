package main.java.Command;

import main.java.Store;
import main.java.User;

/**
 * Restock command that will restock the inventory
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 *
 */
public class Restock implements Command {

    /**
     *
     * Restock execute command that will restock the inventory
     * @param args: Restock command
     *            usage: restock <storeId>
     */
    @Override
    public void execute(String[] args) {
        try {
            System.out.println("Restocking the inventory.");
            int storyID = Integer.parseInt(args[1]);
            // TODO Add the backend restock command.
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: restock <storeId>");
        }
    }
}
