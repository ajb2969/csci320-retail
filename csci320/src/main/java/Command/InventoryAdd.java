package main.java.Command;

import main.java.*;
import main.java.Inventory;

import java.util.Scanner;

/**
 * InventoryAdd command to add to the inventory
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 *
 */
public class InventoryAdd implements Command {

    /**
     *
     * InventoryAdd execute command to add to the inventory
     * Format is inventoryAdd <UPC> <quantity>
     * @param args: inventory command
     *
     */
    @Override
    public void execute(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Insert the UPC");
        System.out.print("> ");
        int UPC = Integer.parseInt(scanner.nextLine());
        System.out.println("Insert the quantity to add");
        System.out.print("> ");
        int quantity = Integer.parseInt(scanner.nextLine());
        Inventory.inventoryAdd(UPC, quantity);


    }
}
