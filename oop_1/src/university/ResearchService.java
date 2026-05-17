package src.university;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
public class ResearchService{
	public static List<Researcher> getAllResearchers(List<User> users) {
        List<Researcher> researchers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Researcher) {
                researchers.add((Researcher) user);
            }
        }
        return researchers;
    }
    public static void printAllResearchers(List<User> users) {
        List<Researcher> researchers = getAllResearchers(users);
        System.out.println("=== All Researchers in University ===");
        for (Researcher researcher : researchers) {
            System.out.println(researcher);
        }
    }
    public static void printAllUniversityPapers(List<User> users, Comparator<ResearchPaper> comparator) {
        List<ResearchPaper> allPapers = new ArrayList<>();
        for (User user : users) {
            if (user instanceof Researcher) {
                allPapers.addAll(((Researcher) user).getPapers());
            }
        }
        allPapers.sort(comparator);
        System.out.println("=== All University Research Papers ===");
        for (ResearchPaper paper : allPapers) {
            System.out.println(paper);
        }
    }
    public static Researcher getTopCitedResearcher(List<User> users) {
        Researcher top = null;
        int maxCitations = -1;
        for (User user : users) {
            if (user instanceof Researcher researcher) {
                int citations = getTotalCitations(researcher);
                if (citations > maxCitations) {
                    maxCitations = citations;
                    top = researcher;
                }
            }
        }
        return top;
    }
    public static Researcher getTopCitedResearcherOfYear(List<User> users, int year) {
        Researcher top = null;
        int maxCitations = -1;
        for (User user : users) {
            if (user instanceof Researcher researcher) {
                int citations = 0;
                for (ResearchPaper paper : researcher.getPapers()) {
                    if (paper.getDatePublished() != null && paper.getDatePublished().getYear() == year) {
                        citations += paper.getCitations();
                    }
                }
                if (citations > maxCitations) {
                    maxCitations = citations;
                    top = researcher;
                }
            }
        }
        return top;
    }
    public static Researcher getTopCitedResearcherBySchool(List<User> users, School school) {
        Researcher top = null;
        int maxCitations = -1;

        for (User user : users) {
            if (user instanceof Researcher researcher && researcher.getSchool() == school) {
                int citations = getTotalCitations(researcher);
                if (citations > maxCitations) {
                    maxCitations = citations;
                    top = researcher;
                }
            }
        }
        return top;
    }
    public static Researcher getTopCitedResearcherBySchoolAndYear(List<User> users, School school, int year) {
        Researcher top = null;
        int maxCitations = -1;
        for (User user : users) {
            if (user instanceof Researcher researcher && researcher.getSchool() == school) {
                int citations = 0;
                for (ResearchPaper paper : researcher.getPapers()) {
                    if (paper.getDatePublished() != null && paper.getDatePublished().getYear() == year) {
                        citations += paper.getCitations();
                    }
                }
                if (citations > maxCitations) {
                    maxCitations = citations;
                    top = researcher;
                }
            }
        }
        return top;
    }
    public static int getTotalCitations(Researcher researcher) {
        int sum = 0;
        for (ResearchPaper paper : researcher.getPapers()) {
            sum += paper.getCitations();
        }
        return sum;
    }	
}