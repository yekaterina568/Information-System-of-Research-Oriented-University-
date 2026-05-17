package university;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    private static int counter = 0;

    private final int id;
    private final User sender;
    private final User receiver;
    private final String text;
    private final LocalDateTime timestamp;
    private boolean read;

    public Message(User sender, User receiver, String text) {
        this.id        = ++counter;
        this.sender    = sender;
        this.receiver  = receiver;
        this.text      = text;
        this.timestamp = LocalDateTime.now();
        this.read      = false;
    }

    public void markAsRead() { this.read = true; }

    public int       getId()        { return id; }
    public User      getSender()    { return sender; }
    public User      getReceiver()  { return receiver; }
    public String    getText()      { return text; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public boolean   isRead()       { return read; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        return id == ((Message) o).id;
    }

    @Override
    public int hashCode() { return Objects.hash(id); }

    @Override
    public String toString() {
        String fmt = timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        return String.format("[%s] From: %s → %s | %s%s",
                fmt, sender.getName(), receiver.getName(),
                text, read ? "" : " [NEW]");
    }
}
