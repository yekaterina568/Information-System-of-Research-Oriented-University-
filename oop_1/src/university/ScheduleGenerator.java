package src.university;
import java.util.ArrayList;
import java.util.List;
public class ScheduleGenerator {
	private List<ScheduledLesson> schedule=new ArrayList<>();
	
	public boolean addLesson(Course course,Teacher teacher,Room room,TimeSlot slot,Lesson type) {
		if(type==Lesson.LECTURE && room.getRoomType() !=RoomType.LECTURE_HALL) return false;
		if(type==Lesson.PRACTICE && room.getRoomType()==RoomType.LECTURE_HALL) return false;
		if(course.getStudents().size()>room.getCapacity()) return false;
		
		for(ScheduledLesson lesson: schedule) {
			if(lesson.getTimeSlot().equals(slot)) {
				if(lesson.getRoom().equals(room)) return false;
				if(lesson.getTeacher().equals(teacher)) return false;
			}
		}
		schedule.add(new ScheduledLesson(course,teacher,room,slot,type));
		return true;	
	}
	public List<ScheduledLesson> getSchedule(){
		return new ArrayList<>(schedule);
	}
	public void printSchedule() {
		for(ScheduledLesson lesson:schedule) {
			System.out.println(lesson);
		}
	}
}
