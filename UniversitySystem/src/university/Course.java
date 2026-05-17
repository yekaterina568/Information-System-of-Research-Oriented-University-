package university;

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
        this.yearOfStudy =yearOfStudy;
    }

    public void addStudent(Student s) {
        if (!students.contains(s)) {
            students.add(s);
        }
        pendingStudents.remove(s);
    }

    public void addPendingStudent(Student s) {
        if (!pendingStudents.contains(s)) {
            pendingStudents.add(s);
        }
    }

    public void removePendingStudent(Student s) {
        pendingStudents.remove(s);
    }

    public void removeStudent(Student s) {
        students.remove(s);
    }

    public void addTeacher(Teacher t) {
        if (!teachers.contains(t)) {
            teachers.add(t);
        }
    }

    public void removeTeacher(Teacher t) {
        teachers.remove(t);
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

    public double getAverageMark() {
        return students.stream()
                .map(Student::getMarks)
                .map(marks -> marks.get(this))
                .filter(Objects::nonNull)
                .mapToDouble(Mark::getTotal)
                .average()
                .orElse(0.0);
    }

    public long getPassedStudentsCount() {
        return students.stream()
                .map(student -> student.getMark(this))
                .filter(Objects::nonNull)
                .filter(Mark::isPassed)
                .count();
    }

    public long getFailedStudentsCount() {
        return students.stream()
                .map(student -> student.getMark(this))
                .filter(Objects::nonNull)
                .filter(mark -> !mark.isPassed())
                .count();
    }

    public String generateReport() {
        String teacherNames = teachers.isEmpty()
                ? "No teachers assigned"
                : teachers.stream()
                        .map(Teacher::getName)
                        .sorted()
                        .reduce((left, right) -> left + ", " + right)
                        .orElse("No teachers assigned");

        return String.format(
                Locale.US,
                "Course: %s | Credits: %d | Major: %s | Year: %d | Teachers: %s | Enrolled: %d | Pending: %d | Avg Mark: %.2f | Pass: %d | Fail: %d",
                name,
                credits,
                major == null || major.isBlank() ? "Any" : major,
                yearOfStudy,
                teacherNames,
                students.size(),
                pendingStudents.size(),
                getAverageMark(),
                getPassedStudentsCount(),
                getFailedStudentsCount());
    }
}
