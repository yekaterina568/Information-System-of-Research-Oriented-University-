package src.university;

public enum DegreeType {
    BACHELOR,
    MASTER,
    PHD;

    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}
