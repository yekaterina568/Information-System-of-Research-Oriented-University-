package src.university;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        try {
            Database db = Database.getInstance();
            db.getAllUsers().clear();
            db.getCourses().clear();
            Database.saveDatabase();

            Admin admin = new Admin("a1", "Main Admin", "admin", "123", "admin@kbtu.kz");
            Student student = new Student("s1", "Alice", "alice", "123", "alice@kbtu.kz", 4);
            student.setMajor("IS");

            Teacher teacher = new Teacher(
                    "t1", "Bob Teacher", "bob", "123", "bob@kbtu.kz",
                    300000, TeacherTitle.LECTOR
            );

            Professor professor = new Professor(
                    "p1", "Dr. Smith", "smith", "123", "smith@kbtu.kz",
                    500000, School.FIT
            );

            ResearchEmployee researchEmployee = new ResearchEmployee(
                    "re1", "Dana Researcher", "dana", "123", "dana@kbtu.kz",
                    350000, School.BS
            );

            Manager manager = new Manager(
                    "m1", "Dean John", "john", "123", "john@kbtu.kz",
                    450000, ManagerType.DEAN
            );

            admin.addUser(admin);
            admin.addUser(student);
            admin.addUser(teacher);
            admin.addUser(professor);
            admin.addUser(researchEmployee);
            admin.addUser(manager);

            Course oop = new Course("OOP", 6, "IS", 2);
            Course dbCourse = new Course("Databases", 5, "IS", 2);

            db.getCourses().add(oop);
            db.getCourses().add(dbCourse);

            teacher.addCourse(oop);
            oop.addTeacher(teacher);

            manager.approveRegistration(student, oop);

            Mark mark = new Mark(25, 20, 40);
            teacher.putMark(student, oop, mark);

            System.out.println("Student GPA: " + student.getGPA());
            System.out.println("Student mark in OOP: " + student.getMark(oop).getTotal());

            ResearchPaper p1 = new ResearchPaper(
                    "AI in Education",
                    List.of("Dr. Smith"),
                    12,
                    LocalDate.of(2024, 5, 10),
                    25,
                    "IEEE Access",
                    "References",
                    "10.1000/ai-edu"
            );

            ResearchPaper p2 = new ResearchPaper(
                    "OOP for University Systems",
                    List.of("Dr. Smith"),
                    8,
                    LocalDate.of(2023, 3, 1),
                    10,
                    "Scopus Journal",
                    "References",
                    "10.1000/oop-uni"
            );

            ResearchPaper p3 = new ResearchPaper(
                    "Data Science in Universities",
                    List.of("Dr. Smith"),
                    15,
                    LocalDate.of(2022, 11, 20),
                    7,
                    "Springer Journal",
                    "References",
                    "10.1000/data-uni"
            );

            professor.addPaper(p1);
            professor.addPaper(p2);
            professor.addPaper(p3);

            System.out.println("Professor H-index: " + professor.getHIndex());
            professor.printPapers(Researcher.byCitations());

            student.assignResearchSupervisor(professor);
            student.printSupervisorInfo();
            professor.addPaper(p1);
            professor.addPaper(p2);

            System.out.println("Professor H-index: " + professor.getHIndex());
            professor.printPapers(Researcher.byCitations());

            student.assignResearchSupervisor(professor);
            student.printSupervisorInfo();

            ResearchProject project = new ResearchProject(
                    "rp1",
                    "Smart Campus",
                    LocalDate.of(2024, 1, 1),
                    LocalDate.of(2026, 1, 1),
                    ResearchProjectStatus.ACTIVE
            );

            project.addParticipant(professor);
            project.addParticipant(researchEmployee);
            project.addPaper(p1);

            System.out.println(project);

            ResearcherDecorator studentResearcher = UserFactory.makeResearcher(student, School.FIT);
            ResearchPaper studentPaper = new ResearchPaper(
                    "Student Research",
                    List.of("Alice"),
                    6,
                    LocalDate.of(2025, 2, 15),
                    4,
                    "Student Journal",
                    "Refs",
                    "10.1000/student-oop"
            );
            studentResearcher.addPaper(studentPaper);
            project.addParticipant(studentResearcher);

            NotificationService notificationService = new NotificationService();
            notificationService.send(student, new Notification(
                    NotificationType.MESSAGE,
                    "Your registration was approved"
            ));
            notificationService.printUserNotifications(student);

            Request request = new Request("Need approval for conference trip", teacher);
            manager.signRequest(request);
            manager.viewSignedRequests();

            Room room = new Room("A401", RoomType.LECTURE_HALL, 100);
            TimeSlot slot = new TimeSlot(
                    DayOfWeek.MONDAY,
                    LocalTime.of(10, 0),
                    LocalTime.of(11, 0)
            );

            ScheduleGenerator generator = new ScheduleGenerator();
            boolean added = generator.addLesson(oop, teacher, room, slot, Lesson.LECTURE);
            System.out.println("Lesson added: " + added);
            generator.printSchedule();

            System.out.println("Search users by 'ali':");
            for (User u : SearchService.searchUserByRegex(db.getAllUsers(), "ali")) {
                System.out.println(u);
            }

            System.out.println("Search courses by 'OOP':");
            for (Course c : SearchService.searchCoursesByRegex(db.getCourses(), "OOP")) {
                System.out.println(c.getName());
            }

            System.out.println("Top cited researcher: " +
                    ResearchService.getTopCitedResearcher(db.getAllUsers()));

            admin.updateUser(student, "Alice Updated", "alice2", "456", "alice2@kbtu.kz");
            admin.viewLogs();

            admin.removeUser(researchEmployee);

            Database.saveDatabase();
            System.out.println("=== TEST FINISHED SUCCESSFULLY ===");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
