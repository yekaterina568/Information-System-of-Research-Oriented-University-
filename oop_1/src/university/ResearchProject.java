package src.university;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.util.Objects;

import src.university.NotResearcherException;
import src.university.User;
public class ResearchProject implements Serializable{
	private static final long serialVersionUID=1L;
	private String id;
	private String topic;
	private LocalDate startDate;
	private LocalDate endDate;
	private ResearchProjectStatus status;
	private List<ResearchPaper> publishedPapers=new ArrayList<>();
	private List<Researcher> participants=new ArrayList<>();
	
	public ResearchProject(String id,String topic,LocalDate startDate,LocalDate endDate,ResearchProjectStatus status) {
		this.id=id;
		this.topic=topic;
		this.startDate=startDate;
		this.endDate=endDate;
		this.status=status;
	}
	public void addParticipant(User user) throws NotResearcherException{
		if(!(user instanceof Researcher)) {
			throw new NotResearcherException(
					"User '"+user.getLogin()+"' is not a Researcher and cannot join project '"+topic+"'.");	
		}
		Researcher researcher=(Researcher) user;
		if(!participants.contains(researcher)) {
			participants.add(researcher);
			researcher.addProject(this);
		}
	}
	public void addPaper(ResearchPaper paper) {
		if(!publishedPapers.contains(paper)) {
			publishedPapers.add(paper);
		}
	}
	public String getid() {
		return id;
	}
	public String getTopic() {
		return topic;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public ResearchProjectStatus getStatus() {
		return status;
	}
	public void setStatus(ResearchProjectStatus status) {
		this.status=status;
	}
	public List<ResearchPaper> getPublishedPapers(){
		return new ArrayList<>(publishedPapers);
	}
	public List<Researcher> getParticipants(){
		return new ArrayList<>(participants);
	}
	@Override
	public String toString() {
		return "ResearchProject{id='"+id+"', topic='"+topic+
				"', status="+status+
				", participants="+participants.size()+
				", papers="+publishedPapers.size()+"}";
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof ResearchProject)) return false;
		ResearchProject that=(ResearchProject) o;
		return Objects.equals(id,that.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}	