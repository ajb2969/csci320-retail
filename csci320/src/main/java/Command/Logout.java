package main.java.Command;

import main.java.InitRetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
        if(args[0] == null){
            try{
                String resetStore = "Update Customer set hstoreId = 1 where fname = \'Guest\';";
                PreparedStatement ps = InitRetail.getConnection().prepareStatement(resetStore);
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("EXITING");
        System.exit(0);
    }
}
