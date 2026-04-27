package src;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private int totalCredits = 0;
    private int failedCourses = 0;
    private Map<Course, Mark> marks = new HashMap<>();

    private static final int MAX_CREDITS = 21;
    private static final int MAX_FAILURES = 3;

    public Student(String name, String login, String password) {
        super(name, login, password);
    }

    public void enrollCourse(Course course) throws MaxCreditsException {
    if (totalCredits + course.getCredits() > MAX_CREDITS) {
        Logger.log("Error: Student " + name + " exceeded the credit limit.");
        throw new MaxCreditsException("Credit limit exceeded (" + MAX_CREDITS + ") for " + name);
    }

    totalCredits += course.getCredits();
    course.addStudent(this);
    Logger.log("Student " + name + " enrolled in course: " + course.getName());
}

    public void setMark(Course course, Mark mark) {
        marks.put(course, mark);
        Logger.log("Mark assigned to student " + name + " for course: " + course.getName());

        if (mark.getTotal() < 50) {
            failedCourses++;
        }
    
        if (failedCourses > MAX_FAILURES) {
            Logger.log("CRITICAL: Student " + name + " dismissed (more than 3 failed courses).");
        }
}

    public Mark getMark(Course course) {
        return marks.get(course);
    }
}