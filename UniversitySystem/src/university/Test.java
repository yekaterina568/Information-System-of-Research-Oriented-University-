package university;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    private static final Scanner sc = new Scanner(System.in);
    private static final AuthService auth = AuthService.getInstance();
    private static final NotificationService notifSvc = new NotificationService();
    private static final MessageService msgSvc = MessageService.getInstance();
    private static final ResearchService resSvc = new ResearchService();

    public static void main(String[] args) {
        Database.loadDatabase();
        seedIfEmpty();

        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   KBTU University Information System ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            if (!auth.isLoggedIn()) {
                showLoginMenu();
            } else {
                User u = auth.getCurrentUser();
                if (u instanceof Admin)
                    adminMenu((Admin) u);
                else if (u instanceof Manager)
                    managerMenu((Manager) u);
                else if (u instanceof Professor)
                    professorMenu((Professor) u);
                else if (u instanceof Teacher)
                    teacherMenu((Teacher) u);
                else if (u instanceof Student)
                    studentMenu((Student) u);
                else if (u instanceof ResearchEmployee)
                    researchEmployeeMenu((ResearchEmployee) u);
                else {
                    System.out.println("Unknown role.");
                    auth.logout();
                }
            }
        }
    }

    private static void showLoginMenu() {
        System.out.println("\n1. Login\n2. Exit");
        int ch = readInt();
        switch (ch) {
            case 1 -> {
                System.out.print("Login: ");
                String login = sc.nextLine().trim();
                System.out.print("Password: ");
                String pass = sc.nextLine().trim();
                User user = auth.login(login, pass);
                if (user == null)
                    System.out.println("Invalid credentials.");
            }
            case 2 -> {
                System.out.println("Bye!");
                Database.saveDatabase();
                System.exit(0);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    private static void adminMenu(Admin admin) {
        System.out.println("\n=== ADMIN MENU ===");
        System.out.println("1. Add user\n2. Remove user\n3. Update user\n4. View all users\n5. View logs\n6. Logout");
        switch (readInt()) {
            case 1 -> adminAddUser(admin);
            case 2 -> adminRemoveUser(admin);
            case 3 -> adminUpdateUser(admin);
            case 4 -> {
                System.out.println("=== All Users ===");
                Database.getInstance().getAllUsers().forEach(u -> System.out.println("  " + u));
            }
            case 5 -> admin.viewLogs();
            case 6 -> auth.logout();
            default -> System.out.println("Invalid.");
        }
    }

    private static void adminAddUser(Admin admin) {
        System.out.println("Role: 1=Student 2=Teacher 3=Professor 4=Manager 5=ResearchEmployee 6=Admin");
        int role = readInt();
        System.out.print("ID: ");
        String id = sc.nextLine().trim();
        System.out.print("Name: ");
        String name = sc.nextLine().trim();
        System.out.print("Login: ");
        String login = sc.nextLine().trim();
        System.out.print("Password:");
        String pass = sc.nextLine().trim();
        System.out.print("Email: ");
        String email = sc.nextLine().trim();

        try {
            switch (role) {
                case 1 -> {
                    System.out.print("Major: ");
                    String major = sc.nextLine().trim();
                    System.out.print("Year (1-4): ");
                    int year = readInt();
                    Student s = new Student(id, name, login, pass, email, year);
                    s.setMajor(major);
                    admin.addUser(s);
                }
                case 2 -> {
                    System.out.println("Title: 1=TUTOR 2=LECTOR 3=SENIOR_LECTOR 4=PROFESSOR");
                    TeacherTitle[] titles = TeacherTitle.values();
                    int ti = Math.max(0, Math.min(readInt() - 1, titles.length - 1));
                    Teacher t = new Teacher(id, name, login, pass, email, 0, titles[ti]);
                    admin.addUser(t);
                }
                case 3 -> {
                    System.out.println("School: " + Arrays.toString(School.values()));
                    System.out.print("School: ");
                    School sch = School.valueOf(sc.nextLine().trim().toUpperCase());
                    admin.addUser(new Professor(id, name, login, pass, email, 0, sch));
                }
                case 4 -> {
                    System.out.println("Type: " + Arrays.toString(ManagerType.values()));
                    System.out.print("Type: ");
                    ManagerType mt = ManagerType.valueOf(sc.nextLine().trim().toUpperCase());
                    admin.addUser(new Manager(id, name, login, pass, email, 0, mt));
                }
                case 5 -> {
                    System.out.println("School: " + Arrays.toString(School.values()));
                    System.out.print("School: ");
                    School sch = School.valueOf(sc.nextLine().trim().toUpperCase());
                    admin.addUser(new ResearchEmployee(id, name, login, pass, email, 0, sch));
                }
                case 6 -> admin.addUser(new Admin(id, name, login, pass, email));
                default -> System.out.println("Invalid role.");
            }
            System.out.println("User added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void adminRemoveUser(Admin admin) {
        System.out.print("User login to remove: ");
        String login = sc.nextLine().trim();
        User u = Database.getInstance().findByLogin(login);
        if (u == null) {
            System.out.println("Not found.");
            return;
        }
        admin.removeUser(u);
        System.out.println("Removed.");
    }

    private static void adminUpdateUser(Admin admin) {
        System.out.print("User login to update: ");
        String login = sc.nextLine().trim();
        User u = Database.getInstance().findByLogin(login);
        if (u == null) {
            System.out.println("Not found.");
            return;
        }
        System.out.print("New name (blank=keep): ");
        String name = sc.nextLine().trim();
        System.out.print("New login (blank=keep): ");
        String newLogin = sc.nextLine().trim();
        System.out.print("New pass (blank=keep): ");
        String pass = sc.nextLine().trim();
        System.out.print("New email (blank=keep): ");
        String email = sc.nextLine().trim();
        admin.updateUser(u,
                name.isEmpty() ? u.getName() : name,
                newLogin.isEmpty() ? u.getLogin() : newLogin,
                pass.isEmpty() ? u.getPassword() : pass,
                email.isEmpty() ? u.getEmail() : email);
        System.out.println("Updated.");
    }

    private static void studentMenu(Student student) {
        System.out.println("\n=== STUDENT MENU | " + student.getName() + " ===");
        System.out.println("1. View available courses\n2. Request course registration" +
                "\n3. View my courses\n4. View marks\n5. Get transcript" +
                "\n6. Rate teacher\n7. Send message\n8. View inbox" +
                "\n9. View supervisor info\n10. Logout");
        switch (readInt()) {
            case 1 -> Database.getInstance().getCourses()
                    .forEach(c -> System.out.printf("  %-5s %-25s credits=%-3d major=%-10s year=%d%n",
                            c.getName(), c.getName(), c.getCredits(), c.getMajor(), c.getYearOfStudy()));
            case 2 -> studentRequestCourse(student);
            case 3 -> {
                System.out.println("=== My Courses ===");
                student.getEnrolledCourses()
                        .forEach(c -> System.out.println("  " + c.getName() + " [" + c.getCredits() + " cr]"));
                if (!student.getPendingCourses().isEmpty()) {
                    System.out.println("--- Pending approval ---");
                    student.getPendingCourses().forEach(c -> System.out.println("  " + c.getName()));
                }
            }
            case 4 -> student.viewMarks();
            case 5 -> student.printTranscript();
            case 6 -> studentRateTeacher(student);
            case 7 -> sendMessageMenu(student);
            case 8 -> msgSvc.printInbox(student);
            case 9 -> student.printSupervisorInfo();
            case 10 -> auth.logout();
            default -> System.out.println("Invalid.");
        }
    }

    private static void studentRequestCourse(Student student) {
        List<Course> courses = Database.getInstance().getCourses();
        for (int i = 0; i < courses.size(); i++)
            System.out.println((i + 1) + ". " + courses.get(i).getName() +
                    " [" + courses.get(i).getCredits() + " cr]");
        System.out.print("Choose course #: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size()) {
            System.out.println("Invalid.");
            return;
        }
        try {
            student.requestCourseRegistration(courses.get(idx));
        } catch (MaxCreditsException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void studentRateTeacher(Student student) {
        List<Course> enrolled = new ArrayList<>(student.getEnrolledCourses());
        if (enrolled.isEmpty()) {
            System.out.println("No enrolled courses.");
            return;
        }
        for (int i = 0; i < enrolled.size(); i++)
            System.out.println((i + 1) + ". " + enrolled.get(i).getName());
        System.out.print("Choose course #: ");
        int ci = readInt() - 1;
        if (ci < 0 || ci >= enrolled.size())
            return;
        Course course = enrolled.get(ci);
        if (course.getTeachers().isEmpty()) {
            System.out.println("No teachers assigned.");
            return;
        }
        Teacher teacher = course.getTeachers().get(0);
        System.out.print("Rating for " + teacher.getName() + " (1-10): ");
        int r = readInt();
        student.rateTeacher(teacher, r);
        System.out.println("Rated.");
    }

    private static void teacherMenu(Teacher teacher) {
        System.out.println("\n=== TEACHER MENU | " + teacher.getName() +
                " [" + teacher.getTitle() + "] ===");
        System.out.println("1. View my courses\n2. View students in course\n3. Put mark" +
                "\n4. Generate course report\n5. Send message\n6. View inbox" +
                "\n7. Send complaint\n8. Logout");
        switch (readInt()) {
            case 1 -> teacher.viewCourses();
            case 2 -> {
                Course c = pickCourse(teacher.getCourses());
                if (c != null)
                    teacher.viewStudents(c);
            }
            case 3 -> teacherPutMark(teacher);
            case 4 -> {
                Course c = pickCourse(teacher.getCourses());
                if (c != null)
                    System.out.println(c.generateReport());
            }
            case 5 -> sendMessageMenu(teacher);
            case 6 -> msgSvc.printInbox(teacher);
            case 7 -> {
                System.out.print("Complaint text: ");
                String text = sc.nextLine().trim();
                teacher.sendComplaint(text);
            }
            case 8 -> auth.logout();
            default -> System.out.println("Invalid.");
        }
    }

    private static void teacherPutMark(Teacher teacher) {
        Course c = pickCourse(teacher.getCourses());
        if (c == null)
            return;
        List<Student> students = new ArrayList<>(c.getStudents());
        if (students.isEmpty()) {
            System.out.println("No students.");
            return;
        }
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).getName());
        System.out.print("Choose student #: ");
        int si = readInt() - 1;
        if (si < 0 || si >= students.size())
            return;
        Student s = students.get(si);
        try {
            System.out.print("ATT1 (0-30): ");
            double a1 = Double.parseDouble(sc.nextLine().trim());
            System.out.print("ATT2 (0-30): ");
            double a2 = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Final (0-40): ");
            double fn = Double.parseDouble(sc.nextLine().trim());
            teacher.putMark(s, c, new Mark(a1, a2, fn));
            System.out.println("Mark saved.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void professorMenu(Professor professor) {
        System.out.println("\n=== PROFESSOR MENU | " + professor.getName() + " ===");
        System.out.println("1. Teacher functions\n2. View my papers\n3. Add paper" +
                "\n4. Print papers by citations\n5. Print papers by date" +
                "\n6. Print papers by pages\n7. View H-index\n8. View research projects\n9. Logout");
        switch (readInt()) {
            case 1 -> teacherMenu(professor);
            case 2 -> {
                System.out.println("=== Papers ===");
                professor.getPapers().forEach(System.out::println);
            }
            case 3 -> addPaperMenu(professor);
            case 4 -> professor.printPapers(Researcher.byCitations());
            case 5 -> professor.printPapers(Researcher.byDate());
            case 6 -> professor.printPapers(Researcher.byPages());
            case 7 -> System.out.println("H-index: " + professor.getHIndex());
            case 8 -> professor.getProjects().forEach(System.out::println);
            case 9 -> auth.logout();
            default -> System.out.println("Invalid.");
        }
    }

    private static void managerMenu(Manager manager) {
        System.out.println("\n=== MANAGER MENU | " + manager.getName() +
                " [" + manager.getManagerType() + "] ===");
        System.out.println(
                "1.  View pending registrations\n2.  Approve/Reject registration" +
                        "\n3.  Add course\n4.  Assign teacher to course" +
                        "\n5.  Course report\n6.  Statistical report" +
                        "\n7.  Students by GPA\n8.  Students alphabetically" +
                        "\n9.  Students by credits\n10. View teachers" +
                        "\n11. Manage news\n12. View signed requests" +
                        "\n13. Send message\n14. View inbox\n15. Logout");
        switch (readInt()) {
            case 1 -> managerViewPending(manager);
            case 2 -> managerApproveReject(manager);
            case 3 -> managerAddCourse(manager);
            case 4 -> managerAssignTeacher(manager);
            case 5 -> managerCourseReport();
            case 6 -> manager.createStatisticalReport();
            case 7 -> manager.viewStudentsByGPA();
            case 8 -> manager.viewStudentsAlphabetically();
            case 9 -> manager.viewStudentsByCredits();
            case 10 -> Database.getInstance().getAllUsers().stream()
                    .filter(u -> u instanceof Teacher)
                    .forEach(System.out::println);
            case 11 -> managerNewsMenu(manager);
            case 12 -> manager.viewSignedRequests();
            case 13 -> sendMessageMenu(manager);
            case 14 -> msgSvc.printInbox(manager);
            case 15 -> auth.logout();
            default -> System.out.println("Invalid.");
        }
    }

    private static void managerViewPending(Manager manager) {
        System.out.println("=== Pending Registrations ===");
        boolean any = false;
        for (Course c : Database.getInstance().getCourses()) {
            List<Student> pending = new ArrayList<>(c.getPendingStudents());
            if (!pending.isEmpty()) {
                any = true;
                System.out.println("Course: " + c.getName());
                pending.forEach(s -> System.out.println("  - " + s.getName() +
                        " | Year: " + s.getYearOfStudy() + " | Major: " + s.getMajor()));
            }
        }
        if (!any)
            System.out.println("  No pending requests.");
    }

    private static void managerApproveReject(Manager manager) {
        List<Course> withPending = Database.getInstance().getCourses().stream()
                .filter(c -> !c.getPendingStudents().isEmpty())
                .collect(Collectors.toList());
        if (withPending.isEmpty()) {
            System.out.println("No pending requests.");
            return;
        }

        for (int i = 0; i < withPending.size(); i++)
            System.out.println((i + 1) + ". " + withPending.get(i).getName());
        System.out.print("Choose course #: ");
        int ci = readInt() - 1;
        if (ci < 0 || ci >= withPending.size())
            return;
        Course course = withPending.get(ci);

        List<Student> pending = new ArrayList<>(course.getPendingStudents());
        for (int i = 0; i < pending.size(); i++)
            System.out.println((i + 1) + ". " + pending.get(i).getName());
        System.out.print("Choose student #: ");
        int si = readInt() - 1;
        if (si < 0 || si >= pending.size())
            return;
        Student student = pending.get(si);

        System.out.print("1=Approve 2=Reject: ");
        int choice = readInt();
        if (choice == 1) {
            manager.approveRegistration(student, course);
            notifSvc.send(student, new Notification(NotificationType.REGISTRATION_APPROVED,
                    "Your registration for " + course.getName() + " was approved."));
        } else {
            manager.rejectRegistration(student, course);
            notifSvc.send(student, new Notification(NotificationType.REGISTRATION_REJECTED,
                    "Your registration for " + course.getName() + " was rejected."));
        }
    }

    private static void managerAddCourse(Manager manager) {
        System.out.print("Course name: ");
        String name = sc.nextLine().trim();
        System.out.print("Credits: ");
        int credits = readInt();
        System.out.print("Major: ");
        String major = sc.nextLine().trim();
        System.out.print("Year (1-4): ");
        int year = readInt();
        Course c = new Course(name, credits, major, year);
        Database.getInstance().getCourses().add(c);
        Logger.log(manager.getLogin() + " added course: " + name);
        System.out.println("Course added: " + name);
    }

    private static void managerAssignTeacher(Manager manager) {
        List<Course> courses = Database.getInstance().getCourses();
        List<User> teachers = Database.getInstance().getAllUsers().stream()
                .filter(u -> u instanceof Teacher).collect(Collectors.toList());
        if (courses.isEmpty() || teachers.isEmpty()) {
            System.out.println("No courses or teachers available.");
            return;
        }
        for (int i = 0; i < courses.size(); i++)
            System.out.println((i + 1) + ". " + courses.get(i).getName());
        System.out.print("Course #: ");
        int ci = readInt() - 1;
        if (ci < 0 || ci >= courses.size())
            return;

        for (int i = 0; i < teachers.size(); i++)
            System.out.println((i + 1) + ". " + teachers.get(i).getName());
        System.out.print("Teacher #: ");
        int ti = readInt() - 1;
        if (ti < 0 || ti >= teachers.size())
            return;

        Teacher t = (Teacher) teachers.get(ti);
        Course c = courses.get(ci);
        manager.assignTeacherToCourse(t, c);
        System.out.println("Assigned " + t.getName() + " to " + c.getName());
    }

    private static void managerCourseReport() {
        Database.getInstance().getCourses().forEach(c -> System.out.println(c.generateReport()));
    }

    private static void managerNewsMenu(Manager manager) {
        System.out.println("1. Add news\n2. View news");
        switch (readInt()) {
            case 1 -> {
                System.out.print("News: ");
                manager.addNews(sc.nextLine().trim());
            }
            case 2 -> manager.viewNews();
        }
    }

    private static void researchEmployeeMenu(ResearchEmployee re) {
        System.out.println("\n=== RESEARCHER MENU | " + re.getName() + " ===");
        System.out.println("1. View my papers\n2. Add paper\n3. Print papers by citations" +
                "\n4. Print papers by date\n5. Print papers by pages" +
                "\n6. My H-index\n7. All university papers (by citations)" +
                "\n8. Top cited researcher\n9. Send message\n10. View inbox\n11. Logout");
        switch (readInt()) {
            case 1 -> re.getPapers().forEach(System.out::println);
            case 2 -> addPaperMenu(re);
            case 3 -> re.printPapers(Researcher.byCitations());
            case 4 -> re.printPapers(Researcher.byDate());
            case 5 -> re.printPapers(Researcher.byPages());
            case 6 -> System.out.println("H-index: " + re.getHIndex());
            case 7 -> ResearchService.printAllUniversityPapers(
                    Database.getInstance().getAllUsers(), Researcher.byCitations());
            case 8 -> System.out.println("Top cited: " +
                    ResearchService.getTopCitedResearcher(Database.getInstance().getAllUsers()));
            case 9 -> sendMessageMenu(re);
            case 10 -> msgSvc.printInbox(re);
            case 11 -> auth.logout();
            default -> System.out.println("Invalid.");
        }
    }

    private static void addPaperMenu(Researcher researcher) {
        try {
            System.out.print("Title: ");
            String title = sc.nextLine().trim();
            System.out.print("Journal: ");
            String journal = sc.nextLine().trim();
            System.out.print("DOI: ");
            String doi = sc.nextLine().trim();
            System.out.print("Pages: ");
            int pages = readInt();
            System.out.print("Citations: ");
            int cit = readInt();
            System.out.print("Year (e.g. 2024): ");
            int year = readInt();
            System.out.print("Month (1-12): ");
            int month = readInt();
            System.out.print("Authors (comma-sep): ");
            List<String> authors = Arrays.asList(sc.nextLine().trim().split(","));

            ResearchPaper p = new ResearchPaper(
                    title, authors, pages,
                    LocalDate.of(year, month, 1),
                    cit, journal, "", doi);
            researcher.addPaper(p);
            System.out.println("Paper added.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void sendMessageMenu(User sender) {
        System.out.print("Recipient login: ");
        String login = sc.nextLine().trim();
        User receiver = Database.getInstance().findByLogin(login);
        if (receiver == null) {
            System.out.println("User not found.");
            return;
        }
        System.out.print("Message: ");
        String text = sc.nextLine().trim();
        msgSvc.send(sender, receiver, text);
        System.out.println("Sent.");
    }

    private static Course pickCourse(List<Course> courses) {
        if (courses.isEmpty()) {
            System.out.println("No courses.");
            return null;
        }
        for (int i = 0; i < courses.size(); i++)
            System.out.println((i + 1) + ". " + courses.get(i).getName());
        System.out.print("Choose course #: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= courses.size())
            return null;
        return courses.get(idx);
    }

    private static int readInt() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void seedIfEmpty() {
        Database db = Database.getInstance();
        if (!db.getAllUsers().isEmpty())
            return;

        System.out.println("[Seed] Initializing demo data...");

        Admin admin = new Admin("a1", "Main Admin", "admin", "admin123", "admin@kbtu.kz");

        Student alice = new Student("s1", "Alice Smith", "alice", "pass123",
                "alice@kbtu.kz", 4);
        alice.setMajor("IS");

        Student bob = new Student("s2", "Bob Jones", "bob", "pass123",
                "bob@kbtu.kz", 2);
        bob.setMajor("IS");

        Professor prof = new Professor("p1", "Dr. Clark", "clark", "pass123",
                "clark@kbtu.kz", 500000, School.FIT);

        Teacher teacher = new Teacher("t1", "Ann Lee", "ann", "pass123",
                "ann@kbtu.kz", 300000, TeacherTitle.LECTOR);

        Manager manager = new Manager("m1", "Dean Brown", "dean", "pass123",
                "dean@kbtu.kz", 450000, ManagerType.DEAN);

        ResearchEmployee re = new ResearchEmployee("re1", "Dan Res", "dan", "pass123",
                "dan@kbtu.kz", 350000, School.BS);

        for (int i = 1; i <= 4; i++) {
            prof.addPaper(new ResearchPaper(
                    "Paper " + i, List.of("Dr. Clark"),
                    10 + i, LocalDate.of(2022 + i, 3, 1),
                    10 * i, "IEEE", "", "doi:" + i));
        }

        Course oop = new Course("OOP", 6, "IS", 2);
        Course ai = new Course("AI", 5, "IS", 4);
        db.getCourses().add(oop);
        db.getCourses().add(ai);
        teacher.addCourse(oop);
        oop.addTeacher(teacher);
        prof.addCourse(ai);
        ai.addTeacher(prof);

        try {
            db.addUser(admin);
            db.addUser(alice);
            db.addUser(bob);
            db.addUser(prof);
            db.addUser(teacher);
            db.addUser(manager);
            db.addUser(re);
        } catch (Exception e) {
            System.out.println("Seed error: " + e.getMessage());
        }

        Database.saveDatabase();
        System.out.println("[Seed] Done. Default logins: admin/admin123, alice/pass123, etc.");
    }
}