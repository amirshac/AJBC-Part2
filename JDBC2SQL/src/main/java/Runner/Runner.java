package Runner;


import java.sql.Connection;
import java.sql.SQLException;

import DBConnection.DBConnection;
import dbservices.ItemDBService;
import models.Item;

public class Runner {

	private static final String configFileName = "demo.properties";
	private static DBConnection db = null;
	private static Connection connection = null;
	private static ItemDBService itemDB = new ItemDBService();
	
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
		Item item = new Item(10, "Magnet", 9.5f, java.sql.Date.valueOf("2022-01-02") , 7);
		itemDB.insertItem(connection, item);
		
		System.out.println(item);
		
	}
	
	public static void testDeleteItem() {
		Item item = itemDB.deleteItem(connection, 1004);
		System.out.println("deleted " + item);
	}
	
	public static void testUpdateItem() {
		Item item = itemDB.getItem(connection, 1003);
		itemDB.UpdateItem(connection, item);
			
		System.out.println(item);
	}
	
	public static void main(String[] args) throws SQLException {
		
		connect();
		
		//testGetItem();
		//testInsertItem();
		//testDeleteItem();
		testUpdateItem();
		
		connection.close();
	}
	
}
