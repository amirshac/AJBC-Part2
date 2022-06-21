package hotel.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Customer {

	protected ObjectId id;
	@BsonProperty(value = "customer_id")
	protected int customerId;
	@BsonProperty(value = "first_name")
	protected String firstName;
	@BsonProperty(value = "last_name")
	protected String lastName;
	protected String country;
	protected List<ObjectId> orders;
	
	public Customer(ObjectId id, int customerId, String firstName, String lastName, String country,
			List<ObjectId> orders) {
		this.id = id;
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.orders = orders;
	}
	
	public Customer(int customerId, String firstName, String lastName, String country,
			List<ObjectId> orders) {
		this.id = new ObjectId();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.orders = orders;
	}
	
	public Customer() {}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<ObjectId> getOrders() {
		return orders;
	}

	public void setOrders(List<ObjectId> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", customerId=" + customerId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", country=" + country + ", orders=" + orders + "]";
	}
	
}