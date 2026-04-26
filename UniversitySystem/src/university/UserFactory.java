package university;
import university.User;
import university.ResearcherDecorator;
public class UserFactory {
	public enum UserType{
		STUDENT,TEACHER,ADMIN,MANAGER,EMPLOYEE
	}
	public static User createUser(UserType type,String id,String login,String password,String firstName,String lastName,String email) {
		throw new UnsupportedOperationException(
				"Class for type "+type+" not yet implemented by team members.");
	}
	public static User createUser(UserType type,String id,String login,String password,String firstName,String lastName,String email,boolean isResearcher) {
		User user=createUser(type,id,login,password,firstName,lastName,email);
		if(isResearcher) {
			return new ResearcherDecorator(user);
		}
		return user;
	}
	public static ResearcherDecorator makeResearcher(User existingUser) {
		if(existingUser instanceof ResearcherDecorator) {
			return (ResearcherDecorator) existingUser;
		}
		return new ResearcherDecorator(existingUser);
	}

}
