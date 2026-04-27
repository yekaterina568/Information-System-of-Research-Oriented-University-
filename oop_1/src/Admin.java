package src;

public class Admin extends User {
    private static final long serialVersionUID = 1L;

    public Admin(String name, String login, String password) {
        super(name, login, password);
    }

    public void addUser(User user) {
        Database.getInstance().getUsers().add(user);
        Logger.log("Admin " + this.name + " removed user: " + user.getLogin());
    }

    public void removeUser(User user) {
        Database.getInstance().getUsers().remove(user);
        Logger.log("Admin " + this.name + " removed user: " + user.getLogin());
    }
}