package oop_1;

import java.io.Serializable;

public abstract class User implements Observer, Serializable {
    protected String name;

    public User(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println("Notification: " + message);
    }

    public String getName() {
        return name;
    }
}
