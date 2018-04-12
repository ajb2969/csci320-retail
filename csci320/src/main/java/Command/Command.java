package main.java.Command;

/**
 * Created by Kevin Bastian
 */
public interface Command {
    /**
     * Execute the command
     * @param args: Given arguments for the command to be fully
     *              executed
     */
    public abstract void execute(String[] args);
}
