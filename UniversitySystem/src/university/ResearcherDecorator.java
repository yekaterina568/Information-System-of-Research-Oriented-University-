package university;
import university.User;
import java.util.ArrayList;
import java.util.List;
public class ResearcherDecorator extends User implements Researcher{
	private static final long serialVersionUID=1L;
	private final User decoratedUser;
	private final List<ResearchPaper> papers=new ArrayList<>();
	private final List<ResearchProject> projects=new ArrayList<>();
	public ResearcherDecorator(User user) {
		this.decoratedUser=user;
		this.id=user.getId();
		this.login=user.getLogin();
		this.password=user.getPassword();
		this.firstName=user.getFirstName();
		this.lastName=user.getLastName();
		this.email=user.getEmail();
}
	@Override
	public int getHIndex() {
		List<Integer> citationCounts=papers.stream()
				.map(ResearchPaper::getCitations)
				.sorted(java.util.Comparator.reverseOrder())
				.collect(java.util.stream.Collectors.toList());
		int h=0;
		for(int i=0;i<citationCounts.size();i++) {
			if(citationCounts.get(i)>=i+1) {
				h=i+1;
			}else {
				break;
			}
		}
		return h;
	}
	@Override
	public void addPaper(ResearchPaper paper) {
		papers.add(paper);
	}
	@Override
	public List<ResearchPaper> getPapers(){
		return new ArrayList<>(papers);
	}
	public void addProject(ResearchProject project) {
		projects.add(project);
	}
	public List<ResearchProject> getProjects(){
		return new ArrayList<>(projects);
	}
	@Override
	public void update(String eventType,Object data) {
		decoratedUser.update(eventType, data);
	}
	public User getDecoratedUser() {
		return decoratedUser;
	}
	@Override
	public String toString() {
		return"ResearcherDecorator{wrapping="+decoratedUser+
				", hIndex="+getHIndex()+ ", papers="+papers.size()+"}";
	}

}
