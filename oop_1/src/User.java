package src; 
import java.io.Serializable;

public abstract class User implements Serializable {
    protected String name;
    protected String login;
    protected String password;
    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }
    
    public String getName() {
        return name;
    }
}