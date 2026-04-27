package university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends User implements Observer, Serializable {
    private static final long serialVersionUID = 1L;
    private String employeeId;
    private double salary;
    private List<String> inbox;
    private List<String> sentComplaints;

    public Employee(String login, String password, String firstName, String lastName, String employeeId, double salary) {
        super(login, password, firstName, lastName);
        this.employeeId = employeeId;
        this.salary = salary;
        this.inbox = new ArrayList<>();
        this.sentComplaints = new ArrayList<>();
    }

    @Override
    public void update(String message) {
        inbox.add(message);
        System.out.println("[Notification] " + getFirstName() + " " + getLastName() + ": " + message);
    }

    public void sendMessage(Employee recipient, String text) {
        String msg = "From " + getFirstName() + " " + getLastName() + ": " + text;
        recipient.update(msg);
        System.out.println("Message sent to " + recipient.getFirstName() + " " + recipient.getLastName());
    }

    public void sendComplaint(String complaint) {
        sentComplaints.add(complaint);
        System.out.println("Complaint submitted by " + getFirstName() + " " + getLastName() + ": " + complaint);
    }

    public String getEmployeeId() { return employeeId; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public List<String> getInbox() { return inbox; }
    public List<String> getSentComplaints() { return sentComplaints; }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName() + " (ID: " + employeeId + ")";
    }
}