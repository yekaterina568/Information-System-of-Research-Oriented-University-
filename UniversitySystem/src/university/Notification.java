package university;
import java.io.Serializable;
import java.time.LocalDateTime;
public class Notification implements Serializable {
	private static final long serialVersionUID=1L;
	private NotificationType type;
	private String message;
	private LocalDateTime createdAt;
	private boolean read;
	
	public Notification(NotificationType type,String message) {
		this.type=type;
		this.message=message;
		this.createdAt=LocalDateTime.now();
		this.read=false;
	}
	public NotificationType getType() {return type;}
	public String getmessage() {return message;}
	public LocalDateTime getCreatedAt() {return createdAt;}
	public boolean isRead() {return read;}
	
	public void markAsRead() {
		this.read=true;
	}
	@Override
	public String toString() {
		return"["+type+"}"+message+" | "+createdAt+" | read="+read;
	}
}
