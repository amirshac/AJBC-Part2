package ajbc.learn.mongodb.daos;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Chair;
import ajbc.learn.nosql.MongoDBConnection;

public class ChairDAO {
	
	protected MongoDBConnection connection;
	protected Gson gson;
	
	public ChairDAO() {
		gson = new Gson();
	}
	
	public ChairDAO(MongoDBConnection connection) {
		this.connection = connection;
		gson = new Gson();
	}
	
	public boolean insert(MongoDBConnection connection, Chair chair) {
		MongoCollection<Document> collection = connection.getCollection();
		
		String chairJson = gson.toJson(chair);
		
		Document chairDoc = Document.parse(chairJson);
		
		InsertOneResult result = collection.insertOne(chairDoc);
		
		return result.wasAcknowledged();
	}
	
	public boolean insertMany(MongoDBConnection connection, List<Chair> chairs) {
		MongoCollection<Document> collection = connection.getCollection();
		
		List<Document> chairDocs = new ArrayList<Document>();
		
		chairs.forEach(chair -> {
			String chairJson = gson.toJson(chair);
			Document chairDoc = Document.parse(chairJson);
			chairDocs.add(chairDoc);
		});
		
		InsertManyResult results = collection.insertMany(chairDocs);
		
		return results.wasAcknowledged();
	}
	
	public boolean delete(MongoDBConnection connection, Bson filter) {
		MongoCollection<Document> collection = connection.getCollection();
		DeleteResult result = collection.deleteMany(filter);
		return result.wasAcknowledged();
		
	}

}
