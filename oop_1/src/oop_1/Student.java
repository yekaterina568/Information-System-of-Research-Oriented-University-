package oop_1;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student extends User implements Serializable {
    private int totalCredits = 0;
    private int failedCourses = 0;

    private Map<Course, Mark> marks = new HashMap<>();

    private static final int MAX_CREDITS = 21;
    private static final int MAX_FAILURES = 3;

    public Student(String name) {
        super(name);
    }

    public boolean enrollCourse(Course course) {
        if (totalCredits + course.getCredits() > MAX_CREDITS) {
            System.out.println("Credit limit exceeded");
            return false;
        }

        totalCredits += course.getCredits();
        course.addStudent(this);
        return true;
    }

    public void setMark(Course course, Mark mark) {
        marks.put(course, mark);

        if (mark.getTotal() < 50) {
            failedCourses++;
        }

        if (failedCourses > MAX_FAILURES) {
            System.out.println("Student dismissed");
        }
    }

    public Mark getMark(Course course) {
        return marks.get(course);
    }
}
