package university;
import java.io.Serializable;
import university.Observer;
public abstract class User implements Observer,Serializable {
	private static final long serialVersionUID=1L;
	protected String id;
	protected String login;
	protected String password;
	protected String firstName;
	protected String lastName;
	protected String email;
	public User(){}
	
	public User(String id,String login,String password,String firstnme,String lastName,String email) {
		this.id=id;
		this.login=login;
		this.password=password;
		this.firstName=firstName;
		this.lastName=lastName;
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
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName=firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName=lastName;
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
				"',name='"+firstName+" "+lastName+"'}";
	}
}
