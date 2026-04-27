package university;
import university.Observer;
import java.util.ArrayList;
import java.util.List;
public class NotificationService {
	private final List<Observer> subscribers=new ArrayList<>();
	public void subscribe(Observer observer) {
		if(!subscribers.contains(observer)) {
			subscribers.add(observer);
		}
	}
	public void unsubscribe(Observer observer) {
		subscribers.remove(observer);
	}
	public void notifyAll(String eventType,Object data) {
		for(Observer observer:subscribers) {
			observer.update(eventType, data);
		}
	}
	public void notify(Observer target,String eventType,Object data) {
		target.update(eventType, data);
	}

}
