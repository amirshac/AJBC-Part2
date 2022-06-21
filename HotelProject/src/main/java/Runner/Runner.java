package Runner;

import java.util.List;

import hotel.daos.CustomerDao;
import hotel.daos.HotelDao;
import hotel.models.Customer;
import hotel.models.Hotel;
import hotel.models.Order;
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
	
	public static void main(String[] args) {
		connection.connect("nosql.properties", "hotel_db");
		//SeedDB.seedHotels(connection);
		//SeedDB.seedRooms(connection);
		//SeedDB.seedCustomers(connection);
		//SeedDB.seedOrders(connection);
		
		//testFindHotelByCityName();
		testFindOrdersOfCustomer();
		
		connection.close();
	}
}
