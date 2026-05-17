package src.university;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
public class AttendanceRecord implements Serializable{
	private static final long serialVersionUID=1L;
	private Student student;
	private Course course;
	private LocalDate date;
	private AttendanceStatus status;
	public AttendanceRecord(Student student,Course course,LocalDate date,AttendanceStatus status) {
		this.student=student;
		this.course=course;
		this.date=date;
		this.status=status;
	}
	public Student getStudent() {return student;}
	public Course getCourse() {return course;}
	public LocalDate getDate() {return date;}
	public AttendanceStatus getStatus() {return status;}
	
	@Override
	public String toString() {
		return student.getName()+" | "+course.getName()+" | "+date+" | "+status;
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof AttendanceRecord)) return false;
		AttendanceRecord that=(AttendanceRecord) o;
		return Objects.equals(student,that.student)
				&& Objects.equals(course, that.course)
				&& Objects.equals(date, that.date);	
	}
	@Override
	public int hashCode() {
		return Objects.hash(student,course,date);
	}

}
