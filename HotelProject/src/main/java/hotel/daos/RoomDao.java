package hotel.daos;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import hotel.models.Room;
import hotel.utils.MongoDBConnection;

public class RoomDao {

	protected MongoDBConnection connection;
	protected MongoCollection<Room> roomCollection;
		
	public RoomDao(MongoDBConnection connection) {
		this.connection = connection;
		roomCollection = connection.getDataBase().getCollection("room", Room.class);
	}
	
	public Room findRoomById(ObjectId id) {
		return roomCollection.find(Filters.eq("_id", id)).first();
	}
}
