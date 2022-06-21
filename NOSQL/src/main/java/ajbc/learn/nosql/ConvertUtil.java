package ajbc.learn.nosql;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ConvertUtil {

	static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public static String documentToJson(Document document) {
		return gson.toJson(document);
	}
	
	public static Document jsonToDocument(String jsonString) {
		return Document.parse(jsonString);
	}
}
