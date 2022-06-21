package hotel.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Room {
	protected ObjectId id;
	@BsonProperty(value = "hotel_id")
	protected int hotelId;
	@BsonProperty(value = "room_id")
	protected int roomId;
	@BsonProperty(value = "number")
	protected int number;
	@BsonProperty(value = "has_bath")
	protected boolean hasBath;
	@BsonProperty(value = "max_occupants")
	protected int maxOccupants;
	
	public Room(ObjectId id, int hotelId, int roomId, int number, boolean hasBath, int maxOccupants) {
		this.id = id;
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.number = number;
		this.hasBath = hasBath;
		this.maxOccupants = maxOccupants;
	}
	
	public Room(int hotelId, int roomId, int number, boolean hasBath, int maxOccupants) {
		this.id = new ObjectId();
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.number = number;
		this.hasBath = hasBath;
		this.maxOccupants = maxOccupants;
	}
	
	public Room() {}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isHasBath() {
		return hasBath;
	}

	public void setHasBath(boolean hasBath) {
		this.hasBath = hasBath;
	}

	public int getMaxOccupants() {
		return maxOccupants;
	}

	public void setMaxOccupants(int maxOccupants) {
		this.maxOccupants = maxOccupants;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", hotelId=" + hotelId + ", roomId=" + roomId + ", number=" + number + ", hasBath="
				+ hasBath + ", maxOccupants=" + maxOccupants + "]";
	}
	
}


