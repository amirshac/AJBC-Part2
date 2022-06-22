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
		MongoCollection<Hotel> hotelCollection = connection.getDataBase().getCollection("hotel", Hotel.class);
		Bson filter = Filters.eq("address.city", cityName);
		Hotel hotel = hotelCollection.find(filter).first();
		return hotel;
	}
	
	public Hotel findHotelById(ObjectId id) {
		Bson filter = Filters.eq("_id", id);
		Hotel hotel = hotelCollection.find(filter).first();
		return hotel;
	}
	
	public boolean hasAvailableRoomAt(ObjectId hotelId, LocalDate date) {
		List<ObjectId> availableRooms = availableRoomsByHotelAtDateRange(hotelId, date, date);
		return !(availableRooms.isEmpty());		
	}
	
	public boolean hasAvailableRoomAt(ObjectId hotelId, LocalDate startDate, LocalDate endDate) {
		List<ObjectId> availableRooms = availableRoomsByHotelAtDateRange(hotelId, startDate, endDate);
		return !(availableRooms.isEmpty());		
	}
	
	public List<ObjectId> availableRoomsByHotelAtDateRange(ObjectId hotelId, LocalDate startDate, LocalDate endDate){
		List<ObjectId> availableRooms = new ArrayList<ObjectId>();
		
		Hotel hotel = findHotelById(hotelId);
		List<ObjectId> roomIds = hotel.getRooms();
		
		OrderDao orderDao = new OrderDao(connection);
		
		// search for orders for each room on a specific date 
		// - if theres no orders, the room is available
		for (ObjectId roomId : roomIds) {
			List<Order> orders = orderDao.findOrdersByRoomInDateRange(roomId, startDate, endDate);
			//System.out.println(orders);
			//System.out.println(orders.isEmpty());
			if (orders.isEmpty()) {
				//System.out.println("adding " + roomId);
				availableRooms.add(roomId);
			}
			orders = null;
		}
		return availableRooms;
	}
	
	public void hasAvailableRoomAt(String hotelIdString, LocalDate date) {
		//return 
				hasAvailableRoomAt(new ObjectId(hotelIdString), date);
	}
	
}
