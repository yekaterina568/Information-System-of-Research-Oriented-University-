package src.university;

import java.io.Serializable;
import java.util.*;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private static final int MAX_CREDITS = 21;
    private static final int MAX_FAILURES = 3;

    private int yearOfStudy;
    private String major;
    private DegreeType degreeType = DegreeType.BACHELOR;
    private StudentStatus status = StudentStatus.ACTIVE;
    private Researcher researchSupervisor;

    private int totalCredits = 0;
    private int failedCourses = 0;

    private Map<Course, Mark> marks = new HashMap<>();
    private List<Course> enrolledCourses = new ArrayList<>();
    private List<Course> pendingCourses = new ArrayList<>();

    public Student(String id, String name, String login,
            String password, String email) {
        this(id, name, login, password, email, 1);
    }

    public Student(String id, String name, String login,
            String password, String email, int yearOfStudy) {
        super(id, name, login, password, email);
        this.yearOfStudy = yearOfStudy;
    }

    public void requestCourseRegistration(Course course) throws MaxCreditsException {
        if (enrolledCourses.contains(course)) {
            System.out.println("Already enrolled in " + course.getName());
            return;
        }
        if (pendingCourses.contains(course)) {
            System.out.println("Already requested " + course.getName());
            return;
        }
        if (totalCredits + course.getCredits() > MAX_CREDITS) {
            Logger.log("WARN: " + login + " exceeded credit limit.");
            throw new MaxCreditsException(
                    "Credit limit exceeded (" + MAX_CREDITS + ") for " + name);
        }
        if (course.getYearOfStudy() != 0 && course.getYearOfStudy() != yearOfStudy) {
            System.out.println("This course is for year " + course.getYearOfStudy() +
                    ", you are year " + yearOfStudy);
            return;
        }
        if (course.getMajor() != null && major != null &&
                !course.getMajor().equalsIgnoreCase(major)) {
            System.out.println("Major mismatch: course is for " + course.getMajor());
            return;
        }
        pendingCourses.add(course);
        course.addPendingStudent(this);
        Logger.log(login + " requested registration for: " + course.getName());
        System.out.println("Registration request sent for: " + course.getName());
    }

    public void enrollCourse(Course course) throws MaxCreditsException {
        if (totalCredits + course.getCredits() > MAX_CREDITS) {
            throw new MaxCreditsException("Credit limit exceeded for " + name);
        }
        pendingCourses.remove(course);
        if (!enrolledCourses.contains(course)) {
            enrolledCourses.add(course);
            course.addStudent(this);
            totalCredits += course.getCredits();
            Logger.log(login + " enrolled in: " + course.getName());
        }
    }

    public void addCourse(Course course) {
        if (!enrolledCourses.contains(course))
            enrolledCourses.add(course);
    }

    public void addMark(Course course, Mark mark) {
        setMark(course, mark);
    }

    public void setMark(Course course, Mark mark) {
        marks.put(course, mark);
        Logger.log("Mark assigned to " + login + " for " + course.getName() +
                ": " + mark.getTotal());
        if (!mark.isPassed()) {
            failedCourses++;
            Logger.log("WARN: " + login + " failed " + course.getName() +
                    ". Failures: " + failedCourses);
            if (failedCourses > MAX_FAILURES) {
                status = StudentStatus.DISMISSED;
                Logger.log("CRITICAL: " + login + " DISMISSED (>3 failures).");
                System.out.println("[!] Student " + name + " has been DISMISSED.");
            }
        }
    }

    public Mark getMark(Course course) {
        return marks.get(course);
    }

    public Map<Course, Mark> getMarks() {
        return Collections.unmodifiableMap(marks);
    }

    public double getGPA() {
        if (marks.isEmpty())
            return 0.0;
        double totalPoints = 0.0;
        int totalCred = 0;
        for (Map.Entry<Course, Mark> e : marks.entrySet()) {
            int cr = e.getKey().getCredits();
            totalPoints += e.getValue().getGradePoint() * cr;
            totalCred += cr;
        }
        return totalCred == 0 ? 0.0 : totalPoints / totalCred;
    }

    public Transcript getTranscript() {
        return new Transcript(this);
    }

    public void printTranscript() {
        getTranscript().print();
    }

    public void assignResearchSupervisor(Researcher supervisor) throws HIndexException {
        if (yearOfStudy != 4) {
            throw new HIndexException("Only 4th year students can have a research supervisor.");
        }
        if (supervisor == null) {
            throw new HIndexException("Supervisor cannot be null.");
        }
        if (supervisor.getHIndex() < 3) {
            throw new HIndexException(
                    "Supervisor h-index (" + supervisor.getHIndex() + ") is below 3. Required: >= 3.");
        }
        this.researchSupervisor = supervisor;
        Logger.log(login + " assigned supervisor: " + supervisor);
        System.out.println("Supervisor assigned for " + name);
    }

    public void reassignResearchSupervisor(Researcher newSupervisor) throws HIndexException {
        assignResearchSupervisor(newSupervisor);
    }

    public void printSupervisorInfo() {
        if (researchSupervisor == null) {
            System.out.println(name + " has no research supervisor assigned.");
            return;
        }
        System.out.println("Supervisor of " + name + ": " + researchSupervisor +
                " | H-index: " + researchSupervisor.getHIndex());
    }

    public void rateTeacher(Teacher teacher, int rating) {
        teacher.receiveRating(rating);
        Logger.log(login + " rated teacher " + teacher.getName() + " -> " + rating);
    }

    public void viewMarks() {
        System.out.println("=== Marks for " + name + " ===");
        if (marks.isEmpty()) {
            System.out.println("  No marks yet.");
            return;
        }
        marks.forEach((c, m) -> System.out.printf("  %-25s %s%n", c.getName(), m));
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }

    public void setYearOfStudy(int y) {
        this.yearOfStudy = y;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public DegreeType getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(DegreeType dt) {
        this.degreeType = dt;
    }

    public StudentStatus getStatus() {
        return status;
    }

    public void setStatus(StudentStatus s) {
        this.status = s;
    }

    public Researcher getResearchSupervisor() {
        return researchSupervisor;
    }

    public int getTotalCredits() {
        return totalCredits;
    }

    public void setTotalCredits(int c) {
        this.totalCredits = c;
    }

    public int getFailedCourses() {
        return failedCourses;
    }

    public List<Course> getEnrolledCourses() {
        return Collections.unmodifiableList(enrolledCourses);
    }

    public List<Course> getPendingCourses() {
        return Collections.unmodifiableList(pendingCourses);
    }

    public boolean isDismissed() {
        return status == StudentStatus.DISMISSED;
    }

    @Override
    public String toString() {
        return "Student{name='" + name + "', year=" + yearOfStudy +
                ", major='" + major + "', GPA=" + String.format("%.2f", getGPA()) +
                ", status=" + status + "}";
    }
}
