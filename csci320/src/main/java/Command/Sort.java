package main.java.Command;

import java.util.InputMismatchException;

/**
 *
 */
public class Sort implements Command{
    public Sort() {}

    /**
     *
     * @param args: rest of the input
     *            usage: sort price
     *            OR     sort weight
     */
    public void execute(String [] args) {
        try {

            String sortType = args[1];
            if (sortType.equals("price")) {
                // SORT BY PRICE
            } else if (sortType.equals("weight")) {
                // SORT BY WEIGHT
            } else {
                System.err.println("Illegal command. Format: usage: sort price OR sort weight");
            }
            // TODO Add the backend restock command.
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: usage: sort price OR sort weight");
        }
    }
}
