package ajbc.learn.mongodb.models;

import org.bson.types.ObjectId;
import com.google.gson.annotations.SerializedName;

public class Chair {
	@SerializedName("_id")
	private ObjectId id;
	@SerializedName("manufacturer")
	private String manufacturer;
	@SerializedName("model_name")
	private String modelName;
	@SerializedName("is_stool")
	private boolean isStool;
	@SerializedName("price")
	private float price;
	@SerializedName("measurement")
	private Measurement measurement;
	
	public Chair() {}

	public Chair(ObjectId id, String manufacturer, String modelName, boolean isStool, float price, Measurement measurement) {
		this.id = id;
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.isStool = isStool;
		this.price = price;
		this.measurement = measurement;
	}
	
	public Chair(String manufacturer, String modelName, boolean isStool, float price, Measurement measurement) {
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.isStool = isStool;
		this.price = price;
		this.measurement = measurement;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public boolean isStool() {
		return isStool;
	}

	public void setStool(boolean isStool) {
		this.isStool = isStool;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

	@Override
	public String toString() {
		return "Chair [id=" + id + ", manufacturer=" + manufacturer + ", modelName=" + modelName + ", isStool="
				+ isStool + ", price=" + price + ", measurement=" + measurement + "]";
	}
	
}
