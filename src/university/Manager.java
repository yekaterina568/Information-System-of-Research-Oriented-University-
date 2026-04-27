package university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Manager extends Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private ManagerType managerType;
    private List<String> pendingRequests;
    private List<String> news;

    public Manager(String login, String password, String firstName, String lastName, String employeeId, double salary, ManagerType managerType) {
        super(login, password, firstName, lastName, employeeId, salary);
        this.managerType = managerType;
        this.pendingRequests = new ArrayList<>();
        this.news = new ArrayList<>();
    }

    public void approveRegistration(Student student, Course course) {
        if (!course.getPendingStudents().contains(student)) {
            System.out.println("No pending request from " + student.getFirstName() + " for " + course.getCourseName());
            return;
        }
        course.getPendingStudents().remove(student);
        course.getStudents().add(student);
        student.addCourse(course);
        student.update("Your registration for " + course.getCourseName() + " was approved!");
        System.out.println("Approved: " + student.getFirstName() + " → " + course.getCourseName());
    }

    public void addCourseForRegistration(Course course, String major, int year) {
        Database.getInstance().getCourses().add(course);
        System.out.println("Course opened: " + course.getCourseName() + " | Major: " + major + " | Year " + year);
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        course.addTeacher(teacher);
        teacher.addCourse(course);
        teacher.update("You have been assigned to: " + course.getCourseName());
        System.out.println("Assigned " + teacher.getTitle() + " " + teacher.getLastName() + " → " + course.getCourseName());
    }

    public void createStatisticalReport() {
        List<Student> students = Database.getInstance().getStudents();
        System.out.println("========== Academic Report ==========");
        System.out.println("Total students: " + students.size());
        if (students.isEmpty()) return;
        double avgGPA = students.stream().mapToDouble(Student::getGPA).average().orElse(0.0);
        System.out.println("Average GPA: " + String.format("%.2f", avgGPA));
        List<Student> byGPA = new ArrayList<>(students);
        byGPA.sort((a, b) -> Double.compare(b.getGPA(), a.getGPA()));
        System.out.println("\nTop 5 by GPA:");
        byGPA.stream().limit(5).forEach(s -> System.out.println("  " + s.getFirstName() + " " + s.getLastName() + " — GPA: " + String.format("%.2f", s.getGPA())));
        System.out.println("=====================================");
    }

    public void viewStudentsInfo(Comparator<Student> comparator) {
        List<Student> students = new ArrayList<>(Database.getInstance().getStudents());
        students.sort(comparator);
        System.out.println("=== Students ===");
        for (Student s : students) {
            System.out.println("  " + s.getFirstName() + " " + s.getLastName() + " | GPA: " + String.format("%.2f", s.getGPA()) + " | Credits: " + s.getTotalCredits());
        }
    }

    public void viewTeachersInfo(Comparator<Teacher> comparator) {
        List<Teacher> teachers = new ArrayList<>(Database.getInstance().getTeachers());
        teachers.sort(comparator);
        System.out.println("=== Teachers ===");
        for (Teacher t : teachers) {
            System.out.println("  " + t.getTitle() + " " + t.getFirstName() + " " + t.getLastName() + " | Rating: " + String.format("%.1f", t.getAverageRating()));
        }
    }

    public void viewRequests() {
        System.out.println("=== Pending Requests ===");
        if (pendingRequests.isEmpty()) { System.out.println("  None."); return; }
        for (int i = 0; i < pendingRequests.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + pendingRequests.get(i));
        }
    }
    public void receiveRequest(String request) { pendingRequests.add(request); }

    public void addNews(String item) {
        news.add(item);
        System.out.println("News posted: " + item);
    }

    public void viewNews() {
        System.out.println("=== News ===");
        if (news.isEmpty()) { System.out.println("  No news."); return; }
        for (int i = 0; i < news.size(); i++) {
            System.out.println("  " + (i + 1) + ". " + news.get(i));
        }
    }

    public ManagerType getManagerType() { return managerType; }
    public void setManagerType(ManagerType type) { this.managerType = type; }
    public List<String> getNews() { return news; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;
        Manager m = (Manager) o;
        return Objects.equals(getEmployeeId(), m.getEmployeeId());
    }

    @Override
    public int hashCode() { return Objects.hash(getEmployeeId()); }

    @Override
    public String toString() { return managerType + " Manager: " + getFirstName() + " " + getLastName(); }
}