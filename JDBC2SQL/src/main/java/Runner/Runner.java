package Runner;


import java.sql.Connection;
import java.sql.SQLException;

import DBConnection.DBConnection;
import dbservices.ItemDBService;
import models.Item;

public class Runner {

	private static final String configFileName = "demo.properties";
	
	
	public static void main(String[] args) throws SQLException {
		Connection connection = null;
		DBConnection db = new DBConnection(configFileName);
		ItemDBService itemDB = new ItemDBService();
		
		try {
			connection = db.connect();
			System.out.println("connected to db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Item item = itemDB.getItem(connection, 1000);
		System.out.println(item);
		
		connection.close();
	}
	
}
