package university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Teacher extends Employee implements Serializable, Comparable<Teacher> {
    private static final long serialVersionUID = 1L;
    private TeacherTitle title;
    private List<Course> courses;
    private List<Integer> ratings;

    public Teacher(String login, String password, String firstName, String lastName, String employeeId, double salary, TeacherTitle title) {
        super(login, password, firstName, lastName, employeeId, salary);
        this.title = title;
        this.courses = new ArrayList<>();
        this.ratings = new ArrayList<>();
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses assigned.");
            return;
        }
        System.out.println("=== Your Courses ===");
        for (Course c : courses) {
            System.out.println("  - " + c.getCourseName() + " | " + c.getCredits() + " credits | Students: " + c.getStudents().size());
        }
    }

    public void putMark(Student student, Course course, Mark mark) {
        if (!courses.contains(course)) {
            System.out.println("Error: You are not assigned to " + course.getCourseName());
            return;
        }
        if (!course.getStudents().contains(student)) {
            System.out.println("Error: Student not enrolled in " + course.getCourseName());
            return;
        }
        student.addMark(course, mark);
        System.out.println("Mark recorded for " + student.getFirstName() + " " + student.getLastName() + " in " + course.getCourseName());
    }

    public void viewStudents(Course course) {
        if (!courses.contains(course)) {
            System.out.println("Error: You don't teach " + course.getCourseName());
            return;
        }
        System.out.println("=== Students in " + course.getCourseName() + " ===");
        for (Student s : course.getStudents()) {
            System.out.println("  " + s.getFirstName() + " " + s.getLastName() + " | GPA: " + String.format("%.2f", s.getGPA()));
        }
    }

    public void manageCourse(Course course) {
        if (!courses.contains(course)) {
            System.out.println("Error: You don't teach " + course.getCourseName());
            return;
        }
        System.out.println("=== Managing: " + course.getCourseName() + " ===");
        System.out.println("  Credits: " + course.getCredits());
        System.out.println("  Enrolled: " + course.getStudents().size());
        System.out.println("  Instructors: " + course.getTeachers().size());
    }

    public void receiveRating(int rating) {
        if (rating < 1 || rating > 5) {
            System.out.println("Invalid rating. Must be 1-5.");
            return;
        }
        ratings.add(rating);
        System.out.println("Rating of " + rating + " recorded for " + getFirstName() + " " + getLastName());
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) return 0.0;
        return ratings.stream().mapToInt(i -> i).average().orElse(0.0);
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public TeacherTitle getTitle() { return title; }
    public void setTitle(TeacherTitle title) { this.title = title; }
    public List<Course> getCourses() { return courses; }
    public List<Integer> getRatings() { return ratings; }

    @Override
    public int compareTo(Teacher other) {
        return this.getLastName().compareTo(other.getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher t = (Teacher) o;
        return Objects.equals(getEmployeeId(), t.getEmployeeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId());
    }

    @Override
    public String toString() {
        return title + " " + getFirstName() + " " + getLastName() + " | Avg Rating: " + String.format("%.1f", getAverageRating());
    }
}