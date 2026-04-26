package oop_1;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {
    private String name;
    private int credits;

    private List<Student> students = new ArrayList<>();
    private List<String> teachers = new ArrayList<>();

    public Course(String name, int credits) {
        this.name = name;
        this.credits = credits;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addTeacher(String teacher) {
        teachers.add(teacher);
    }

    public String getName() { return name; }
    public int getCredits() { return credits; }
}
