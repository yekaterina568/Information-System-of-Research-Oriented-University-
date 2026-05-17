package src.university;
import src.university.ResearcherDecorator;
import src.university.User;
public class UserFactory {
	public static ResearcherDecorator makeResearcher(User existingUser,School school) {
		if(existingUser instanceof ResearcherDecorator) {
			return (ResearcherDecorator) existingUser;
		}
		return new ResearcherDecorator(existingUser,school);
	}
}
