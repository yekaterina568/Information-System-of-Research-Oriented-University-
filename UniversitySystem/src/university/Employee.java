package university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Employee extends User implements Observer, Serializable {
    private static final long serialVersionUID = 1L;
    private double salary;
    private List<String> inbox;
    private List<String> sentComplaints;

    public Employee(String id,String name,String login, String password,String email,double salary) {
        super(id,name,login, password,email);
        this.salary = salary;
        this.inbox = new ArrayList<>();
        this.sentComplaints = new ArrayList<>();
    }

    @Override
    public void update(String eventType,Object data) {
        String message=eventType+":"+data;
        inbox.add(message);
        System.out.println("[Notification] " + getName() +":" + message);
    }

    public void sendMessage(Employee recipient, String text) {
        recipient.update("MESSAGE","From"+getName()+":"+text);
        System.out.println("Message sent to " + recipient.getName());
    }

    public void sendComplaint(String complaint) {
        sentComplaints.add(complaint);
        System.out.println("Complaint submitted by " + getName() + ": " + complaint);
    }

    public String getId() { return id; }
    public double getSalary() { return salary; }
    public void setSalary(double salary) { this.salary = salary; }
    public List<String> getInbox() { return inbox; }
    public List<String> getSentComplaints() { return sentComplaints; }

    @Override
    public String toString() {
        return getName() + " (ID: " + id + ")";
    }
}