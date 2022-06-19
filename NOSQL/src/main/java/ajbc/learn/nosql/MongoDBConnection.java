package ajbc.learn.nosql;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

	protected ConnectionString connectionString;
	protected MongoClientSettings settings;
	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Document> collection;
	
	public MongoDBConnection() {

	}
	
	public MongoClient getClient() {
		return mongoClient;
	}
	
	
	public MongoDatabase getDataBase() {
		return database;
	}
	
	public MongoDatabase getDataBase(String dbName) {
		database = mongoClient.getDatabase(dbName);
		return database;
	}
	
	public MongoCollection<Document> getCollection(){
		return collection;
	}
	
	public MongoCollection<Document> getCollection(String collectionName){
		collection = database.getCollection(collectionName);
		return collection;
	}
		
	public MongoClient connect(String propsFilename, String dbName, String collectionName) {
		connectionString = MyConnectionString.uri(propsFilename);
		
		settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .serverApi(ServerApi.builder()
		            .version(ServerApiVersion.V1)
		            .build())
		        .build();
		
		try {
			mongoClient = MongoClients.create(settings);
		} catch (Exception e) {
			return null;
		}
		
		database = mongoClient.getDatabase(dbName);
		collection = database.getCollection(collectionName);
		
		return mongoClient;
	}
	
	public void close() {
		if (mongoClient!=null)
			mongoClient.close();
	}
}
