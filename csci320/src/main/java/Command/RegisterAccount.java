package main.java.Command;
import main.java.InitRetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
/**
 *
 * @author jahongiramirkulov
 * @version 04/13/18
 */
public class RegisterAccount implements Command{


    public String convertListToString(ArrayList<String> kk){
        return String.format("%d,\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',\'%s\',%d,\'%s\'",
                Integer.parseInt(kk.get(0)),
                kk.get(1),
                kk.get(2),
                kk.get(3),
                kk.get(4),
                kk.get(5),
                kk.get(6),
                kk.get(7),
                kk.get(8),
                kk.get(9),
                Integer.parseInt(kk.get(10)),
                kk.get(11)
        );
    }

    public void execute(String [] args) {
        try {
            try {
                String idQuery = "Select max(id) from Customer;";
                Statement s = InitRetail.getConnection().createStatement();
                ResultSet rs = s.executeQuery(idQuery);
                while(rs.next()){
                    idQuery = String.valueOf(rs.getInt(1));
                }
                ArrayList<String> insertArgs = new ArrayList<String>();
                Scanner input = new Scanner(System.in);
                insertArgs.add(String.valueOf(Integer.parseInt(idQuery)+1));
                System.out.print("Please enter your first name: ");
                String first = input.nextLine();
                insertArgs.add(first);
                System.out.print("Please enter your middle name: ");
                String middle = input.nextLine();
                insertArgs.add(middle);
                System.out.print("Please enter your last name: ");
                String last = input.nextLine();
                insertArgs.add(last);
                System.out.print("Please enter your address: ");
                String addr = input.nextLine();
                insertArgs.add(addr);
                System.out.print("Please enter your city: ");
                String city = input.nextLine();
                insertArgs.add(city);
                System.out.print("Please enter your state: ");
                String state = input.nextLine();
                insertArgs.add(state);
                System.out.print("Please enter your zipcode: ");
                int zip = input.nextInt();
                insertArgs.add(String.valueOf(zip));
                System.out.print("Please enter your country: ");
                String country = input.next();
                insertArgs.add(country);
                String email = "";
                insertArgs.add(email);
                System.out.print("Please enter your homestoreID: ");
                int homeId = input.nextInt();
                insertArgs.add(String.valueOf(homeId));
                insertArgs.add("1");
                input.close();
                String insertQuery = "insert into Customer values (" + convertListToString(insertArgs) + ");";
                s.execute(insertQuery);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (IllegalArgumentException | InputMismatchException e) {
            System.err.println("Illegal RegisterAccount command input.");
        }
    }
}
