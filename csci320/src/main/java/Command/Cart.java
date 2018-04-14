package main.java.Command;

import main.java.ShoppingCart;
import static main.java.ShoppingCart.printCart;

/**
 *
 */
public class Cart implements Command {

    /**
     * Cart execute command
     * @param args: cart command
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Viewing cart.");
        printCart();
    }
}
