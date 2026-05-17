package src.university;

import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

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
        this.yearOfStudy = yearOfStudy;
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

    public void removePendingStudent(Student s) {
        pendingStudents.remove(s);
    }

    public void addTeacher(Teacher t) {
        if (!teachers.contains(t)) {
            teachers.add(t);
        }
    }

    public String generateReport() {
        if (students.isEmpty())
            return "=== " + name + ": No students enrolled ===";

        DoubleSummaryStatistics stats = students.stream()
                .map(s -> s.getMark(this))
                .filter(Objects::nonNull)
                .mapToDouble(Mark::getTotal)
                .summaryStatistics();

        if (stats.getCount() == 0)
            return "=== " + name + ": No marks recorded ===";

        long passed = students.stream()
                .map(s -> s.getMark(this))
                .filter(m -> m != null && m.isPassed())
                .count();

        return String.format(
                "=== Report: %s ===%n  Enrolled : %d%n  Graded   : %d%n" +
                        "  Avg      : %.2f%n  Max      : %.2f%n  Min      : %.2f%n  Passed   : %d/%d",
                name, students.size(), (int) stats.getCount(),
                stats.getAverage(), stats.getMax(), stats.getMin(),
                passed, (int) stats.getCount());
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

    public int getYearOfStudy() {
        return yearOfStudy;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Course))
            return false;
        Course c = (Course) o;
        return Objects.equals(name, c.name) && Objects.equals(major, c.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, major);
    }

    @Override
    public String toString() {
        return "Course{name='" + name + "', credits=" + credits +
                ", major='" + major + "', year=" + yearOfStudy + "}";
    }
}