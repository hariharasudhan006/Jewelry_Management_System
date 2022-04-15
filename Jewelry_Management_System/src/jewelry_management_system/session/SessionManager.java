package jewelry_management_system.session;

import java.io.*;

public final class SessionManager {

    private static Session currentSession = null;
    private static final String fileName = "ssn.txt";

    public static Session CreateNewSession(String username){
        currentSession = new Session(username);
        return currentSession;
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
            file.delete();
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
                file.createNewFile();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(file));
                objectOutputStream.writeObject(currentSession);
                objectOutputStream.flush();
                objectOutputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
