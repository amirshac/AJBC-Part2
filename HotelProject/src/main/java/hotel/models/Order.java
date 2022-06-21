package hotel.models;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Order {
	private ObjectId id;
	@BsonProperty(value = "hotel_id")
	private int hotelId;
	@BsonProperty(value = "customer_id")
	private int customerId;
	@BsonProperty(value = "order_date")
	private LocalDate orderDate;
	@BsonProperty(value = "start_date")
	private LocalDate startDate;
	private int nights;
	@BsonProperty(value = "total_price")
	private float totalPrice;
	
	public Order(ObjectId id, int hotelId, int customerId, LocalDate orderDate, LocalDate startDate,
			int nights, float totalPrice) {
		this.id = id;
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.nights = nights;
		this.totalPrice = totalPrice;
	}
	
	public Order(int hotelId, int customerId, LocalDate orderDate, LocalDate startDate,
			int nights, float totalPrice) {
		this.id = new ObjectId();
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.nights = nights;
		this.totalPrice = totalPrice;
	}
	
	public Order() {}
	
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
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public LocalDate getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public int getNights() {
		return nights;
	}
	public void setNights(int nights) {
		this.nights = nights;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Override
	public String toString() {
		return "Order [id=" + id + ", hotelId=" + hotelId + ", customerId=" + customerId + ", orderDate=" + orderDate
				+ ", startDate=" + startDate + ", nights=" + nights + ", totalPrice=" + totalPrice + "]";
	}
	
}