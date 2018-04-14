package main.java.Command;

import main.java.InitRetail;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Inventory command to view the inventory
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 */
public class Inventory implements Command {

    /**
     * Inventory execute command to view the inventory
     * @param args: Given arguments for the command to be fully
     */
    @Override
    public void execute(String [] args) {//firstname[0],lastname[1]
        if(args[0] == null){
            main.java.Inventory.printInventory(null,null);
        }else{
            String [] name = args[0].split(" ");
            main.java.Inventory.printInventory(name[0],name[1]);
        }


    }
}
 