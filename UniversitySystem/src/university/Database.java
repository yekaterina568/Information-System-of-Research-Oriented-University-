package university;
import university.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Database implements Serializable {
	private static final long serialVersionUID=1L;
	private static final String DB_FILE="database.ser";
	
	private static Database instance;
	private List<User> users=new ArrayList<>();
	private Database() {}
	public static Database getInstance() {
		if(instance==null) {
			instance=loadFromFile();
		}
		return instance;
	}
	public void addUser(User user) {
		if(users.stream().anyMatch(u->u.getLogin().equals(user.getLogin()))) {
			throw new IllegalArgumentException("User with ogin'"+user.getLogin()+"' already exists.");
		}
		users.add(user);
		save();
	}
	public boolean removeUser(String userId) {
		boolean removed=users.removeIf(u->u.getId().equals(userId));
		if(removed) save();
		return removed;
	}
	public User findByLogin(String login) {
		return users.stream()
				.filter(u->u.getLogin().equals(login))
				.findFirst()
				.orElse(null);
	}
	public List<User> getAllUsers(){
		return new ArrayList<>(users);
	}
	public void save() {
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(DB_FILE))){
			oos.writeObject(this);
			System.out.println("[Database] Saved successfully.");
		}catch(IOException e) {
			System.err.println("[Database] Save error: "+e.getMessage());
		}
	}
	private static Database loadFromFile() {
		File file=new File(DB_FILE);
		if(file.exists()) {
			try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(file))){
				Database loaded=(Database) ois.readObject();
				System.out.println("[Database] Loaded from file.");
				return loaded;
			}catch(IOException |ClassNotFoundException e) {
				System.err.println("[Database] Load error,starting fresh: "+e.getMessage());
			}
		}
		return new Database();
	}
	protected Object readResolve() {
		instance=this;
		return instance;
	}
}
