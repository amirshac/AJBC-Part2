package hotel.daos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import hotel.models.Customer;
import hotel.models.Hotel;
import hotel.models.Order;
import hotel.models.Room;
import hotel.utils.MongoDBConnection;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Projections.*;


public class HotelDao {
	
	protected MongoDBConnection connection;
	protected MongoCollection<Hotel> hotelCollection;
		
	public HotelDao(MongoDBConnection connection) {
		this.connection = connection;
		hotelCollection = connection.getDataBase().getCollection("hotel", Hotel.class);
	}
	
	public Hotel findByCityName(String cityName) {
		return hotelCollection.find(Filters.eq("address.city", cityName)).first(); 
	}
	
	public Hotel findHotelById(ObjectId id) {
		return hotelCollection.find(Filters.eq("_id", id)).first();
	}
	
	public boolean hasAvailableRoomAt(ObjectId hotelId, LocalDate date) {
		List<ObjectId> availableRooms = availableRoomsByHotelAtDateRange(hotelId, date, date);
		return !(availableRooms.isEmpty());		
	}
	
	public boolean hasAvailableRoomAt(ObjectId hotelId, LocalDate startDate, LocalDate endDate) {
		List<ObjectId> availableRooms = availableRoomsByHotelAtDateRange(hotelId, startDate, endDate);
		return !(availableRooms.isEmpty());		
	}
	
	/**
	 * Returns hotel's available rooms ids in date range
	 * @param hotelId
	 * @param startDate
	 * @param endDate
	 * @return list of available rooms Ids
	 */
	public List<ObjectId> availableRoomsByHotelAtDateRange(ObjectId hotelId, LocalDate startDate, LocalDate endDate){
		List<ObjectId> availableRooms = new ArrayList<ObjectId>();
		
		Hotel hotel = findHotelById(hotelId);
		List<ObjectId> roomIds = hotel.getRooms();
		
		OrderDao orderDao = new OrderDao(connection);
		
		// search for orders for each room on a specific date 
		// - if theres no orders, the room is available
		for (ObjectId roomId : roomIds) {
			List<Order> orders = orderDao.findOrdersByRoomInDateRange(roomId, startDate, endDate);
			if (orders.isEmpty()) {
				availableRooms.add(roomId);
			}
			orders = null;
		}
		return availableRooms;
	}
	
	public boolean addOrderToHotel(ObjectId hotelId, ObjectId orderId) {
		Bson filter = Filters.eq("_id", hotelId);
		Bson update = Updates.addToSet("orders", orderId);
		UpdateResult result = hotelCollection.updateOne(filter, update);
		
		return result.wasAcknowledged();
	}
	
	public boolean removeOrderFromHotel(ObjectId hotelId, ObjectId orderId) {
		Bson filter = Filters.eq("_id", hotelId);
		Bson update = Updates.pull("orders", orderId);
		UpdateResult result = hotelCollection.updateOne(filter, update);
		
		return result.wasAcknowledged();
	}
}
