package hotel.daos;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;

import hotel.models.Customer;
import hotel.models.Order;
import hotel.utils.MongoDBConnection;

public class OrderDao {
protected MongoDBConnection connection;
	
	public OrderDao(MongoDBConnection connection) {
		this.connection = connection;
	}
	
	public Order findOrderById(String orderIdString) {
		MongoCollection<Order> orderCollection = connection.getDataBase().getCollection("order", Order.class);
		Bson filter = Filters.eq("_id", new ObjectId(orderIdString));
		
		Order order = orderCollection.find(filter).first();
		return order;
	}
	
	public Order findOrderById(ObjectId id) {
		return findOrderById(id.toString());
	}
}
