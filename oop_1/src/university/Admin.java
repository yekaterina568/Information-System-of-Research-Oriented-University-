package src.university;
import java.util.List;
public class Admin extends User {
    private static final long serialVersionUID = 1L;

    public Admin(String id, String name,String login, String password, String email) {
        super(id,name,login, password, email);
    }

    public void addUser(User user) {
        Database.getInstance().addUser(user);
        Logger.log("Admin " + this.name + " added user: " + user.getLogin());
    }

    public void removeUser(User user) {
        boolean removed = Database.getInstance().removeUser(user.getId());

        if (removed) {
            Logger.log("Admin " + this.name + " removed user: " + user.getLogin());
        } else {
            Logger.log("Admin " + this.name + " failed to remove user: " + user.getLogin());
        }
    }
    public void updateUser(User user,String newName,String newLogin, String newPassword,String newEmail) {
    	user.setName(newName);
        user.setLogin(newLogin);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        
        Logger.log("Admin "
                + this.name
                + " updated user: "
                + user.getLogin());
        Database.saveDatabase();
    }
    public void viewLogs() {
    	List<String> logs =Logger.readLogs();
    	if (logs.isEmpty()) {
    		System.out.println("No logs found.");
    		return;
    	}
    	System.out.println("========== SYSTEM LOGS ==========");
    	for (String log : logs) {
    		System.out.println(log);
    	}
    }
   
}
