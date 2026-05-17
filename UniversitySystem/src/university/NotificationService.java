package university;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
public class NotificationService {
	private Map<User,List<Notification>> storage=new HashMap<>();
	public void send(User user,Notification notification) {
		storage.putIfAbsent(user, new ArrayList<>());
		storage.get(user).add(notification);
	}
	public List<Notification> getUserNotifications(User user){
		return new ArrayList<>(storage.getOrDefault(user, new ArrayList<>()));
	}
	public void printUserNotifications(User user) {
		List<Notification> list=getUserNotifications(user);
		for(Notification notification:list) {
			System.out.println(notification);
		}
	}
	public List<Notification> getUnreadNotifications(User user) {
		return getUserNotifications(user).stream()
				.filter(notification -> !notification.isRead())
				.sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
				.collect(Collectors.toList());
	}
	public List<Notification> getReadNotifications(User user) {
		return getUserNotifications(user).stream()
				.filter(Notification::isRead)
				.sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
				.collect(Collectors.toList());
	}
	public void markAllAsRead(User user) {
		storage.getOrDefault(user, new ArrayList<>())
				.forEach(Notification::markAsRead);
	}
	public void markAsRead(User user, Notification notification) {
		List<Notification> notifications = storage.get(user);
		if (notifications == null) {
			return;
		}
		for (Notification current : notifications) {
			if (current == notification) {
				current.markAsRead();
				return;
			}
		}
	}
} 
