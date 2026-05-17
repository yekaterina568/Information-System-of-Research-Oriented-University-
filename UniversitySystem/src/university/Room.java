package university;
import java.io.Serializable;
import java.util.Objects;
public class Room implements Serializable{
	private static final long serialVersionUID=1L;
	private String roomNumber;
	private RoomType roomType;
	private int capacity;
	
	public Room(String roomNumber,RoomType roomType,int capacity) {
		this.roomNumber=roomNumber;
		this.roomType=roomType;
		this.capacity=capacity;
	}
	public String getRoomNumber() {return roomNumber;}
	public RoomType getRoomType() {return roomType;}
	public int getCapacity() {return capacity;}
	@Override
	public String toString() {
		return roomNumber+"("+roomType+", cap="+capacity+")";
	}
	@Override
	public boolean equals(Object o) {
		if(this==o) return true;
		if(!(o instanceof Room)) return false;
		Room room=(Room) o;
		return Objects.equals(roomNumber,room.roomNumber);
	}
	@Override
	public int hashCode() {
		return Objects.hash(roomNumber);
	}

}
