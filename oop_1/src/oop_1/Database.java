package oop_1;

import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance = new Database();

    private List<Course> courses = new ArrayList<>();

    private Database() {}

    public static Database getInstance() {
        return instance;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
