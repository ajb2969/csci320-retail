package main.java.Command;

import main.java.ShoppingCart;

import java.util.Scanner;

public class Add implements Command{
    private static ShoppingCart sc = new ShoppingCart();
    public Add(){

    }

    public ShoppingCart getCart(){
        return this.sc;
    }
    public void execute(String [] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the upc of the item: ");
        int upc = input.nextInt();
        System.out.print("Enter how many items you would like: ");
        int quantity = input.nextInt();
        this.sc.addToCart(upc,quantity,args);
    }
}
