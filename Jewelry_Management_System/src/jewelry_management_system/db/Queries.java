package jewelry_management_system.db;

public class Queries {

    public static String userSearchQuery(String username){
        return "select * from jms_users where USERNAME like '" + username + "';";
    }

    public static String userPasswordQuery(String username){
        return "select PASSWORD from jms_users where USERNAME like '" + username + "';";
    }

    public static String updateUserPassword(String username, String password){
        return "update jms_users set PASSWORD='" + password +"' where USERNAME like'"+username+"';";
    }

    public static String updateUserName(String newUsername, String oldUsername){
        return "update jms_users set USERNAME='" + newUsername + "'where USERNAME like '" + oldUsername +"';";
    }
}
