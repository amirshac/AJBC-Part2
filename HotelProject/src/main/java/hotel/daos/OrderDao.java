package hotel.daos;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.InsertOneResult;

import hotel.models.Customer;
import hotel.models.Hotel;
import hotel.models.Order;
import hotel.models.Room;
import hotel.utils.MongoDBConnection;

public class OrderDao {

	protected MongoDBConnection connection;
	protected MongoCollection<Order> orderCollection;
	protected MongoCollection<Document> orderDocumentCollection;

	public OrderDao(MongoDBConnection connection) {
		this.connection = connection;
		orderCollection = connection.getDataBase().getCollection("order", Order.class);
		orderDocumentCollection = connection.getDataBase().getCollection("order");
	}

	public Order findOrderById(ObjectId id) {
		return orderCollection.find(Filters.eq("_id", id)).first();
	}

	public Order findOrderById(String orderIdString) {
		return findOrderById(new ObjectId(orderIdString));
	}

	/**
	 * Checks if there's orders under a specific room that overlaps a date range
	 * 
	 * @param id
	 * @param startDate
	 * @param endDate
	 * @return List of overlapping orders or NULL if no orders were found
	 */
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

	public List<Order> findOrdersByRoomInDateRange(String id, LocalDate startDate, LocalDate endDate) {
		return findOrdersByRoomInDateRange(new ObjectId(id), startDate, endDate);
	}

	/**
	 * Create new order for hotel and updates relevant database collections
	 * 
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
		List<ObjectId> availableRoomIds = hotelDao.availableRoomsByHotelInDateRange(hotelId, startDate, endDate);

		// no free rooms - can't order
		if (availableRoomIds.isEmpty())
			return null;

		// pick free room
		ObjectId freeRoomId = availableRoomIds.get(0);

		Room room = roomDao.findRoomById(freeRoomId);

		if ((room == null) || (hotel == null) || (customer == null))
			return null;

		float totalPrice = nights * hotel.getPricePerNight();

		LocalDate orderDate = LocalDate.now();

		order = new Order(hotelId, freeRoomId, customerId, orderDate, startDate, nights, totalPrice);
		ObjectId orderId = order.getId();

		// insert new order to database and update objectId references in hotel and
		// customer collections
		InsertOneResult result = orderCollection.insertOne(order);
		if (!result.wasAcknowledged())
			return null;

		hotelDao.addOrderToHotel(hotelId, orderId);
		customerDao.addOrderToCustomer(customerId, orderId);

		return order;
	}

	/**
	 * Deletes order in order collections and relevant collections in hotel and
	 * custoemr
	 * 
	 * @param orderId
	 * @return canceled order or NULL if no order was found
	 */
	public Order cancelOrder(ObjectId orderId) {
		Order order = orderCollection.findOneAndDelete(Filters.eq("_id", orderId));

		if (order == null)
			return null;

		CustomerDao customerDao = new CustomerDao(connection);
		HotelDao hotelDao = new HotelDao(connection);

		hotelDao.removeOrderFromHotel(order.getHotelId(), orderId);
		customerDao.removeOrderFromCustomer(order.getCustomerId(), orderId);

		return order;
	}

	public void displaySortedHotelsByTotalIncomeFromOrders() {
		List<Document> result = null;
		Bson group = Aggregates.group("$hotel_id", Accumulators.sum("hotel_income", "$total_price"));
		Bson sort = sort(Sorts.descending("totalPrice"));
		Bson project = project(fields(excludeId(), include("hotel_income"),computed("hotel_id", "$_id")));

		result = orderDocumentCollection.aggregate(Arrays.asList(group, sort, project)).into(new ArrayList<>());

		result.forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));
	}

	public void displayTotalIncomeFromOrders() {
		List<Document> result = null;
		Bson group = Aggregates.group(null, Accumulators.sum("total_income_from_orders", "$total_price"));
		Bson project = project(fields(excludeId(), include("total_income_from_orders")));
		result = orderDocumentCollection.aggregate(Arrays.asList(group, project)).into(new ArrayList<>());
	
		result.forEach(doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build())));
	}
	
	public void testJoin() {
		List<Document> result = null;

		Bson group = Aggregates.group("$hotel_id", Accumulators.sum("hotel_income", "$total_price"));
		Bson sort = sort(Sorts.descending("totalPrice"));
		Bson project = project(fields(excludeId(), include("joined._id", "joined.name", "hotel_income"),computed("hotel_id", "$_id")));
		result = orderDocumentCollection.aggregate(Arrays.asList(group,project)).into(new ArrayList<>());

		
		Bson leftJoin = Aggregates.lookup("hotel", "hotel_id", "_id", "joined");
		
		
		result.forEach(doc -> System.out.println("\n-------------\n" + doc.toJson(JsonWriterSettings.builder().indent(true).build())));
	}
}
