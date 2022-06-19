package ajbc.learn.mongodb.models;

import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

public class Measurement {
	@SerializedName("_id")
	private ObjectId id;
	@SerializedName("height")
	private double height;
	@SerializedName("width")
	private double width;
	@SerializedName("depth")
	private double depth;
	
	public Measurement() {}
	
	public Measurement(ObjectId id, double height, double width, double depth) {
		this.id = id;
		this.height = height;
		this.width = width;
		this.depth = depth;
	}
	
	public Measurement(double height, double width, double depth) {
		this.height = height;
		this.width = width;
		this.depth = depth;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "Measurement [id=" + id + ", height=" + height + ", width=" + width + ", depth=" + depth + "]";
	}
	
}
