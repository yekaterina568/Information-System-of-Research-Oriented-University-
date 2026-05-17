package university;

import java.io.Serializable;

public class Request implements Serializable {

    private static final long serialVersionUID = 1L;

    private String text;
    private Employee sender;
    private boolean signed;
    private String signedBy;
    
    public void sign(Manager manager) {
        this.signed = true;
        this.signedBy = manager.getName();
    }

    public Request(String text,
                   Employee sender) {

        this.text = text;
        this.sender = sender;
        this.signed = false;
    }

    public void sign(String managerName) {

        this.signed = true;
        this.signedBy = managerName;
    }

    public boolean isSigned() {
        return signed;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public String getText() {
        return text;
    }

    public Employee getSender() {
        return sender;
    }

    @Override
    public String toString() {

        return "Request{" +
                "text='" + text + '\'' +
                ", sender=" +
                sender.getName() +
                ", signed=" +
                signed +
                ", signedBy='" +
                signedBy + '\'' +
                '}';
    }
}
