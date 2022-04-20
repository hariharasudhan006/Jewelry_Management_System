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

    public static String stockTableSelect(){
        return "Select id, name, price, weight from stock where isSold = 0;";
    }
    
    public static String selectJewelById(String id){
        return "select * from stock where id='" + id + "';";
    }
    
    public static String InsertCustomer(String name, String address, String phone){
        return "insert into Customer(name, address, phone) values('"+name+"', '"+ address+"', '"+phone+"');";
    }
    
    public static String SelectLastCustomerID(){
        return "Select id from Customer order by id desc limit 1;";
    }
    
    public static String InsertBillQuery(double discount, double wastage, 
            double tax, String stockId, String date, int CustID){
        
        return "insert into bill(date, discount, wastage, tax, stockId, customerId) values('"+date+"', "+discount+", "
                +wastage+", "+tax+", '"+stockId+"', "+CustID+");";
    }
    
    public static String updateStockSoldStatus(String id){
        return "update stock set isSold= 1 where id='" + id +"';";
    }
    
    public static String updateJewelQuery(String id, String name, String price, 
            String discount, String carat, String weight){
        return "update stock set name='" + name + "', price=" + price + 
                ", discount=" + discount + ", carat=" + carat + 
                ", weight=" + weight + " where id='" + id + "';";
    }
    
    public static String deletJeweQuery(String id){
        return "delete from stock where id='" + id + "';";
    }
}
