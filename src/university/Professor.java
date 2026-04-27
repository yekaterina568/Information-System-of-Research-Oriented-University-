package university;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class Professor extends Teacher implements Researcher, Serializable {
    private static final long serialVersionUID = 1L;
    private int hIndex;
    private List<ResearchPaper> researchPapers;
    private List<ResearchProject> researchProjects;

    public Professor(String login, String password, String firstName, String lastName, String employeeId, double salary, int hIndex) {
        super(login, password, firstName, lastName, employeeId, salary, TeacherTitle.PROFESSOR);
        this.hIndex = hIndex;
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> comparator) {
        if (researchPapers.isEmpty()) {
            System.out.println("No research papers yet.");
            return;
        }
        List<ResearchPaper> sorted = new ArrayList<>(researchPapers);
        sorted.sort(comparator);
        System.out.println("=== Papers of Prof. " + getFirstName() + " " + getLastName() + " ===");
        for (ResearchPaper paper : sorted) {
            System.out.println(paper);
        }
    }

    @Override
    public void addResearchPaper(ResearchPaper paper) {
        researchPapers.add(paper);
        System.out.println("Paper added: " + paper.getTitle());
    }

    @Override
    public void joinResearchProject(ResearchProject project) throws NotResearcherException {
        if (!project.getParticipants().contains(this)) {
            project.addParticipant(this);
            researchProjects.add(project);
            System.out.println("Prof. " + getFirstName() + " joined project: " + project.getTopic());
        } else {
            System.out.println("Already a participant.");
        }
    }

    @Override
    public int getHIndex() { return hIndex; }
    public void setHIndex(int hIndex) { this.hIndex = hIndex; }

    @Override
    public List<ResearchPaper> getResearchPapers() { return researchPapers; }

    @Override
    public List<ResearchProject> getResearchProjects() { return researchProjects; }

    @Override
    public String toString() {
        return "Prof. " + getFirstName() + " " + getLastName() + " | H-Index: " + hIndex + " | Papers: " + researchPapers.size();
    }
}