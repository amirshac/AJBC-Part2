package models;

import java.util.Objects;

public class ItemLocation {
	
	protected int itemID;
	protected int locationID;
	
	public ItemLocation() {}
	
	public ItemLocation(int itemID, int locationID) {
		this.itemID = itemID;
		this.locationID = locationID;
	}

	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public int getLocationID() {
		return locationID;
	}

	public void setLocationID(int locationID) {
		this.locationID = locationID;
	}

	@Override
	public String toString() {
		return "ItemLocation [itemID=" + itemID + ", locationID=" + locationID + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemID, locationID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemLocation other = (ItemLocation) obj;
		return itemID == other.itemID && locationID == other.locationID;
	}
	
	
}
