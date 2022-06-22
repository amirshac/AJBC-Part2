package hotel.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;

import hotel.models.*;

public class SeedDB {
	
	public static void seedRooms(MongoDBConnection connection) {
		
		MongoCollection<Room> roomCollection = connection.getDataBase().getCollection("room", Room.class);
		roomCollection.drop();
		
		List<Room> rooms = new ArrayList<Room>();
		
		//int hotelId, int roomId, int number, boolean hasBath, int maxOccupants)
		ObjectId hotelId; 
		
		hotelId = new ObjectId("62b1e395c5ae613f0133e12a");
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718c6"), hotelId, 100, false, 2));
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718c7"), hotelId, 101, false, 2));
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718c8"), hotelId, 102, true, 2));
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718c9"), hotelId, 103, true, 2));
		
		hotelId = new ObjectId("62b1ea87345a2860707718cf");
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718ca"), hotelId, 200, false, 4));
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718cb"), hotelId, 201, true, 4));
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718cc"), hotelId, 203, true, 4));
		
		hotelId = new ObjectId("62b1ea87345a2860707718d0");
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718cd"), hotelId, 300, false, 3));
		rooms.add(new Room(new ObjectId("62b1ea86345a2860707718ce"), hotelId, 301, true, 3));
		
		InsertManyResult result = roomCollection.insertMany(rooms);
		System.out.println("Room seed > " + result.wasAcknowledged());
		
	}
	
	public static void seedHotels(MongoDBConnection connection) {
		
		MongoCollection<Hotel> hotelCollection = connection.getDataBase().getCollection("hotel", Hotel.class);
		hotelCollection.drop();

		List<Hotel> hotels = new ArrayList<Hotel>();
		Address address = null;
		List<ObjectId> roomList = new ArrayList<ObjectId>();
		List<ObjectId> ordersList = new ArrayList<ObjectId>();
		
		//hotel - int hotelId, String name, Address address, int rank, List<Integer> rooms, float pricePerNight, List<Integer> orders
		//address - (String street, int number, String city, String country) 
		address = new Address("Hayarkon", 57, "Tel-Aviv","Israel");
		roomList = Arrays.asList(
				new ObjectId("62b1ea86345a2860707718c6"),
				new ObjectId("62b1ea86345a2860707718c7"),
				new ObjectId("62b1ea86345a2860707718c8"),
				new ObjectId("62b1ea86345a2860707718c9"));
		ordersList = Arrays.asList(new ObjectId("62b1fd1a6238053d15580b20"));
		
		hotels.add(new Hotel(new ObjectId("62b1e395c5ae613f0133e12a"), "Hermoso Hotel", address, 5, roomList, 55f, ordersList));
		
		address = new Address("Funete", 66, "Higueruela" ,"Italy");
		roomList = Arrays.asList(
				new ObjectId("62b1ea86345a2860707718ca"),
				new ObjectId("62b1ea86345a2860707718cb"),
				new ObjectId("62b1ea86345a2860707718cc"));
		ordersList = Arrays.asList(new ObjectId("62b1fd1a6238053d15580b21"));
		
		hotels.add(new Hotel(new ObjectId("62b1ea87345a2860707718cf"), "Lindo Hotel", address, 8, roomList, 90f, ordersList));
		
		
		address = new Address("Essex", 20, "Tannach" ,"UK");
		roomList = Arrays.asList(
				new ObjectId("62b1ea86345a2860707718cd"),
				new ObjectId("62b1ea86345a2860707718ce"));
		ordersList = null;
		
		hotels.add(new Hotel(new ObjectId("62b1ea87345a2860707718d0"), "Bello Hotel", address, 10, roomList, 115f, ordersList));
		
		InsertManyResult result = hotelCollection.insertMany(hotels);
		System.out.println("Hotel seed > " + result.wasAcknowledged());
		
	}
	
	public static void seedCustomers(MongoDBConnection connection) {
		MongoCollection<Customer> customerCollection = connection.getDataBase().getCollection("customer", Customer.class);
		customerCollection.drop();
		
		List<Customer> customers = new ArrayList<Customer>();
		List<ObjectId> ordersList = new ArrayList<ObjectId>();
		
		ordersList = Arrays.asList(new ObjectId("62b1fd1a6238053d15580b20"));
		customers.add(new Customer(new ObjectId("62b1ea87345a2860707718d1"), 1000, "Lea", "Chan", "UK", ordersList));
		
		ordersList = Arrays.asList(new ObjectId("62b1fd1a6238053d15580b21"));
		customers.add(new Customer(new ObjectId("62b1ea87345a2860707718d2"), 1001, "Eden", "Mendez", "Spain", ordersList));
		
		ordersList = null;
		customers.add(new Customer(new ObjectId("62b1ea87345a2860707718d3"), 1003, "Salim", "Ali", "Lebanon", ordersList));
		customers.add(new Customer(new ObjectId("62b1ea87345a2860707718d4"), 1004, "Jose", "Tate", "US", ordersList));
		customers.add(new Customer(new ObjectId("62b1ea87345a2860707718d5"), 1005, "Lorena", "Huff", "New Zealand", ordersList));
		
		InsertManyResult result = customerCollection.insertMany(customers);
		System.out.println("Customer seed > " + result.wasAcknowledged());
	}
	
	public static void seedOrders(MongoDBConnection connection) {
		MongoCollection<Order> orderCollection = connection.getDataBase().getCollection("order", Order.class);
		orderCollection.drop();
		
		List<Order> orders = new ArrayList<Order>();
		//lea chan orders
		
		ObjectId customerId;
		ObjectId hotelId;
		ObjectId orderId;
		ObjectId roomId = null;
		
		orderId = new ObjectId("62b1fd1a6238053d15580b20");
		customerId = new ObjectId("62b1e395c5ae613f0133e12a");
		hotelId = new ObjectId("62b1e395c5ae613f0133e12a");
		roomId = new ObjectId("62b1ea86345a2860707718c6");
		orders.add(new Order( orderId, hotelId, roomId, customerId, LocalDate.now(), LocalDate.of(2022, 9, 1), 5, 2000 ));
		
		orderId = new ObjectId("62b1fd1a6238053d15580b21");
		customerId = new ObjectId("62b1ea87345a2860707718d2");
		hotelId = new ObjectId("62b1ea87345a2860707718cf");
		roomId = new ObjectId("62b1ea86345a2860707718ca");
		orders.add(new Order( orderId, hotelId, roomId, customerId, LocalDate.now(), LocalDate.of(2022, 9, 15), 5, 2000 ));
		
		InsertManyResult result = orderCollection.insertMany(orders);
		System.out.println("Orders seed > " + result.wasAcknowledged());
	}
}
