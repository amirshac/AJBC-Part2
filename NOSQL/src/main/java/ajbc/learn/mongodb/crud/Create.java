package ajbc.learn.mongodb.crud;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.InsertOneResult;

import ajbc.learn.mongodb.models.Exam;
import ajbc.learn.mongodb.models.Student;
import ajbc.learn.nosql.MyConnectionString;

public class Create {
	public static void main(String[] args) {
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder()
		        .applyConnectionString(connectionString)
		        .serverApi(ServerApi.builder()
		            .version(ServerApiVersion.V1)
		            .build())
		        .build();
		try(MongoClient mongoClient = MongoClients.create(settings)){
			
			final String DB_NAME = "my_own_db", COLLECTION = "myCollection";
			MongoDatabase database = mongoClient.getDatabase(DB_NAME);
			MongoCollection<Document> myCollection = database.getCollection(COLLECTION);
			
			Document studentDoc = Basics.createStudentDoc(1, 2, "Amir", "Shachar");
			
			InsertOneResult insertResult = myCollection.insertOne(studentDoc);
			System.out.println(insertResult.wasAcknowledged());
			
			
			// create POJO
			List<Exam> exams = new ArrayList<Exam>();
			exams.add(new Exam("Java", 80));
			exams.add(new Exam("Math", 54));
			Student student = new Student(1234, 5678, "Moshe", "Ufnik", exams);
			
			// convert to JSON
			Gson gson = new Gson();
			String studentJson = gson.toJson(student);
			System.out.println(studentJson);
			
			Document studentDoc1 = Document.parse(studentJson);
			myCollection.insertOne(studentDoc1);
			
			// insert many
			
			Student student2 = new Student(222, 538, "Miki", "Katz", exams);
			Student student3 = new Student(333, 638, "Shimi", "Tavori", exams);
		
			String studentJson2 = gson.toJson(student2);
			String studentJson3 = gson.toJson(student3);
			
			Document studentDoc2 = Document.parse(studentJson2);
			Document studentDoc3 = Document.parse(studentJson3);
			
			List<Document> studentDocs = new ArrayList<>();
			studentDocs.add(studentDoc2);
			studentDocs.add(studentDoc3);
		
			InsertManyResult manyResult = myCollection.insertMany(studentDocs);
			boolean ack = manyResult.wasAcknowledged();
			System.out.println(ack);
		}		
	}
}
