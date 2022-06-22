package hotel.daos;

import static com.mongodb.client.model.Aggregates.match;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import hotel.models.Customer;
import hotel.models.Hotel;
import hotel.models.Order;
import hotel.models.Room;
import hotel.utils.MongoDBConnection;

public class OrderDao {
	
protected MongoDBConnection connection;
protected MongoCollection<Order> orderCollection;

	public OrderDao(MongoDBConnection connection) {
		this.connection = connection;
		orderCollection = connection.getDataBase().getCollection("order", Order.class);
	}
	
	public Order findOrderById(String orderIdString) {
		Bson filter = Filters.eq("_id", new ObjectId(orderIdString));
		
		Order order = orderCollection.find(filter).first();
		return order;
	}
	
	public Order findOrderById(ObjectId id) {
		return findOrderById(id.toString());
	}
	
	public List<Order> findOrdersByRoomInDateRange(ObjectId id, LocalDate startDate, LocalDate endDate) {
		Bson roomFilter = Filters.eq("room_id", id);
		Bson datefilter1 = Filters.and(Filters.lte("start_date", startDate), Filters.gte("end_date", endDate));
		Bson datefilter2 = Filters.and(Filters.lte("start_date", endDate), Filters.gte("start_date", startDate));
		Bson datefilter3 = Filters.and(Filters.lte("end_date", endDate), Filters.gte("end_date", startDate));
		Bson filter = Filters.or(datefilter1, datefilter2, datefilter3);
		filter = Filters.and(filter, roomFilter);
		List<Order> result = orderCollection.find(filter).into(new ArrayList<Order>());
		
		return result;
	}
	
	public List<Order> findOrdersByRoomInDateRange(String id, LocalDate startDate, LocalDate endDate){
		return findOrdersByRoomInDateRange(new ObjectId(id), startDate, endDate);
	}

	/**
	 * Create new order for hotel and updates relevant database collections
	 * @param hotelId
	 * @param customerId
	 * @param startDate
	 * @param nights
	 * @return created order or NULL in case there was no free room in hotel
	 */
	public Order createOrderForHotel(ObjectId hotelId, ObjectId customerId, LocalDate startDate, int nights) {
		CustomerDao customerDao = new CustomerDao(connection);
		HotelDao hotelDao = new HotelDao(connection);
		RoomDao roomDao = new RoomDao(connection);
		
		Order order = null;
		
		LocalDate endDate = startDate.plusDays(nights);
		
		Hotel hotel = hotelDao.findHotelById(hotelId);
		
		Customer customer = customerDao.findCustomerById(customerId);
		
		// get available room ids
		List<ObjectId> availableRoomIds = hotelDao.availableRoomsByHotelAtDateRange(hotelId, startDate, endDate);
		
		// no free rooms - can't order
		if (availableRoomIds.isEmpty())
			return null;
		
		// pick free room
		ObjectId freeRoomId = availableRoomIds.get(0);	
		
		Room room = roomDao.findRoomById(freeRoomId);
		
		if ((room == null) || (hotel == null) || (customer == null)) return null;
		
		float totalPrice = nights * hotel.getPricePerNight();
		
		LocalDate orderDate = LocalDate.now();
		
		order = new Order(hotelId, freeRoomId, customerId, orderDate, startDate, nights, totalPrice);
		ObjectId orderId = order.getId();
		
		// insert new order to database and update objectId references in hotel and customer collections
		InsertOneResult result = orderCollection.insertOne(order);
		if (!result.wasAcknowledged()) return null;
		
		hotelDao.addOrderToHotel(hotelId, orderId);
		customerDao.addOrderToCustomer(customerId, orderId);
		
		return order;
	}
	
	/**
	 * Deletes order in order collections and relevant collections in hotel and custoemr
	 * @param orderId
	 * @return canceled order or NULL if no order was found
	 */
	public Order cancelOrder(ObjectId orderId) {
		Order order = orderCollection.findOneAndDelete(Filters.eq("_id", orderId));
		
		if (order == null) return null;
		
		CustomerDao customerDao = new CustomerDao(connection);
		HotelDao hotelDao = new HotelDao(connection);
		
		hotelDao.removeOrderFromHotel(order.getHotelId(), orderId);
		customerDao.removeOrderFromCustomer(order.getCustomerId(), orderId);
		
		return order;
	}
}
