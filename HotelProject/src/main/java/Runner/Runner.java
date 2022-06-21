package Runner;

import hotel.utils.MongoDBConnection;
import hotel.utils.SeedDB;

public class Runner {
	
	public static void main(String[] args) {
		MongoDBConnection connection = new MongoDBConnection();
		connection.connect("nosql.properties", "hotel_db");
		//SeedDB.seedRooms(connection);
		//SeedDB.seedHotels(connection);
	//	SeedDB.seedCustomers(connection);
		SeedDB.seedOrders(connection);
		connection.close();
	}
}
