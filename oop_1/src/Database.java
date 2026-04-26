package src;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    private static transient Database instance = null;

    private List<Course> courses = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<Course> getCourses() { return courses; }
    public List<User> getUsers() { return users; }

    public static void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("database.dat"))) {
            oos.writeObject(getInstance());
            Logger.log("The database has been successfully saved to file.");
        } catch (IOException e) {
            System.err.println("The error while saving database: " + e.getMessage());
            Logger.log("The error of saving database: " + e.getMessage());
        }
    }

    public static void loadDatabase() {
        File file = new File("database.dat");
        if (!file.exists()) {
            System.out.println("The file of database hasn't been found. The new empty database was created.");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            instance = (Database) ois.readObject();
            Logger.log("The database was successfully loaded from file.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading database: " + e.getMessage());
        }
    }
}