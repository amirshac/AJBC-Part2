package hotel.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Hotel {
	protected ObjectId id;
	protected String name;
	protected Address address;
	protected int rank;
	protected List<ObjectId> rooms;
	@BsonProperty(value = "price_per_night")
	protected float pricePerNight;
	protected List<ObjectId> orders;
	
	public Hotel(ObjectId id, String name, Address address, int rank, List<ObjectId> rooms,
		float pricePerNight, List<ObjectId> orders) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.rank = rank;
		this.rooms = rooms;
		this.pricePerNight = pricePerNight;
		this.orders = orders;
	}
	
	public Hotel(String name, Address address, int rank, List<ObjectId> rooms,
			float pricePerNight, List<ObjectId> orders) {
			this.id = new ObjectId();
			this.name = name;
			this.address = address;
			this.rank = rank;
			this.rooms = rooms;
			this.pricePerNight = pricePerNight;
			this.orders = orders;
		}
	
	public Hotel() {}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public List<ObjectId> getRooms() {
		return rooms;
	}

	public void setRooms(List<ObjectId> rooms) {
		this.rooms = rooms;
	}

	public float getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(float pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public List<ObjectId> getOrders() {
		return orders;
	}

	public void setOrders(List<ObjectId> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", address=" + address + ", rank=" + rank
				+ ", rooms=" + rooms + ", pricePerNight=" + pricePerNight + ", orders=" + orders + "]";
	}
	
	public void addOrder(ObjectId orderId) {
		orders.add(orderId);
	}
	
	public void removeOrder(ObjectId orderId) {
		if (orders.contains(orderId)){
			orders.remove(orderId);
		}
	}
}
