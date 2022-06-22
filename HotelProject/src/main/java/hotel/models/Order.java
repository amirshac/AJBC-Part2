package hotel.models;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import hotel.utils.*;

public class Order {
	private ObjectId id;
	@BsonProperty(value = "hotel_id")
	private ObjectId hotelId;
	@BsonProperty(value = "room_id")
	private ObjectId roomId;
	@BsonProperty(value = "customer_id")
	private ObjectId customerId;
	@BsonProperty(value = "order_date")
	private LocalDate orderDate;
	@BsonProperty(value = "start_date")
	private LocalDate startDate;
	@BsonProperty(value = "end_date")
	private LocalDate endDate;
	private int nights;
	@BsonProperty(value = "total_price")
	private float totalPrice;

	public Order(ObjectId id, ObjectId hotelId, ObjectId roomId, ObjectId customerId, LocalDate orderDate, LocalDate startDate,
			int nights, float totalPrice) {
		this.id = id;
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.endDate = DateUtil.calculateEndDate(startDate, nights);
	}

	public Order(ObjectId hotelId, ObjectId roomId, ObjectId customerId, LocalDate orderDate, LocalDate startDate, int nights,
			float totalPrice) {
		this.id = new ObjectId();
		this.hotelId = hotelId;
		this.roomId = roomId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.nights = nights;
		this.totalPrice = totalPrice;
		this.endDate = DateUtil.calculateEndDate(startDate, nights);
	}

	public Order() {
	}

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

	public ObjectId getRoomId() {
		return roomId;
	}

	public void setRoomId(ObjectId roomId) {
		this.roomId = roomId;
	}

	public ObjectId getCustomerId() {
		return customerId;
	}

	public void setCustomerId(ObjectId customerId) {
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

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate startDate) {
		this.endDate = endDate;
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