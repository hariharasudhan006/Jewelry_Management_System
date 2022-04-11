/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jewelry_management_system;
import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;

/**
 *
 * @author MYPC
 */
public class DBHandler {
    Statement stmt;
    Connection con;
    public DBHandler(String username, String password) throws SQLException
    {
        String forName = "com.mysql.jdbc.Driver";
        try {
            Class.forName(forName);
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/jms","root","root");
            stmt = con.createStatement();
            System.out.println("Driver Loaded Successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver Failed To Load Successfully");
            System.out.println(ex);
        }
    }

    boolean verify_user(String username, String password) throws SQLException {
        boolean x=false;
        try
        {
            String query = "SELECT PASSWORD FROM jms_users WHERE USERNAME LIKE '"+username+"'";
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
                if(rs.next())
                {
                    if(rs.getString("Password").equals(password))
                        x = true;
                }
        }
        catch(Exception e)
        {
            System.out.println(e);
            x = false;
        }
        return x;
    }
    
    int change_username(String old_user_name, String new_user_name)
    {
        int x=0;
        try
        {
            String query = "UPDATE jms_users SET USERNAME='" + new_user_name +"' WHERE USERNAME LIKE '"+old_user_name+"'";
            System.out.println(query);
            x=stmt.executeUpdate(query);
            stmt.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return x;
    }
    
}