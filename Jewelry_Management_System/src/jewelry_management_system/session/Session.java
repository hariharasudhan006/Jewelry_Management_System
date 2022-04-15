package jewelry_management_system.session;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

public final class Session implements Serializable {
    private final LocalDate dateOfSessionCreated;
    private final HashMap<String, String> additionData;
    private String username;
    Session(String username){
        dateOfSessionCreated = LocalDate.now();
        additionData = new HashMap<>();
        this.username = username;
    }

    boolean isSessionOver(){
        return  !dateOfSessionCreated.plusDays(10).equals(LocalDate.now())
                || dateOfSessionCreated.plusDays(10).isBefore(LocalDate.now());
    }

    public void addData(String key, String data){
        additionData.put(key, data);
    }

    public String getData(String key){
        return additionData.get(key);
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String value){
        username = value;
    }

    @Override
    public String toString() {
        return "Session{" +
                "dateOfSessionCreated=" + dateOfSessionCreated +
                "Username" + username +
                '}';
    }
}
