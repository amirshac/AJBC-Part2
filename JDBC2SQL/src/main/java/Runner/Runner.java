package Runner;


import java.sql.Connection;
import java.sql.SQLException;

import DBConnection.DBConnection;
import dbservices.ItemDBService;
import dbservices.LocationDBService;
import models.Item;
import models.Location;

public class Runner {

	private static final String configFileName = "demo.properties";
	private static DBConnection db = null;
	private static Connection connection = null;
	private static ItemDBService itemDB = new ItemDBService();
	private static LocationDBService locationDB = new LocationDBService();
	
	
	public static void connect() {
		db = new DBConnection(configFileName);
		
		try {
			connection = db.connect();
			System.out.println("connected to db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void testGetItem() {
		Item item = itemDB.getItem(connection, 1000);
		System.out.println(item);	
	}
	
	public static void testInsertItem() {
		Item item = new Item(10, "bissli", 20f, java.sql.Date.valueOf("2021-01-02") , 9);
		Item newItem = itemDB.insertItem(connection, item);
		
		System.out.println(newItem);
		
	}
	
	public static void testDeleteItem() {
		Item item = itemDB.deleteItem(connection, 1004);
		System.out.println("deleted " + item);
	}
	
	public static void testUpdateItem() {
		Item item = itemDB.getItem(connection, 1003);
		item.setName("PowerMagnets");
		item = itemDB.UpdateItem(connection, item);
		
		System.out.println(item);		
	}
	
	public static void testGetLocation() {
		Location location = locationDB.getLocation(connection, 1000);
		System.out.println(location);	
	}
	
	public static void testInsertLocation() {
		Location location = new Location(10, "Ibiza", "IBI433");
		Location newLocation = locationDB.insertLocation(connection, location);
		System.out.println(newLocation);
	}
	
	public static void testDeleteLocation() {
		Location location = locationDB.deleteLocation(connection, 1004);
		System.out.println(location);
	}
	
	public static void testUpdateLocation() {
		Location location = locationDB.getLocation(connection, 1003);
		location.setAccessCode("boop332");
		location = locationDB.UpdateLocation(connection, location);
		
		System.out.println(location);
	}
	
	public static void main(String[] args) throws SQLException {
		
		connect();
		
		//testGetItem();
		//testInsertItem();
		//testDeleteItem();
		//testUpdateItem();
		
		//testGetLocation();
		//testInsertLocation();
		//testDeleteLocation();
		testUpdateLocation();
		
		
		connection.close();
	}
	
}
