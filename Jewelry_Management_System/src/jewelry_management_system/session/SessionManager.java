package jewelry_management_system.session;

import java.io.*;

public final class SessionManager {

    private static Session currentSession = null;
    private static final String fileName = "ssn.rgl";

    public static void CreateNewSession(String username){
        currentSession = new Session(username);
    }

    public static Session loadSession(){
        readSessionObject();
        if(currentSession != null) {
            System.out.println(currentSession);
            if(currentSession.isSessionOver()){
                return currentSession;
            }
        }
        System.out.println("Null session");
        return null;
    }

    public static Session getCurrentSession(){
        return currentSession;
    }

    public static void purgeCurrentSession(){
        File file = new File(fileName);
        if(file.exists()){
            if(!file.delete()){
                System.out.println("Unable to remove session file do it manually");
            }
        }
        currentSession = null;
    }

    private static void readSessionObject(){
        File file = new File(fileName);
        try {
            if (file.exists()) {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
                Object obj = objectInputStream.readObject();
                objectInputStream.close();
                currentSession = (Session) obj;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void storeCurrentSession(){
        try {
            if (currentSession != null) {
                File file = new File(fileName);
                if(file.createNewFile()) {
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                    objectOutputStream.writeObject(currentSession);
                    objectOutputStream.flush();
                    objectOutputStream.close();
                }else{
                    System.out.println("Unable to create new file in session manager");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
