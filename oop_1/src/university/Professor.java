package src.university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Professor extends Teacher implements Researcher, Serializable {
    private static final long serialVersionUID = 1L;
    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;
    private School school;
    public Professor(String id,String name,String login, String password, String email,  double salary,School school) {
        super(id,name,login, password, email, salary, TeacherTitle.PROFESSOR);
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
        this.school=school;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (researchPapers.isEmpty()) {
            System.out.println("No research papers yet.");
            return;
        }
        List<ResearchPaper> sorted = new ArrayList<>(researchPapers);
        sorted.sort(comparator);
        System.out.println("=== Papers of Prof. " + getName() +" ===");
        for (ResearchPaper paper : sorted) {
            System.out.println(paper);
        }
    }
    @Override
    public int getHIndex() {
    	List<Integer> citations = new ArrayList<>();
    	for (ResearchPaper p: researchPapers) {
    		citations.add(p.getCitations());
    	}
    	citations.sort(Collections.reverseOrder());
    	int h = 0;
    	for(int i = 0; i < citations.size(); i++) {
    		if(citations.get(i) >= i + 1) h = i + 1;
    		else { break;}
    	}
    	return h;
    }

    @Override
    public void addPaper(ResearchPaper paper) {
        researchPapers.add(paper);
        System.out.println("Paper added: " + paper.getTitle());
    }
    @Override
    public void addProject(ResearchProject project) {
    	if(!researchProjects.contains(project)) {
    		researchProjects.add(project);
    	}
    }

    public void joinResearchProject(ResearchProject project) throws NotResearcherException {
        if (!project.getParticipants().contains(this)) {
            project.addParticipant(this);
            researchProjects.add(project);
            System.out.println("Prof. " + getName() + " joined project: " + project.getTopic());
        } else {
            System.out.println("Already a participant.");
        }
    }

    @Override
    public List<ResearchPaper> getPapers() { return researchPapers; }

    @Override
    public List<ResearchProject> getProjects() { return researchProjects; }
    
    @Override
    public School getSchool() {
    	return school;
    }
    @Override
    public void setSchool(School school) {this.school=school;}

    @Override
    public String toString() {
        return "Prof. " + getName() + " | H-Index: " + getHIndex() + " | Papers: " + researchPapers.size();
    }
}
