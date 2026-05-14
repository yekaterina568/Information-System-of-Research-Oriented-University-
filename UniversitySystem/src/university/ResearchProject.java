package university;
import university.NotResearcherException;
import university.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class ResearchProject implements Serializable{
	private static final long serialVersionUID=1L;
	private String id;
	private String topic;
	private List<ResearchPaper> publishedPapers=new ArrayList<>();
	private List<Researcher> participants=new ArrayList<>();
	
	public ResearchProject(String id,String topic) {
		this.id=id;
		this.topic=topic;
	}
	public void addPArticipant(User user) throws NotResearcherException{
		if(!(user instanceof Researcher)) {
			throw new NotResearcherException(
					"User '"+user.getLogin()+"' is not a Researcher and cannot join project '"+topic+"'.");	
		}
		participants.add((Researcher)user);
	}
	public void addPaper(ResearchPaper paper) {
		publishedPapers.add(paper);
	}
	public String getId() {
		return id;
	}
	public String getTopic() {
		return topic;
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
				"', participants="+participants.size()+
				", papers="+publishedPapers.size()+"}";
	}

}
