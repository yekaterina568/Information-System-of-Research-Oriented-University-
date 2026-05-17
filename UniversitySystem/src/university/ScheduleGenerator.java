package university;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
				boolean sharedStudents = lesson.getCourse().getStudents().stream()
						.anyMatch(course.getStudents()::contains);
				if(sharedStudents) return false;
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
	public List<ScheduledLesson> getLessonsByCourse(Course course) {
		return schedule.stream()
				.filter(lesson -> lesson.getCourse().equals(course))
				.collect(Collectors.toList());
	}
	public Map<Room, Long> getRoomLoadReport() {
		return schedule.stream()
				.collect(Collectors.groupingBy(
						ScheduledLesson::getRoom,
						LinkedHashMap::new,
						Collectors.counting()));
	}
	public Map<String, List<ScheduledLesson>> groupByDay() {
		return schedule.stream()
				.collect(Collectors.groupingBy(
						lesson -> lesson.getTimeSlot().toString(),
						LinkedHashMap::new,
						Collectors.toList()));
	}
}
