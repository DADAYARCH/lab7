package server.database;

import java.io.Serializable;

public class UserData implements Serializable {
    public String username;
    public String password;

    public UserData(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
