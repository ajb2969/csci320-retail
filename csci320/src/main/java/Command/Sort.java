package main.java.Command;

/**
 * Sort execute command to sort the products by weight or by price
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 *
 */
public class Sort implements Command{

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
                // TODO SORT BY PRICE
            } else if (sortType.equals("weight")) {
                // TODO SORT BY WEIGHT
            } else {
                System.err.println("Illegal command. Format: usage: sort price OR sort weight");
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: usage: sort price OR sort weight");
        }
    }
}
