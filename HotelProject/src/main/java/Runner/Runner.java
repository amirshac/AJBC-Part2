package Runner;

import hotel.daos.HotelDao;
import hotel.models.Hotel;
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
	
	public static void main(String[] args) {
		connection.connect("nosql.properties", "hotel_db");
		//SeedDB.seedHotels(connection);
		//SeedDB.seedRooms(connection);
		//SeedDB.seedCustomers(connection);
		SeedDB.seedOrders(connection);
		
		//testFindHotelByCityName();
		
		connection.close();
	}
}
