/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelry_management_system;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author MYPC
 */
public class DBHandler {
    public DBHandler()
    {
        String forName = "com.mysql.cj.jdbc.Driver";
        try {
            Class.forName(forName);
            System.out.println("Driver Loaded Successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Failed To Load Successfully");
            System.out.println(ex.getMessage());
        }
    }
    public boolean verify_user(String user_name, String password)
    {
        return false;
    }
}