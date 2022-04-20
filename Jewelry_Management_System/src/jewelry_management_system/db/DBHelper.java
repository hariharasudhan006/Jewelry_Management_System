package jewelry_management_system.db;

import jewelry_management_system.dashboard.TableData;
import jewelry_management_system.session.Session;
import jewelry_management_system.session.SessionManager;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DBHelper {
    private static DBHelper helper = null;
    private final String driverClassName = "com.mysql.jdbc.Driver";
    private Connection dbConnection = null;
    private Statement statement;
    private final Session session;

    public static DBHelper getDBHelperInstance(){

        if(helper != null){
          return helper;
        }
        helper = null;
        helper = new DBHelper();
        return helper;
    }

    public static DBHelper getDBHelperInstance(String username, String password){
        if(helper != null){
            return helper;
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

    public boolean updatePassword(String newPassword){
        boolean isPasswordChanged = false;
        try {
            statement.executeUpdate(Queries.updateUserPassword(session.getUsername(), newPassword));
            isPasswordChanged = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isPasswordChanged;
    }

    public List<TableData> GetStockTableData() {
        List<TableData> res;
        try {
            ResultSet resultSet = statement.executeQuery(Queries.stockTableSelect());
            res = new ArrayList<>();
            while(resultSet.next()){
                TableData data = new TableData();
                data.setId(resultSet.getString("id"));
                data.setName(resultSet.getString("name"));
                data.setPrice(resultSet.getInt("price"));
                data.setWeight(resultSet.getDouble("weight"));
                res.add(data);
            }
            return res;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean verifyJewelId(String Id){
        boolean res = false;
        try{
           ResultSet set = statement.executeQuery(Queries.selectJewelById(Id));
           res = set.next();
        }catch(SQLException e){
            
        }
        return res;
    }
    
    
    public boolean InsertBill(double discount, double wastage, double tax, String stockId, String custName, String custAddress, String custPhone){
        try{
            statement.executeUpdate(Queries.InsertCustomer(custName, custAddress, custPhone));
            ResultSet customerSet = statement.executeQuery(Queries.SelectLastCustomerID());
            int custId = 0;
            if(customerSet.next())
                custId = customerSet.getInt("id");
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
            statement.executeUpdate(Queries.InsertBillQuery(discount, wastage, tax, stockId, date, custId));
            statement.executeUpdate(Queries.updateStockSoldStatus(stockId));
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public String[] GetProductInsightData(String id){
        String[] result = new String[5];
        System.out.println(Queries.selectJewelById(id));
        try{
            ResultSet set = statement.executeQuery(Queries.selectJewelById(id));
            if(set.next()){
                int i = 0;
                result[0] = set.getString("name");
                result[1] = String.valueOf(set.getInt("price"));
                result[2] = String.valueOf(set.getDouble("discount"));
                result[3] = String.valueOf(set.getDouble("carat"));
                result[4] = String.valueOf(set.getDouble("weight"));
                return result;
            }
            return null;
        }catch(SQLException e){
            return null;
        }
    }
    
    public boolean UpdateJewelDetails(String id, String name, String price, 
            String discount, String carat, String weight){
        try{
            statement.executeUpdate(Queries.updateJewelQuery(id, name, price, 
                    discount, carat, weight));
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        
    }
    
    public boolean InsertNewStock(String id, String name, int price, double discount, int carat, double weight){
        String query = "insert into stock values('" +id+ "', '"+name+
                "', "+price+", " + discount +", " + carat +", " + weight +", "+0+");";
        try{
            statement.executeUpdate(query);
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    public void close(){
        try {
            statement.close();
            dbConnection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public boolean deleteJewel(String id){
        try{
            statement.executeUpdate(Queries.deletJeweQuery(id));
            return true;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
}
