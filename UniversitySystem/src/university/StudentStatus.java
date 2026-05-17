package university;

public enum StudentStatus {
	ACTIVE,
    DISMISSED,
    GRADUATED,
    ON_LEAVE;

    @Override
    public String toString() {
        return name().replace('_', ' ').toLowerCase();
    }

}
