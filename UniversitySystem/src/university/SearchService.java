package university;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
public class SearchService {
	public static List<User> searchUserByRegex(List<User> users,String regex){
		Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		List<User> result=new ArrayList<>();
		for(User user:users) {
			if(pattern.matcher(user.getName()).find()||
				pattern.matcher(user.getLogin()).find()||
				pattern.matcher(user.getEmail()).find()){
					result.add(user);
				}
		}
		return result;
	}
	public static List<Course> searchCoursesByRegex(List<Course> courses,String regex){
		Pattern pattern=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		List<Course> result=new ArrayList<>();
		for(Course course:courses) {
			if(pattern.matcher(course.getName()).find()) {
				result.add(course);
			}
		}
		return result;
	}
	public static List<ResearchPaper> searchPapersByRegex(List<ResearchPaper> papers,String regex){
		Pattern pattern =Pattern.compile(regex,Pattern.CASE_INSENSITIVE);
		List<ResearchPaper> result=new ArrayList<>();
		for(ResearchPaper paper:papers) {
			if(pattern.matcher(paper.getTitle()).find()
					||pattern.matcher(paper.getJournal()).find()
					||pattern.matcher(paper.getDoi()).find()) {
				result.add(paper);
			}
		}
		return result;
	}

}
