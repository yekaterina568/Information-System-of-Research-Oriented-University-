package university;
import java.io.Serializable; 
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
public class ResearchPaper implements Comparable<ResearchPaper>,Serializable {
	private static final long serialVersionUID=1L;
	private String title;
	private List<String>authors;
	private int pages;
	private LocalDate datePublished;
	private int citations;
	private String journal;
	private String references;
	private String doi;
	public ResearchPaper() {}
	public ResearchPaper(String title,List<String> authors,int pages,LocalDate datePublished,int citations,String journal,String references,String doi) {
		this.title=title;
		this.authors=authors;
		this.pages=pages;
		this.datePublished=datePublished;
		this.citations=citations;
		this.journal=journal;
		this.references=references;
		this.doi=doi;
	}
	@Override
	public int compareTo(ResearchPaper other) {
		return Integer.compare(other.citations, this.citations);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title=title;
	}
	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors=authors;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages=pages;
	}
	public LocalDate getDatePublished() {
		return datePublished;
	}
	public void setDatePublished(LocalDate datePublished) {
		this.datePublished=datePublished;
	}
	public int getCitations() {
		return citations;
	}
	public void setCitations(int sitations ) {
		this.citations=citations;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String Journal) {
		this.journal=journal;
	}
	public String getReferences() {
		return references;
	}
	public void setReferences(String references) {
		this.references=references;
	}
	public String getDoi() {
		return doi;
	}
	public void setDoi(String doi) {
		this.doi=doi;
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof ResearchPaper)) return false;
		ResearchPaper p=(ResearchPaper) o;
		return Objects.equals(doi, p.doi);
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(doi);
	}
	@Override
	public String toString() {
		return "ResearchPaper{title='"+title+"', journal='"+journal+
				"', date="+datePublished+", citations="+citations+
				", pages="+pages+", doi='"+doi+"'}";
	}

}
