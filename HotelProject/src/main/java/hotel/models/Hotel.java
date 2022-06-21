package hotel.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Hotel {
	protected ObjectId id;
	@BsonProperty(value = "hotel_id")
	protected int hotelId;
	protected String name;
	protected Address address;
	protected int rank;
	protected List<Integer> rooms;
	@BsonProperty(value = "price_per_night")
	protected float pricePerNight;
	protected List<Integer> orders;
	
	public Hotel(ObjectId id, int hotelId, String name, Address address, int rank, List<Integer> rooms,
		float pricePerNight, List<Integer> orders) {
		this.id = id;
		this.hotelId = hotelId;
		this.name = name;
		this.address = address;
		this.rank = rank;
		this.rooms = rooms;
		this.pricePerNight = pricePerNight;
		this.orders = orders;
	}
	
	public Hotel(int hotelId, String name, Address address, int rank, List<Integer> rooms,
			float pricePerNight, List<Integer> orders) {
			this.id = new ObjectId();
			this.hotelId = hotelId;
			this.name = name;
			this.address = address;
			this.rank = rank;
			this.rooms = rooms;
			this.pricePerNight = pricePerNight;
			this.orders = orders;
		}

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<Integer> getRooms() {
		return rooms;
	}

	public void setRooms(List<Integer> rooms) {
		this.rooms = rooms;
	}

	public float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public List<Integer> getOrders() {
		return orders;
	}

	public void setOrders(List<Integer> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", hotelId=" + hotelId + ", name=" + name + ", address=" + address + ", rank=" + rank
				+ ", rooms=" + rooms + ", pricePerNight=" + pricePerNight + ", orders=" + orders + "]";
	}
	
}
