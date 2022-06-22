package hotel.daos;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;

import hotel.models.Customer;
import hotel.models.Order;
import hotel.utils.MongoDBConnection;

public class CustomerDao {
	protected MongoDBConnection connection;
	protected MongoCollection<Customer> customerCollection;
	
	public CustomerDao(MongoDBConnection connection) {
		this.connection = connection;
		customerCollection = connection.getDataBase().getCollection("customer", Customer.class);
	}
	
	public Customer findCustomerById(String customerIdString) {
		return findCustomerById(new ObjectId(customerIdString));
	}
	
	public Customer findCustomerById(ObjectId customerId) {
		Bson filter = Filters.eq("_id", customerId);

		Customer customer = customerCollection.find(filter).first();
		return customer;
	}
	
	public List<Order> findOrdersOfCustomer(String customerIdString) {
		Customer customer = findCustomerById(customerIdString);
		
		if (customer == null || customer.getOrders().isEmpty()) return null;
		
		List<ObjectId> ordersIds = customer.getOrders();
		List<Order> orders = new ArrayList<Order>();
		OrderDao orderDao = new OrderDao(connection);
		
		for (ObjectId id : ordersIds) {
			Order order = orderDao.findOrderById(id);
			orders.add(order);
		}
		
		return orders;
	}
	
	public boolean addOrderToCustomer(ObjectId customerId, ObjectId orderId) {
		Bson filter = Filters.eq("_id", customerId);
		Bson update = Updates.addToSet("orders", orderId);
		UpdateResult result = customerCollection.updateOne(filter, update);
		
		return result.wasAcknowledged();
	}
	
	public boolean removeOrderFromCustomer(ObjectId customerId, ObjectId orderId) {
		Bson filter = Filters.eq("_id", customerId);
		Bson update = Updates.pull("orders", orderId);
		UpdateResult result = customerCollection.updateOne(filter, update);
		
		return result.wasAcknowledged();
	}
}
