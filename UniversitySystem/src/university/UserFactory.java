package university;
import university.User;
import university.ResearcherDecorator;
public class UserFactory {
	public static ResearcherDecorator makeResearcher(User existingUser,School school) {
		if(existingUser instanceof ResearcherDecorator) {
			return (ResearcherDecorator) existingUser;
		}
		return new ResearcherDecorator(existingUser,school);
	}
}
