package oop_1;

public class UserFactory {
    public static User createUser(String type, String name) {
        if (type.equalsIgnoreCase("STUDENT")) {
            return new Student(name);
        }
        return null;
    }
}
