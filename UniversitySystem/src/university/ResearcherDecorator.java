package university;
import university.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
public class ResearcherDecorator extends User implements Researcher{
	private static final long serialVersionUID=1L;
	private final User decoratedUser;
	private School school;
	private final List<ResearchPaper> papers=new ArrayList<>();
	private final List<ResearchProject> projects=new ArrayList<>();
	public ResearcherDecorator(User user,School school) {
		super(user.getId(),user.getName(),user.getLogin(),user.getPassword(),user.getEmail());
		this.decoratedUser=user;
		this.school=school;
}
	public User getDecoratedUser() {
		return decoratedUser;
	}
	@Override
	public int getHIndex() {
		List<Integer> citationCounts= new ArrayList<>();
		for (ResearchPaper paper:papers) {
			citationCounts.add(paper.getCitations());
		}
		citationCounts.sort(Collections.reverseOrder());
		int h=0;
		for(int i=0;i<citationCounts.size();i++) {
			if(citationCounts.get(i)>=i+1) {
				h=i+1;
			}else {break;}
		}
		return h;
				
	}
	@Override
	public void addPaper(ResearchPaper paper) {
		if(!papers.contains(paper)) {
			papers.add(paper);
		}
	}
	@Override
	public List<ResearchPaper> getPapers(){
		return new ArrayList<>(papers);
	}
	@Override
	public void addProject(ResearchProject project) {
		if(!projects.contains(project)) {
			projects.add(project);
		}
	}
	@Override
	public List<ResearchProject> getProjects(){
		return new ArrayList<>(projects);
	}
	@Override
	public School getSchool() {
		return school;
	}
	@Override
	public void setSchool(School school) {
		this.school=school;
	}
	@Override
	public void update(String eventType,Object data) {
		decoratedUser.update(eventType, data);
	}
	@Override
	public String toString() {
		return "ResearcherDecorator{user="+decoratedUser.getName()+
				", school="+school+
				", papers="+papers.size()+"}";
	}
	
}