package main.java.Command;

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
        // TODO Call the backend to look at the cart.

    }
}
