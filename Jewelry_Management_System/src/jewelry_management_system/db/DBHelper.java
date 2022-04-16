package jewelry_management_system.db;

import jewelry_management_system.session.Session;
import jewelry_management_system.session.SessionManager;

import java.sql.*;

public class DBHelper {
    private static DBHelper helper = null;
    private final String driverClassName = "com.mysql.jdbc.Driver";
    private Connection dbConnection = null;
    private Statement statement;
    private final Session session;

    public static DBHelper getDBHelperInstance(){

        if(helper != null){
            helper.close();
        }
        helper = null;
        helper = new DBHelper();
        return helper;
    }

    public static DBHelper getDBHelperInstance(String username, String password){
        if(helper != null){
            helper.close();
        }
        helper = null;
        helper = new DBHelper(username, password);
        return helper;
    }
    private DBHelper(String username, String password) {
        try {
            Class.forName(driverClassName);
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jms", username, password);
            statement = dbConnection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        session = SessionManager.getCurrentSession();
    }

    private DBHelper(){
        try {
            Class.forName(driverClassName);
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jms", "root", "");
            statement = dbConnection.createStatement();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        session = SessionManager.getCurrentSession();
    }

    public boolean isUserExists(String username){
        boolean isUserExists = false;
        try {
            ResultSet resultSet = statement.executeQuery(Queries.userSearchQuery(username));
            isUserExists = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isUserExists;
    }

    public boolean verifyUser(String username, String password){
        boolean isValidUser = false;
        try {
            ResultSet resultSet = statement.executeQuery(Queries.userPasswordQuery(username));
            if(resultSet.next()){
                String pwdFromDb = resultSet.getString("PASSWORD");
                if(pwdFromDb.equals(password)){
                    isValidUser = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValidUser;
    }

    public boolean verifyPassword(String password){
        boolean isValid = false;
        try{
            ResultSet resultSet = statement.executeQuery(Queries.userPasswordQuery(session.getUsername()));
            if(resultSet.next()){
                String pwdFromDb = resultSet.getString("PASSWORD");
                if(pwdFromDb.equals(password)){
                    isValid = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public boolean updateUsername(String newUsername){
        boolean isUsernameChanged = false;
        try{
            statement.executeUpdate(Queries.updateUserName(newUsername, session.getUsername()));
            isUsernameChanged = true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return isUsernameChanged;
    }

    public boolean updataPassword(String newPassword){
        boolean isPasswordChanged = false;
        try {
            statement.executeUpdate(Queries.userPasswordQuery(session.getUsername()));
            isPasswordChanged = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isPasswordChanged;
    }

    public void close(){
        try {
            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
