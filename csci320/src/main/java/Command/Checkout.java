package main.java.Command;


import main.java.ShoppingCart;

public class Checkout implements Command {
    public void execute(String [] args) {
        System.out.println("Checkout products.");
        ShoppingCart sc = new Add().getCart();
        
        // TODO Implement the backend of checkout
    }
}
