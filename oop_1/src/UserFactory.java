package src;

public class UserFactory {
    public static User createUser(String type, String name, String login, String password) {
        if (type.equalsIgnoreCase("STUDENT")) {
            return new Student(name, login, password); 
        }
        return null;
    }
}