package Runner;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;

import hotel.daos.CustomerDao;
import hotel.daos.HotelDao;
import hotel.daos.OrderDao;
import hotel.models.Hotel;
import hotel.models.Order;
import hotel.utils.DateUtil;
import hotel.utils.MongoDBConnection;
import hotel.utils.SeedDB;

public class Runner {
	static MongoDBConnection connection;
	
	static {
		connection = new MongoDBConnection();
	}
	
	public static void testFindHotelByCityName() {
		HotelDao hotelDao = new HotelDao(connection);
		
		Hotel hotel;
		hotel = hotelDao.findByCityName("Higueruela"); 
		System.out.println(hotel);
		
		hotel = hotelDao.findByCityName("Tel-Aviv");
		System.out.println(hotel);
	}
	
	public static void testFindOrdersOfCustomer() {
		CustomerDao customerDao = new CustomerDao(connection);
		List<Order> orders = customerDao.findOrdersOfCustomer("62b1ea87345a2860707718d1");
		System.out.println(orders);
	}
	
	public static void testHasAvailableRoom() {
		HotelDao hotelDao = new HotelDao(connection);
		hotelDao.hasAvailableRoomAt("62b1e395c5ae613f0133e12a", LocalDate.of(2022, 10, 10));
	}
	
	public static void testHasOrdersByRoom() {
		OrderDao orderDao = new OrderDao(connection);
		List<Order> result = orderDao.findOrdersByRoomInDateRange
				("62b1ea86345a2860707718c6",LocalDate.of(2022, 9, 6), LocalDate.of(2022, 9, 6));
		System.out.println(result);
	}
	
	public static void testAvailableRoomsByHotelAtDateRange() {
		HotelDao hotelDao = new HotelDao(connection);
		List<ObjectId> result = hotelDao.availableRoomsByHotelAtDateRange(new ObjectId("62b1e395c5ae613f0133e12a"),LocalDate.of(2022, 9, 1), LocalDate.of(2022, 9, 6));
		System.out.println(result);
		
	}
	
	public static void main(String[] args) {
		connection.connect("nosql.properties", "hotel_db");
		//SeedDB.seedHotels(connection);
		//SeedDB.seedRooms(connection);
		//SeedDB.seedCustomers(connection);
		//SeedDB.seedOrders(connection);
		
		//testFindHotelByCityName();
		//testFindOrdersOfCustomer();
		//testHasAvailableRoom();
		
		//testHasOrdersByRoom();
		
		testAvailableRoomsByHotelAtDateRange();
		
		connection.close();
	}
}
