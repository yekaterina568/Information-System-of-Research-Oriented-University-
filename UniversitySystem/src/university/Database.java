package university;
import university.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class Database implements Serializable {
	private static final long serialVersionUID=1L;
	private static final String DB_FILE="database.ser";
	
	private static Database instance;
	private List<User> users=new ArrayList<>();
	private List<Course> courses=new ArrayList<>();
	private Database() {}
	public static Database getInstance() {
		if(instance==null) {
			instance=loadFromFile();
		}
		return instance;
	}
	public static void loadDatabase() {
        getInstance(); 
    }

    public static void saveDatabase() {
        getInstance().save();
    }
    public List<User> getAllUsers() {
        return users;
    }

	public List<Course> getCourses() {
        return courses;
    }
	public boolean isLoginTaken(String login, User excludeUser) {
		return users.stream()
				.filter(user -> excludeUser == null || !user.equals(excludeUser))
				.anyMatch(user -> user.getLogin().equals(login));
	}
	public void addUser(User user) {
		if(isLoginTaken(user.getLogin(), null)) {
			throw new IllegalArgumentException("User with ogin'"+user.getLogin()+"' already exists.");
		}
		users.add(user);
		save();
	}
	public void updateUser(User user, String newName, String newLogin, String newPassword, String newEmail) {
		if(user == null) {
			throw new IllegalArgumentException("User cannot be null.");
		}
		if(newLogin == null || newLogin.isBlank()) {
			throw new IllegalArgumentException("Login cannot be empty.");
		}
		if(isLoginTaken(newLogin, user)) {
			throw new IllegalArgumentException("User with login '" + newLogin + "' already exists.");
		}
		user.setName(newName);
		user.setLogin(newLogin);
		user.setPassword(newPassword);
		user.setEmail(newEmail);
		save();
	}
	public void addCourse(Course course) {
		boolean alreadyExists = courses.stream().anyMatch(existing ->
				existing.getName().equalsIgnoreCase(course.getName())
						&& existing.getYearOfStudy() == course.getYearOfStudy()
						&& Objects.equals(existing.getMajor(), course.getMajor()));
		if (alreadyExists) {
			throw new IllegalArgumentException("Course '" + course.getName() + "' already exists for this major/year.");
		}
		courses.add(course);
		save();
	}
	public boolean removeUser(String userId) {
		boolean removed=users.removeIf(u->u.getId().equals(userId));
		if(removed) {
			save();
		}return removed;
	}
	public User findByLogin(String login) {
		return users.stream()
				.filter(u->u.getLogin().equals(login))
				.findFirst()
				.orElse(null);
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
