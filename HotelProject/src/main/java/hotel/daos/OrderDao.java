package hotel.daos;

import static com.mongodb.client.model.Aggregates.match;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
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
	
	public List<Order> findOrdersByRoomInDateRange(ObjectId id, LocalDate startDate, LocalDate endDate) {
		MongoCollection<Order> orderCollection = connection.getDataBase().getCollection("order", Order.class);
		Bson roomFilter = Filters.eq("room_id", id);
		Bson datefilter1 = Filters.and(Filters.lte("start_date", startDate), Filters.gte("end_date", endDate));
		Bson datefilter2 = Filters.and(Filters.lte("start_date", endDate), Filters.gte("start_date", startDate));
		Bson datefilter3 = Filters.and(Filters.lte("end_date", endDate), Filters.gte("end_date", startDate));
		Bson filter = Filters.or(datefilter1, datefilter2, datefilter3);
		filter = Filters.and(filter, roomFilter);
		List<Order> result = orderCollection.find(filter).into(new ArrayList<Order>());
		
		return result;
	}
	
	public List<Order> findOrdersByRoomInDateRange(String id, LocalDate startDate, LocalDate endDate){
		return findOrdersByRoomInDateRange(new ObjectId(id), startDate, endDate);
	}

}
