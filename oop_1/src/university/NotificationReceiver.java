package src.university;
import java.util.List;
public interface NotificationReceiver {
	void receiverNotification(Notification notification);
	List<Notification> getNotifications();

}
