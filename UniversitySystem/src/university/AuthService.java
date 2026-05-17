package university;

public class AuthService {

    private static AuthService instance;
    private User currentUser;

    private AuthService() {
    }

    public static AuthService getInstance() {
        if (instance == null)
            instance = new AuthService();
        return instance;
    }

    public User login(String login, String password) {
        User user = Database.getInstance().findByLogin(login);
        if (user == null) {
            System.out.println("[Auth] User not found: " + login);
            Logger.log("Failed login attempt for login: " + login);
            return null;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("[Auth] Wrong password for: " + login);
            Logger.log("Wrong password attempt for: " + login);
            return null;
        }
        this.currentUser = user;
        Logger.log("User logged in: " + login + " | Role: " + user.getClass().getSimpleName());
        System.out.println("[Auth] Welcome, " + user.getName() +
                " | Role: " + user.getClass().getSimpleName());
        return user;
    }

    public void logout() {
        if (currentUser != null) {
            Logger.log("User logged out: " + currentUser.getLogin());
            System.out.println("[Auth] Goodbye, " + currentUser.getName());
            currentUser = null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public String getCurrentRole() {
        if (currentUser == null)
            return "None";
        return currentUser.getClass().getSimpleName();
    }
}