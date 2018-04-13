package main.java.Command;


import main.java.Store;
import main.java.User;

/**
 * InventoryRemove command
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 *
 */
public class InventoryRemove implements Command {

    /**
     * InventoryRemove execute command to remove a product from inventory
     * @param args: the UPC
     */
    @Override
    public void execute(String[] args) {
        try {
            int upc = Integer.parseInt(args[1]);
            System.out.println("TODO: the backend of Inventory needs to be implemented.");
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: inventoryremove <UPC>");
        }

    }
}

