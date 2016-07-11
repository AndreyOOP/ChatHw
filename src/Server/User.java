package Server;

import java.io.Serializable;

public class User implements Serializable{

    private String name;
    private String password;
    transient private Boolean isOnline;

    public User(String name, String password, Boolean isOnline) {
        this.name = name;
        this.password = password;
        this.isOnline = isOnline;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
