package university;
import university.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
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
}