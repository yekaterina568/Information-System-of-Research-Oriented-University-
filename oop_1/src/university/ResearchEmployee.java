package src.university;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class ResearchEmployee extends Employee implements Researcher {
	private static final long serialVersionUID=1L;
	private School school;
	private List<ResearchPaper> papers=new ArrayList<>();
	private List<ResearchProject> projects=new ArrayList<>();
	public ResearchEmployee(String id,String name,String login,String password,String email,double salary,School school) {
		super(id,name,login,password,email,salary);
		this.school=school;
	}
	@Override
	public int getHIndex() {
		List<Integer> citations=new ArrayList<>();
		for(ResearchPaper paper:papers) {
			citations.add(paper.getCitations());
		}
		citations.sort(Collections.reverseOrder());
		int h=0;
		for(int i=0;i<citations.size();i++) {
			if(citations.get(i)>=i+1) {
				h=i+1;
			}else {
				break;
			}
		}
		return h;
	}
	@Override
	public List<ResearchPaper> getPapers(){
		return new ArrayList<>(papers);
	}
	@Override
	public List<ResearchProject> getProjects(){
		return new ArrayList<>(projects);
	}
	@Override
	public void addPaper(ResearchPaper paper) {
		if(!papers.contains(paper)) {
			papers.add(paper);
		}
	}
	@Override
	public void addProject(ResearchProject project) {
		if(!projects.contains(project)) {
			projects.add(project);
		}
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
	public String toString() {
		return "ResearchEvployee{name='"+getName()+"', school="+school+
				", hIndex="+getHIndex()+", papers="+papers.size()+"}";
	}

}
