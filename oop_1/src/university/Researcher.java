package src.university;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
public interface Researcher {
	int getHIndex();
	List<ResearchPaper> getPapers();
	List<ResearchProject> getProjects();
	void addPaper(ResearchPaper paper);
	void addProject(ResearchProject project);
	School getSchool();
	void setSchool(School school);
	
	default void printPapers(Comparator<ResearchPaper> comparator) {
		List<ResearchPaper> sorted=new ArrayList<>(getPapers());
		sorted.sort(comparator);
		for(ResearchPaper p:sorted) {
			System.out.println(p);
		}
	}
	static Comparator<ResearchPaper> byDate(){
		return Comparator.comparing(ResearchPaper::getDatePublished).reversed();
	}
	static Comparator<ResearchPaper> byCitations(){
		return Comparator.comparingInt(ResearchPaper::getCitations).reversed();
	}
	static Comparator<ResearchPaper> byPages(){
		return Comparator.comparingInt(ResearchPaper::getPages).reversed();
	}

}
