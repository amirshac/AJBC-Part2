package hotel.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertManyResult;

import hotel.models.*;

public class SeedDB {
	
	public static void seedRooms(MongoDBConnection connection) {
		
		MongoCollection<Room> roomCollection = connection.getDataBase().getCollection("room", Room.class);
		roomCollection.drop();
		
		List<Room> rooms = new ArrayList<Room>();
		
		//int hotelId, int roomId, int number, boolean hasBath, int maxOccupants)
		rooms.add(new Room(1, 11, 100, false, 2));
		rooms.add(new Room(1, 12, 101, false, 2));
		rooms.add(new Room(1, 13, 102, true, 2));
		rooms.add(new Room(1, 14, 103, true, 2));
		
		
		rooms.add(new Room(2, 21, 200, false, 4));
		rooms.add(new Room(2, 22, 201, true, 4));
		rooms.add(new Room(2, 23, 203, true, 4));
		
		rooms.add(new Room(3, 31, 300, false, 3));
		rooms.add(new Room(3, 32, 301, true, 3));
		
		InsertManyResult result = roomCollection.insertMany(rooms);
		System.out.println("Room seed > " + result.wasAcknowledged());
		
	}
	
	public static void seedHotels(MongoDBConnection connection) {
		
		MongoCollection<Hotel> hotelCollection = connection.getDataBase().getCollection("hotel", Hotel.class);
		hotelCollection.drop();

		List<Hotel> hotels = new ArrayList<Hotel>();
		Address address = null;
		List<Integer> roomList = new ArrayList<Integer>();
		List<Integer> ordersList = new ArrayList<Integer>();
		
		//hotel - int hotelId, String name, Address address, int rank, List<Integer> rooms, float pricePerNight, List<Integer> orders
		//address - (String street, int number, String city, String country) 
		address = new Address("Hayarkon", 57, "Tel-Aviv","Israel");
		roomList = Arrays.asList(11,12,13,14);
		hotels.add(new Hotel(1, "Hermoso Hotel", address, 5, roomList, 55f, ordersList));
		
		address = new Address("Funete", 66, "Higueruela" ,"Italy");
		roomList = Arrays.asList(21,22,23);
		hotels.add(new Hotel(2, "Lindo Hotel", address, 8, roomList, 90f, ordersList));
		
		address = new Address("Essex", 20, "Tannach" ,"UK");
		roomList = Arrays.asList(31,32);
		hotels.add(new Hotel(3, "Bello Hotel", address, 10, roomList, 115f, ordersList));
		
		InsertManyResult result = hotelCollection.insertMany(hotels);
		System.out.println("Hotel seed > " + result.wasAcknowledged());
		
	}
	
	public static void seedCustomers(MongoDBConnection connection) {
		MongoCollection<Customer> customerCollection = connection.getDataBase().getCollection("customer", Customer.class);
		customerCollection.drop();
		
		List<Customer> customers = new ArrayList<Customer>();
		List<Integer> ordersList = new ArrayList<Integer>();
		
		customers.add(new Customer(1000, "Lea", "Chan", "UK", ordersList));
		customers.add(new Customer(1001, "Eden", "Mendez", "Spain", ordersList));
		customers.add(new Customer(1003, "Salim", "Ali", "Lebanon", ordersList));
		customers.add(new Customer(1004, "Jose", "Tate", "US", ordersList));
		customers.add(new Customer(1005, "Lorena", "Huff", "New Zealand", ordersList));
		
		InsertManyResult result = customerCollection.insertMany(customers);
		System.out.println("Customer seed > " + result.wasAcknowledged());
		
	}
	
	public static void seedOrders(MongoDBConnection connection) {
		MongoCollection<Order> orderCollection = connection.getDataBase().getCollection("order", Order.class);
		orderCollection.drop();
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order( 1, 1000, LocalDate.now(), LocalDate.of(2022, 9, 1), 5, 2000 ));
		orders.add(new Order( 2, 1000, LocalDate.now(), LocalDate.of(2022, 9, 15), 5, 2000 ));
	}
}
