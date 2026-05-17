package university;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Complaint implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Status { PENDING, REVIEWED, RESOLVED, REJECTED }

    private static int counter = 0;

    private final int    id;
    private final User   sender;
    private final String text;
    private final LocalDateTime createdAt;
    private Status  status;
    private String  reviewNote;
    private String  signedBy; 

    public Complaint(User sender, String text) {
        this.id        = ++counter;
        this.sender    = sender;
        this.text      = text;
        this.createdAt = LocalDateTime.now();
        this.status    = Status.PENDING;
    }

    public void sign(String managerName) {
        this.signedBy = managerName;
        this.status   = Status.REVIEWED;
    }

    public void resolve(String note) {
        this.reviewNote = note;
        this.status     = Status.RESOLVED;
    }

    public void reject(String note) {
        this.reviewNote = note;
        this.status     = Status.REJECTED;
    }

    public int            getId()         { return id; }
    public User           getSender()     { return sender; }
    public String         getText()       { return text; }
    public LocalDateTime  getCreatedAt()  { return createdAt; }
    public Status         getStatus()     { return status; }
    public String         getReviewNote() { return reviewNote; }
    public String         getSignedBy()   { return signedBy; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Complaint)) return false;
        return id == ((Complaint) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        return String.format("Complaint#%d [%s] from %s: \"%s\" | Status: %s%s",
                id, createdAt.toLocalDate(), sender.getName(), text, status,
                signedBy != null ? " | Signed by: " + signedBy : "");
    }
}


