package ajbc.learn.nosql;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import ajbc.learn.mongodb.daos.ChairDAO;
import ajbc.learn.mongodb.models.Chair;
import ajbc.learn.mongodb.models.Measurement;

public class TestRunner {
	
	static MongoDBConnection connection;
	static ChairDAO chairDAO;
	
	public static void testInsert() {
		Chair chair = new Chair("Oaks", "o-107", true, 43.5f, new Measurement(10,20,30));		
		Boolean result = chairDAO.insert(connection, chair);
		System.out.println("insert one - " + result);
	}
	
	public static void testInsertMany() {
		List<Chair> chairs = new ArrayList<Chair>();
		chairs.add(new Chair("OakLand", "o-103", true, 41.5f, new Measurement(10,20,30)));
		chairs.add(new Chair("Chairdom", "o-99", true, 23.7f, new Measurement(40,10,40)));
		chairs.add(new Chair("OakLand", "o-105", true, 42f, new Measurement(10,20,30)));
		
		Boolean result = chairDAO.insertMany(connection, chairs);
		System.out.println("insert many - " + result);
		
	}
	
	public static void testDelete() {
		Bson filter = Filters.eq("manufacturer", "OakLand");
		Boolean result = chairDAO.delete(connection, filter);
		
		System.out.println("delete - " + result);
	}
	
	public static void main(String[] args) {
		connection = new MongoDBConnection();
		chairDAO = new ChairDAO();
		
		connection.connect("nosql.properties", "Furniture", "Chairs");
		
		//testInsert();
		
		//testInsertMany();
		
		testDelete();
		
		connection.close();
	
	}
}
