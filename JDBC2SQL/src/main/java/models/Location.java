package models;

import java.util.Objects;

public class Location {
	protected int locationID;
	protected String name;
	protected String accessCode;
	
	public Location(int locationID, String name, String accessCode) {
		setLocationID(locationID);
		setName(name);
		setAccessCode(accessCode);
	}

	public Location() {}
	
	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessCode() {
		return accessCode;
	}

	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	@Override
	public String toString() {
		return "Location [locationID=" + locationID + ", name=" + name + ", accessCode=" + accessCode + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(accessCode, locationID, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(accessCode, other.accessCode) && locationID == other.locationID
				&& Objects.equals(name, other.name);
	}
	
	
}
