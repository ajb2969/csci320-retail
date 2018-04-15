package main.java.Command;


import main.java.*;

import java.util.Scanner;

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
            Scanner scanner = new Scanner(System.in);
            System.out.println("Insert the UPC");
            System.out.print("> ");
            int UPC = Integer.parseInt(scanner.nextLine());
            System.out.println("Insert the quantity to add");
            System.out.print("> ");
            int quantity = Integer.parseInt(scanner.nextLine());
            main.java.Inventory.inventoryRemove(UPC, quantity);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: inventoryremove <UPC>");
        }

    }
}

