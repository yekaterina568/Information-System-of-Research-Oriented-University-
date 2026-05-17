package university;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

	public List<AttendanceRecord> getAttendanceByDateRange(Student student, LocalDate start, LocalDate end) {
		return records.stream()
				.filter(record -> record.getStudent().equals(student))
				.filter(record -> !record.getDate().isBefore(start) && !record.getDate().isAfter(end))
				.sorted(Comparator.comparing(AttendanceRecord::getDate))
				.collect(Collectors.toList());
	}

	public List<AttendanceRecord> getCourseAttendance(Course course) {
		return records.stream()
				.filter(record -> record.getCourse().equals(course))
				.sorted(Comparator.comparing(AttendanceRecord::getDate)
						.thenComparing(record -> record.getStudent().getName()))
				.collect(Collectors.toList());
	}

	public Map<Student, Double> getAttendanceReportByCourse(Course course) {
		Map<Student, Double> report = new LinkedHashMap<>();
		for (Student student : course.getStudents()) {
			report.put(student, getAttendancePercentage(student, course));
		}
		return report.entrySet().stream()
				.sorted(Map.Entry.<Student, Double>comparingByValue().reversed())
				.collect(Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue,
						(left, right) -> left,
						LinkedHashMap::new));
	}

	public List<Student> getAbsentStudents(Course course, LocalDate date) {
		return records.stream()
				.filter(record -> record.getCourse().equals(course))
				.filter(record -> record.getDate().equals(date))
				.filter(record -> record.getStatus() == AttendanceStatus.ABSENT)
				.map(AttendanceRecord::getStudent)
				.distinct()
				.collect(Collectors.toList());
	}

	public List<Student> getLowAttendanceStudents(Course course, double thresholdPercentage) {
		return course.getStudents().stream()
				.filter(student -> getAttendancePercentage(student, course) < thresholdPercentage)
				.collect(Collectors.toList());
	}

	public String getTeacherAttendanceReport(Teacher teacher) {
		StringBuilder builder = new StringBuilder();
		builder.append("Attendance report for ").append(teacher.getName()).append(System.lineSeparator());
		for (Course course : teacher.getCourses()) {
			builder.append(course.getName())
					.append(" -> ")
					.append(getAttendanceReportByCourse(course))
					.append(System.lineSeparator());
		}
		return builder.toString().trim();
	}

}
