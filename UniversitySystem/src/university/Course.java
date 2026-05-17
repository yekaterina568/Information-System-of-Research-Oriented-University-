package university;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {

    private String name;
    private int credits;
    private String major;
    private int yearOfStudy;

    private List<Student> students = new ArrayList<>();
    private List<Student> pendingStudents = new ArrayList<>();
    private List<Teacher> teachers = new ArrayList<>();

    public Course(String name, int credits, String major, int yearOfStudy) {
        this.name = name;
        this.credits = credits;
        this.major = major;
        this.yearOfStudy =yearOfStudy;
    }

    public void addStudent(Student s) {
        if (!students.contains(s)) {
            students.add(s);
        }
    }

    public void addPendingStudent(Student s) {
        if (!pendingStudents.contains(s)) {
            pendingStudents.add(s);
        }
    }

    public void addTeacher(Teacher t) {
        if (!teachers.contains(t)) {
            teachers.add(t);
        }
    }

    public String getName() {
        return name;
    }

    public String getCourseName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public String getMajor() {
        return major;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Student> getPendingStudents() {
        return pendingStudents;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
}