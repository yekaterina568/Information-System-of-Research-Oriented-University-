package oop_1;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        User user = UserFactory.createUser("STUDENT", name);
        Student s1 = (Student) user;

        Course oop = new Course("OOP", 5);

       
        Database.getInstance().getCourses().add(oop);

        s1.enrollCourse(oop);

        Mark mark = new Mark(20, 25, 30);
        s1.setMark(oop, mark);

        System.out.println("Student: " + s1.getName());
        System.out.println("Course: " + oop.getName());
        System.out.println("Total mark: " + mark.getTotal());
    }
}
