package ajbc.learn.mongodb.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Movies {

	private ObjectId id;
	private String title;
	private int year;

	@BsonProperty(value = "num_mflix_comments")
	private int numComments;

	private List<String> cast;

	public Movies(ObjectId id, String title, int year, int numComments, List<String> cast) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.numComments = numComments;
		this.cast = cast;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	public List<String> getCast() {
		return cast;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	@Override
	public String toString() {
		return "Movies [id=" + id + ", title=" + title + ", year=" + year + ", numComments=" + numComments + ", cast="
				+ cast + "]";
	}

}
