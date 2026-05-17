package university;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
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
	public static List<Teacher> searchTeachersByTitle(List<User> users, TeacherTitle title) {
		return users.stream()
				.filter(Teacher.class::isInstance)
				.map(Teacher.class::cast)
				.filter(teacher -> teacher.getTitle() == title)
				.sorted(Comparator.comparing(Teacher::getName))
				.collect(Collectors.toList());
	}
	public static List<Course> searchCoursesByCredits(List<Course> courses, int minCredits, int maxCredits) {
		return courses.stream()
				.filter(course -> course.getCredits() >= minCredits && course.getCredits() <= maxCredits)
				.sorted(Comparator.comparing(Course::getCredits).thenComparing(Course::getName))
				.collect(Collectors.toList());
	}
	public static List<ResearchPaper> searchPapersByCriteria(List<ResearchPaper> papers,
			Integer minCitations,
			Integer year,
			String journalRegex) {
		Pattern journalPattern = journalRegex == null || journalRegex.isBlank()
				? null
				: Pattern.compile(journalRegex, Pattern.CASE_INSENSITIVE);
		return papers.stream()
				.filter(paper -> minCitations == null || paper.getCitations() >= minCitations)
				.filter(paper -> year == null || (paper.getDatePublished() != null && paper.getDatePublished().getYear() == year))
				.filter(paper -> journalPattern == null || journalPattern.matcher(paper.getJournal()).find())
				.sorted()
				.collect(Collectors.toList());
	}
	public static String formatSearchResults(List<?> results) {
		if (results.isEmpty()) {
			return "No results found.";
		}
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < results.size(); i++) {
			builder.append(i + 1)
					.append(". ")
					.append(results.get(i))
					.append(System.lineSeparator());
		}
		return builder.toString().trim();
	}

}
