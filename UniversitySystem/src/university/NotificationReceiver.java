package university;
import java.util.List;
public interface NotificationReceiver {
	void receiveNotification(Notification notification);
	default void receiverNotification(Notification notification) {
		receiveNotification(notification);
	}
	List<Notification> getNotifications();

}
