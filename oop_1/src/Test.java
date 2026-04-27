package src;

public class Test {
    public static void main(String[] args) {
        Database.loadDatabase();
        Database db = Database.getInstance();

        Admin admin = new Admin("Main admin", "admin", "admin123");
        Student s1 = new Student("Alice", "alice_st", "pass123");

        admin.addUser(s1);

        Course oop = new Course("OOP", 5);
        Course heavyCourse = new Course("Heavy Math", 20);
        db.getCourses().add(oop);
        db.getCourses().add(heavyCourse);

        try {
            s1.enrollCourse(oop); 
            s1.enrollCourse(heavyCourse); 
        } catch (MaxCreditsException e) {
            System.out.println("Catched Exception: " + e.getMessage());
        }

        Mark mark = new Mark(20, 25, 30);
        s1.setMark(oop, mark);
        
        System.out.println("Student: " + s1.getName() + " | The mark for OOP: " + mark.getTotal());

        Database.saveDatabase();
    }
}