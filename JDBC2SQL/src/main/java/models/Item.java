package models;

import java.sql.Date;
import java.util.Objects;

public class Item {
	protected int itemID;
	protected String name;
	protected float unitPrice;
	protected Date purchaseDate;
	protected int quantity;
	
	public Item() {}
	
	public Item(int itemID, String name, float unitPrice, Date purchaseDate, int quantity) {
		setItemID(itemID);
		setName(name);
		setPurchaseDate(purchaseDate);
		setUnitPrice(unitPrice);
		setQuantity(quantity);
	}
	
	public int getItemID() {
		return itemID;
	}

	public void setItemID(int itemID) {
		this.itemID = itemID;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	public float getUnitPrice() {
		return unitPrice;
	}
	
	public void setUnitPrice(float unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [itemID=" + itemID + ", name=" + name + ", unitPrice=" + unitPrice + ", purchaseDate="
				+ purchaseDate + ", quantity=" + quantity + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(itemID, name, purchaseDate, quantity, unitPrice);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return itemID == other.itemID && Objects.equals(name, other.name)
				&& Objects.equals(purchaseDate, other.purchaseDate) && quantity == other.quantity
				&& Float.floatToIntBits(unitPrice) == Float.floatToIntBits(other.unitPrice);
	}
	
}
