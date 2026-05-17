package university;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

import university.Course;
import university.Database;
import university.Employee;
import university.ManagerType;
import university.Mark;
import university.MaxCreditsException;
import university.Request;
import university.Student;
import university.User;

public class Manager extends Employee implements Serializable {

    private ManagerType managerType;
    private List<Request> signedRequests = new ArrayList<>();
    private List<String> news = new ArrayList<>();

    public Manager(String id, String name, String login, String password,
            String email, double salary, ManagerType managerType) {
        super(id, name, login, password, email, salary);
        this.managerType = managerType;
    }

    public void approveRegistration(Student student, Course course) {
        try {
            if (student.getMajor() == null ||
                    !student.getMajor().equals(course.getMajor())) {
                System.out.println("Major mismatch");
                return;
            }

            student.enrollCourse(course);
            System.out.println("Approved: " + student.getName());

        } catch (MaxCreditsException e) {
            System.out.println("Cannot enroll: " + e.getMessage());
        }
    }

    public void signRequest(Request request) {

        if (managerType != ManagerType.DEAN &&
                managerType != ManagerType.RECTOR) {
            throw new IllegalArgumentException("Only DEAN or RECTOR can sign requests");
        }

        request.sign(this);
        signedRequests.add(request);

        System.out.println("Request signed by " + getName());
    }

    public void viewSignedRequests() {
        System.out.println("=== Signed Requests ===");
        if (signedRequests.isEmpty()) {
            System.out.println("No signed requests");
            return;
        }
        for (Request r : signedRequests) {
            System.out.println(r);
        }
    }

    public void addNews(String item) {
        news.add(item);
        System.out.println("News posted: " + item);
    }

    public void viewNews() {
        System.out.println("=== News ===");

        if (news.isEmpty()) {
            System.out.println("No news.");
            return;
        }

        for (int i = 0; i < news.size(); i++) {
            System.out.println((i + 1) + ". " + news.get(i));
        }
    }

    public void createCourseReport() {

        List<Course> courses = Database.getInstance().getCourses();

        System.out.println("===== COURSE REPORT =====");

        for (Course c : courses) {

            double avg = c.getStudents().stream()
                    .mapToDouble(s -> {
                        Mark m = s.getMark(c);
                        return m != null ? m.getTotal() : 0;
                    })
                    .average()
                    .orElse(0);

            System.out.println(c.getName() +
                    " | Average mark: " +
                    String.format("%.2f", avg));
        }
    }

    public void createStatisticalReport() {

        List<Student> students = Database.getInstance().getAllUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .collect(Collectors.toList());

        System.out.println("===== UNIVERSITY STATISTICAL REPORT =====");

        double avgGPA = students.stream()
                .mapToDouble(Student::getGPA)
                .average()
                .orElse(0);

        System.out.println("Average GPA: " + String.format("%.2f", avgGPA));

        Student best = students.stream()
                .max(Comparator.comparingDouble(Student::getGPA))
                .orElse(null);

        Student worst = students.stream()
                .min(Comparator.comparingDouble(Student::getGPA))
                .orElse(null);

        System.out.println("Best student: " + best);
        System.out.println("Worst student: " + worst);

        long passed = students.stream().filter(s -> s.getGPA() >= 2.0).count();
        long failed = students.size() - passed;

        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);

        for (Course c : Database.getInstance().getCourses()) {

            double avg = c.getStudents().stream()
                    .mapToDouble(s -> {
                        Mark m = s.getMark(c);
                        return m != null ? m.getTotal() : 0;
                    })
                    .average()
                    .orElse(0);

            System.out.println(c.getName() +
                    " avg mark: " +
                    String.format("%.2f", avg));
        }
    }

    public void viewStudentsByGPA() {

        List<Student> students = Database.getInstance().getAllUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparingDouble(Student::getGPA).reversed())
                .collect(Collectors.toList());

        for (Student s : students) {
            System.out.println(s.getName() + " | GPA: " + s.getGPA());
        }
    }

    public void viewStudentsAlphabetically() {

        Database.getInstance().getAllUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparing(User::getName))
                .forEach(s -> System.out.println(s.getName()));
    }

    public void viewStudentsByCredits() {

        Database.getInstance().getAllUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparing(Student::getTotalCredits).reversed())
                .forEach(s -> System.out.println(s.getName() +
                        " | Credits: " + s.getTotalCredits()));
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }
}