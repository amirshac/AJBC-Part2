package ajbc.learn.nosql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonWriterSettings;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

import ajbc.learn.mongodb.models.Movies;

import static com.mongodb.client.model.Projections.*;

public class moviesRunner {

	public static void main(String[] args) {
		MongoDBConnection connection = new MongoDBConnection();
		connection.connect("nosql.properties", "sample_mflix", "movies");
		
		//MongoCollection<Movies> moviesCollection = connection.getDataBase().getCollection("movies", Movies.class);
		//List<Movies> movies = new ArrayList<Movies>();
		
		//List<Document> results = numberOfMoviesInEachYear(connection);
		//results.forEach(printDocument());
		
		List<Document> results = movieTitleWithEachComment(connection);
		results.forEach(printDocument());
		
		connection.close();
	}

	private static List<Document> movieTitleWithEachComment(MongoDBConnection connection) {
		// number of movies in each year in descending order
		MongoCollection<Document> movies = connection.getDataBase().getCollection("movies");
		MongoCollection<Document> comments = connection.getDataBase().getCollection("comments");
		
		Bson join = Aggregates.lookup("movies", "movie_id", "_id", "movie_of_comment");
		Bson filter = match(Filters.gte("date", dateFilter));
		Bson limit = Aggregates.limit(1);
		Bson project = Aggregates.project(fields( excludeId(), include("text"), include("title")));
		//List<Document> results = comments.aggregate(Arrays.asList(join, limit, project)).into(new ArrayList<>());
		List<Document> results = comments.aggregate(Arrays.asList(join, limit)).into(new ArrayList<>());
		
//		Bson match = Aggregates.match(Filters.eq("type", "movie"));
//		Bson group = Aggregates.group("$year", Accumulators.sum("movies_in_year", 1));
//		Bson project = Aggregates.project(Projections.fields( Projections.excludeId(), Projections.computed("year", "$_id"), Projections.include("movies_in_year") ));
//		Bson sort = Aggregates.sort(Sorts.descending("movies_in_year"));
		
		return results;
	}
	
	private static List<Document> numberOfMoviesInEachYear(MongoDBConnection connection) {
		// number of movies in each year in descending order
		MongoCollection<Document> movies = connection.getDataBase().getCollection("movies");
		Bson match = Aggregates.match(Filters.eq("type", "movie"));
		Bson group = Aggregates.group("$year", Accumulators.sum("movies_in_year", 1));
		Bson project = Aggregates.project(fields( excludeId(), computed("year", "$_id"), include("movies_in_year") ));
		Bson sort = Aggregates.sort(Sorts.descending("movies_in_year"));
		Bson limit = Aggregates.limit(10);
		List<Document> results = movies.aggregate(Arrays.asList(match, group, project,sort,limit)).into(new ArrayList<>());
		return results;
	}

	private static Consumer<Document> printDocument() {
		return doc -> System.out.println(doc.toJson(JsonWriterSettings.builder().indent(true).build()));
	}
}
