package main.java.Command;
import main.java.Inventory;
import main.java.User;

import java.util.Scanner;

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
            Scanner input = new Scanner(System.in);
            System.out.print("Enter sort type(brand, price, product): ");
            String sortBy = input.nextLine();
            sortBy = sortBy.toLowerCase();

            System.out.print("Sort by ASC or DESC: ");
            String ascDesc = input.nextLine();
            ascDesc = ascDesc.toUpperCase();
            String[] username;

            if(User.getUserName() == null){
                username = new String[2];
                username[0] = "Guest";
                username[1] = "Guest";
            }else{
                username = User.getUserName().split(" ");
            }
            String first = username[0];
            String last = username[1];
            if (sortBy.equals("brand") && ascDesc.equals("ASC")) {
                Inventory.sortBy(SortBy.brandAsc, first, last);
            } else if (sortBy.equals("brand") && ascDesc.equals("DESC")) {
                Inventory.sortBy(SortBy.brandDesc, first, last);
            } else if (sortBy.equals("price") && ascDesc.equals("ASC")) {
                Inventory.sortBy(SortBy.priceAsc, first, last);
            } else if (sortBy.equals("price") && ascDesc.equals("DESC")) {
                Inventory.sortBy(SortBy.priceDesc, first, last);
            } else if (sortBy.equals("product") && ascDesc.equals("ASC")) {
                Inventory.sortBy(SortBy.productAsc, first, last);
            } else if (sortBy.equals("product") && ascDesc.equals("DESC")) {
                Inventory.sortBy(SortBy.productDesc, first, last);
            } else {
                System.err.println("Illegal command. Format usage: sort by: brand, price, product");
                System.err.println("Illegal command. Format usage: ASC OR DESC");
            }
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException ae) {
            System.err.println("Illegal command. Format: usage: sort price OR sort weight");
        }
    }
}
