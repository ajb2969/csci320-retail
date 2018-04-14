package main.java.Command;
import main.java.Inventory;


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
            Inventory.restock(args[0].split(" ")[0], args[0].split(" ")[1]);

        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: restock <storeId>");
        }
    }
}
