package university;
import java.io.Serializable;
public class ScheduledLesson implements Serializable {
	private static final long serialVersionUID=1L;
	
	private Course course;
	private Teacher teacher;
	private Room room;
	private TimeSlot timeSlot;
	private Lesson lesson;
	
	public ScheduledLesson(Course course,Teacher teacher,Room room,TimeSlot timeSlot,Lesson lesson) {
		this.course=course;
		this.teacher=teacher;
		this.room=room;
		this.timeSlot=timeSlot;
		this.lesson=lesson;
	}
	public Course getCourse() {return course;}
	public Teacher getTeacher() {return teacher;}
	public Room getRoom() {return room;}
	public TimeSlot getTimeSlot() {return timeSlot;}
	public Lesson getLesson() {return lesson;}
	
	@Override
	public String toString() {
		return course.getName()+" | "+lesson+" | "+teacher.getName()+" | "+room+" | "+timeSlot;
	}

}
