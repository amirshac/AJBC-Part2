package hotel.daos;

import org.bson.conversions.Bson;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import hotel.models.Hotel;
import hotel.utils.MongoDBConnection;

public class HotelDao {
	
	protected MongoDBConnection connection;
		
	public HotelDao(MongoDBConnection connection) {
		this.connection = connection;
	}
	
	public Hotel findByCityName(String cityName) {
		MongoCollection<Hotel> hotelCollection = connection.getDataBase().getCollection("hotel", Hotel.class);
		Bson filter = Filters.eq("address.city", cityName);
		Hotel hotel = hotelCollection.find(filter).first();
		return hotel;
	}
}
