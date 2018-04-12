package main.java.Command;

/**
 * Created by Kevin Bastian
 */
public class Logout implements Command{

    /**
     * Execute this by closing the application
     * @param args: No arguments should be given
     */
    @Override
    public void execute(String[] args) {
        // Add sql stuff here Maybe print stuff too
        System.out.println("EXITING");
        System.exit(0);
    }
}
