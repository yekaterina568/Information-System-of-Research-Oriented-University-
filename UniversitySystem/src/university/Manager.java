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
    private static final long serialVersionUID = 1L;

    private ManagerType managerType;
    private List<Request> signedRequests = new ArrayList<>();
    private List<Request> pendingRequests = new ArrayList<>();
    private List<String> news = new ArrayList<>();

    public Manager(String id, String name, String login, String password,
            String email, double salary, ManagerType managerType) {
        super(id, name, login, password, email, salary);
        this.managerType = managerType;
    }

    public void approveRegistration(Student student, Course course) {
        try {
            if (!course.getPendingStudents().contains(student)) {
                System.out.println("No pending registration for " + student.getName());
                return;
            }

            if (!isEligibleForCourse(student, course)) {
                System.out.println("Registration requirements not satisfied.");
                return;
            }

            student.enrollCourse(course);
            course.removePendingStudent(student);
            Logger.log(getLogin() + " approved registration: " + student.getLogin() + " -> " + course.getName());
            Database.saveDatabase();
            System.out.println("Approved: " + student.getName());

        } catch (MaxCreditsException e) {
            System.out.println("Cannot enroll: " + e.getMessage());
        }
    }

    public void rejectRegistration(Student student, Course course) {
        if (!course.getPendingStudents().contains(student)) {
            System.out.println("No pending registration for " + student.getName());
            return;
        }

        course.removePendingStudent(student);
        student.removePendingCourse(course);
        Logger.log(getLogin() + " rejected registration: " + student.getLogin() + " -> " + course.getName());
        Database.saveDatabase();
        System.out.println("Rejected: " + student.getName());
    }

    public void assignTeacherToCourse(Teacher teacher, Course course) {
        course.addTeacher(teacher);
        teacher.addCourse(course);
        Logger.log(getLogin() + " assigned teacher " + teacher.getLogin() + " to course " + course.getName());
        Database.saveDatabase();
    }

    private boolean isEligibleForCourse(Student student, Course course) {
        boolean majorMatches = course.getMajor() == null
                || course.getMajor().isBlank()
                || student.getMajor() == null
                || course.getMajor().equalsIgnoreCase(student.getMajor());

        boolean yearMatches = course.getYearOfStudy() == 0
                || student.getYearOfStudy() == course.getYearOfStudy();

        return majorMatches && yearMatches;
    }

    public void signRequest(Request request) {

        if (managerType != ManagerType.DEAN &&
                managerType != ManagerType.RECTOR) {
            throw new IllegalArgumentException("Only DEAN or RECTOR can sign requests");
        }

        request.sign(this);
        signedRequests.add(request);
        pendingRequests.remove(request);
        Logger.log(getLogin() + " signed request from " + request.getSender().getLogin());
        Database.saveDatabase();

        System.out.println("Request signed by " + getName());
    }

    public void submitRequest(Request request) {
        pendingRequests.add(request);
        Logger.log(request.getSender().getLogin() + " submitted request to " + getLogin());
        Database.saveDatabase();
    }

    public void viewRequests() {
        System.out.println("=== Pending Requests ===");
        if (pendingRequests.isEmpty()) {
            System.out.println("No pending requests");
            return;
        }
        for (Request request : pendingRequests) {
            System.out.println(request);
        }
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
        List<Course> courses = Database.getInstance().getCourses();

        System.out.println("===== UNIVERSITY STATISTICAL REPORT =====");

        double avgGPA = students.stream()
                .mapToDouble(Student::getGPA)
                .average()
                .orElse(0);

        System.out.println("Average GPA: " + String.format("%.2f", avgGPA));
        System.out.println("Total students: " + students.size());
        System.out.println("Total courses: " + courses.size());

        Student best = students.stream()
                .max(Comparator.comparingDouble(Student::getGPA))
                .orElse(null);

        Student worst = students.stream()
                .min(Comparator.comparingDouble(Student::getGPA))
                .orElse(null);

        System.out.println("Best student: " + best);
        System.out.println("Worst student: " + worst);

        long passed = students.stream()
                .filter(student -> student.getMarks().values().stream().allMatch(Mark::isPassed))
                .count();
        long failed = students.stream()
                .filter(student -> student.getMarks().values().stream().anyMatch(mark -> !mark.isPassed()))
                .count();

        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        if (!students.isEmpty()) {
            System.out.println("Pass rate: " + String.format("%.2f%%", passed * 100.0 / students.size()));
            System.out.println("Fail rate: " + String.format("%.2f%%", failed * 100.0 / students.size()));
        }

        System.out.println("--- Average mark by course ---");
        for (Course c : courses) {
            System.out.println(c.getName() +
                    " avg mark: " +
                    String.format("%.2f", c.getAverageMark()) +
                    " | pass=" + c.getPassedStudentsCount() +
                    " fail=" + c.getFailedStudentsCount());
        }
    }

    public void viewStudentsInfo(Comparator<Student> comparator) {
        Database.getInstance().getAllUsers().stream()
                .filter(Student.class::isInstance)
                .map(Student.class::cast)
                .sorted(comparator)
                .forEach(student -> System.out.println(student.getName()
                        + " | GPA: " + String.format("%.2f", student.getGPA())
                        + " | Credits: " + student.getTotalCredits()
                        + " | Major: " + student.getMajor()
                        + " | Year: " + student.getYearOfStudy()));
    }

    public void viewStudentsByGPA() {
        viewStudentsInfo(Comparator.comparingDouble(Student::getGPA).reversed());
    }

    public void viewStudentsAlphabetically() {
        viewStudentsInfo(Comparator.comparing(User::getName));
    }

    public void viewStudentsByCredits() {
        viewStudentsInfo(Comparator.comparing(Student::getTotalCredits).reversed());
    }

    public void viewTeachersInfo(Comparator<Teacher> comparator) {
        Database.getInstance().getAllUsers().stream()
                .filter(Teacher.class::isInstance)
                .map(Teacher.class::cast)
                .sorted(comparator)
                .forEach(teacher -> System.out.println(teacher.getName()
                        + " | Title: " + teacher.getTitle()
                        + " | Rating: " + String.format("%.2f", teacher.getAverageRating())
                        + " | Courses: " + teacher.getCourses().size()));
    }

    public void viewTeachersByRating() {
        viewTeachersInfo(Comparator.comparingDouble(Teacher::getAverageRating).reversed()
                .thenComparing(Teacher::getName));
    }

    public void viewTeachersByTitle() {
        viewTeachersInfo(Comparator.comparing(Teacher::getTitle)
                .thenComparing(Teacher::getName));
    }

    public void viewTeachersAlphabetically() {
        viewTeachersInfo(Comparator.comparing(Teacher::getName));
    }

    public List<Request> getPendingRequests() {
        return Collections.unmodifiableList(pendingRequests);
    }

    public ManagerType getManagerType() {
        return managerType;
    }

    public void setManagerType(ManagerType managerType) {
        this.managerType = managerType;
    }
}
