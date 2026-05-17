package university;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Map;

public class Transcript implements Serializable {
    private static final long serialVersionUID = 1L;

    private final Student student;
    private final LocalDate generatedDate;

    public Transcript(Student student) {
        this.student       = student;
        this.generatedDate = LocalDate.now();
    }

    public double calculateGPA() {
        Map<Course, Mark> marks = student.getMarks();
        if (marks.isEmpty()) return 0.0;

        double totalPoints  = 0.0;
        int    totalCredits = 0;

        for (Map.Entry<Course, Mark> entry : marks.entrySet()) {
            int credits = entry.getKey().getCredits();
            totalPoints  += entry.getValue().getGradePoint() * credits;
            totalCredits += credits;
        }
        return totalCredits == 0 ? 0.0 : totalPoints / totalCredits;
    }

    public void print() {
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║           OFFICIAL TRANSCRIPT            ║");
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║ Student : %-32s║%n", student.getName());
        System.out.printf( "║ Major   : %-32s║%n", student.getMajor() != null ? student.getMajor() : "N/A");
        System.out.printf( "║ Year    : %-32s║%n", student.getYearOfStudy());
        System.out.printf( "║ Status  : %-32s║%n", student.getStatus());
        System.out.printf( "║ Date    : %-32s║%n", generatedDate);
        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║ %-20s %-6s %-5s %-5s║%n",
                "Course", "Credits", "Grade", "GP");
        System.out.println("╠══════════════════════════════════════════╣");

        Map<Course, Mark> marks = student.getMarks();
        if (marks.isEmpty()) {
            System.out.println("║  No grades recorded yet.               ║");
        } else {
            for (Map.Entry<Course, Mark> e : marks.entrySet()) {
                System.out.printf("║ %-20s %-6d %-5s %-4.2f║%n",
                        truncate(e.getKey().getName(), 20),
                        e.getKey().getCredits(),
                        e.getValue().getLetterGrade(),
                        e.getValue().getGradePoint());
            }
        }

        System.out.println("╠══════════════════════════════════════════╣");
        System.out.printf( "║ GPA: %-36s║%n", String.format("%.2f / 4.00", calculateGPA()));
        System.out.printf( "║ Total Credits: %-26s║%n", student.getTotalCredits());
        System.out.println("╚══════════════════════════════════════════╝");
    }

    private String truncate(String s, int max) {
        return s.length() <= max ? s : s.substring(0, max - 2) + "..";
    }

    public Student getStudent()       { return student; }
    public LocalDate getGeneratedDate() { return generatedDate; }
    public double getGPA()            { return calculateGPA(); }
}

