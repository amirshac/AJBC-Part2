package hotel.models;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Room {
	protected ObjectId id;
	@BsonProperty(value = "hotel_id")
	protected ObjectId hotelId;
	@BsonProperty(value = "number")
	protected int number;
	@BsonProperty(value = "has_bath")
	protected boolean hasBath;
	@BsonProperty(value = "max_occupants")
	protected int maxOccupants;
	
	public Room(ObjectId id, ObjectId hotelId, int number, boolean hasBath, int maxOccupants) {
		this.id = id;
		this.hotelId = hotelId;
		this.number = number;
		this.hasBath = hasBath;
		this.maxOccupants = maxOccupants;
	}
	
	public Room(ObjectId hotelId, int number, boolean hasBath, int maxOccupants) {
		this.id = new ObjectId();
		this.hotelId = hotelId;
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

	public ObjectId getHotelId() {
		return hotelId;
	}

	public void setHotelId(ObjectId hotelId) {
		this.hotelId = hotelId;
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
		return "Room [id=" + id + ", hotelId=" + hotelId + ", number=" + number + ", hasBath="
				+ hasBath + ", maxOccupants=" + maxOccupants + "]";
	}
	
}


