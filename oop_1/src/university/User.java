package src.university;
import java.io.Serializable;

import src.university.Observer;
public abstract class User implements Observer,Serializable {
	private static final long serialVersionUID=1L;
	protected String id;
	protected String name;
	protected String login;
	protected String password;
	protected String email;
	public User(){}
	
	public User(String id,String name,String login,String password,String email) {
		this.id=id;
		this.name=name;
		this.login=login;
		this.password=password;
		this.email=email;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id=id;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login=login;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email=email;
	}
	@Override
	public void update(String eventType,Object data) {
		System.out.println("[Notification: "+login+"] Event: "+eventType+" Data: "+data);
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof User)) return false;
		User user=(User) o;
		return id!=null&&id.equals(user.id);
	}
	@Override
	public int hashCode() {
		return id!=null?id.hashCode():0;
	}
	@Override
	public String toString() {
		return getClass().getSimpleName()+"{id='"+id+"',login='"+login+
				"',name='"+name+"'}";
	}
}
