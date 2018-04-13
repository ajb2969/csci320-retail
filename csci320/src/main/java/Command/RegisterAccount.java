package main.java.Command;
import java.util.*;
/**
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 */
public class RegisterAccount implements Command{
    /**
     * Executes the RegisterAccount command
     * @param args: Given arguments for the command to be fully
     */
    public void execute(String [] args) {
        try {
            Scanner input = new Scanner(System.in);
            System.out.print("Please enter your first name: ");
            String first = input.nextLine();
            System.out.print("Please enter your middle name: ");
            String middle = input.nextLine();
            System.out.print("Please enter your last name: ");
            String last = input.nextLine();
            System.out.print("Please enter your address: ");
            String addr = input.nextLine();
            System.out.print("Please enter your country: ");
            String country = input.nextLine();
            System.out.print("Please enter your state: ");
            String state = input.nextLine();
            System.out.print("Please enter your city: ");
            String city = input.nextLine();
            System.out.print("Please enter your zipcode: ");
            int zip = input.nextInt();
            System.out.print("Please enter your email: ");
            String email = input.nextLine();
            System.out.print("Please enter your homestoreID: ");
            int homeId = input.nextInt();
            // TODO Add the backend func call
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal RegisterAccount command input.");
        }
    }
}
