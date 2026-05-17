package university;
import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;
public class TimeSlot implements Serializable {
	private static final long serialVersionUID=1L;
	private DayOfWeek dayOfWeek;
	private LocalTime startTime;
	private LocalTime endTime;
	
	public TimeSlot(DayOfWeek dayOfWeek,LocalTime startTime,LocalTime endTime) {
		this.dayOfWeek=dayOfWeek;
		this.startTime=startTime;
		this.endTime=endTime;
	}
	public DayOfWeek getDayOfWeek() {return dayOfWeek;}
	public LocalTime getStartTime() {return startTime;}
	public LocalTime getEndTime() {return endTime;}
	
	@Override
	public String toString() {
		return dayOfWeek+" "+startTime+"-"+endTime;
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof TimeSlot))return false;
		TimeSlot timeSlot=(TimeSlot) o;
		return dayOfWeek==timeSlot.dayOfWeek
				&& Objects.equals(startTime,timeSlot.startTime)
				&& Objects.equals(endTime, timeSlot.endTime);
	}
	@Override
	public int hashCode() {
		return Objects.hash(dayOfWeek,startTime,endTime);
	}

}
