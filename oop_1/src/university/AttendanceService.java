package src.university;
import java.util.ArrayList;
import java.util.List;
public class AttendanceService {
	private List<AttendanceRecord> records=new ArrayList<>();
	public void markAttendance(Student student,Course course,java.time.LocalDate date,AttendanceStatus status) {
		records.add(new AttendanceRecord(student,course,date,status));
	}
	public List<AttendanceRecord> getStudentAttendance(Student student){
		List<AttendanceRecord> result=new ArrayList<>();
		for(AttendanceRecord record:records) {
			if(record.getStudent().equals(student)) {
				result.add(record);
			}
		}return result;
	}
	public double getAttendancePercentage(Student student,Course course) {
		int total=0;
		int present=0;
		
		for(AttendanceRecord record:records) {
			if(record.getStudent().equals(student) && record.getCourse().equals(course)) {
				total++;
				if(record.getStatus()==AttendanceStatus.PRESENT||record.getStatus()==AttendanceStatus.LATE) {
					present++;
				}
			}
		}
		return total==0? 0.0:(present*100.0)/total;
	}

}
