package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.nosql.MyConnectionString;


public class Basics {
	
	public static void main(String[] args) {
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .serverApi(ServerApi.builder()
		            .version(ServerApiVersion.V1)
		            .build())
		        .build();
		try(MongoClient mongoClient = MongoClients.create(settings)){
			
			//print databases
			mongoClient.listDatabaseNames().forEach(System.out::println);
			
			final String DB_NAME = "my_own_db", COLLECTION = "myCollection";
			MongoDatabase database = mongoClient.getDatabase(DB_NAME);
			MongoCollection<Document> myCollection = database.getCollection(COLLECTION);
			
			Document studentDoc = createStudentDoc(1, 2, "Amir", "Shachar");
			
			InsertOneResult insertResult = myCollection.insertOne(studentDoc);
			System.out.println(insertResult.wasAcknowledged());

			//print databases
			System.out.println("---------------After DB and document creation--------------");
			mongoClient.listDatabaseNames().forEach(System.out::println);

		}		
	}
	
	public static Document createStudentDoc(int studentID, int classID, String firstName, String lastName) {
		Document studentDoc = new Document("student_id", studentID).append("class_id", classID)
				.append("first_name", firstName).append("last_name", lastName);
		
		List<Document> scores = new ArrayList<Document>();
		Document score;
		
		scores.add( new Document("topic", "java").append("score", 99) );		
		scores.add( new Document("topic", "english").append("score", 80) );
		scores.add( new Document("topic", "math").append("score", 65) );

		studentDoc.append("exams", scores);
		
		return studentDoc;
	}
}
