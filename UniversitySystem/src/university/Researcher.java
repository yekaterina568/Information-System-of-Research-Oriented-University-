package university;
import java.util.Comparator;
import java.util.List;
public interface Researcher {
	int getHIndex();
	void addPaper(ResearchPaper paper);
	List<ResearchPaper> getPapers();
	default void printPapers(Comparator<ResearchPaper> comparator) {
		List<ResearchPaper> sorted=new java.util.ArrayList<>(getPapers());
		sorted.sort(comparator);
		sorted.forEach(System.out::println);
	}
	static Comparator<ResearchPaper>byDate(){
		return Comparator.comparing(ResearchPaper::getDatePublished);
	}
	static Comparator<ResearchPaper> byCitations(){
		return Comparator.comparingInt(ResearchPaper::getCitations).reversed();
	}
	static Comparator<ResearchPaper> byLength(){
		return Comparator.comparingInt(ResearchPaper::getPages).reversed();
	}

}
