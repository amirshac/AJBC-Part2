package models;

import java.sql.Date;

public class Item {
	protected int itemID;
	protected String name;
	protected float unitPrice;
	protected Date purchaseDate;
	protected int quantity;
	
	public Item() {}
	
	public Item(String name, float unitPrice, Date purchaseDate, int quantity) {
		this.name = name;
		this.purchaseDate = purchaseDate;
		this.unitPrice = unitPrice;
		this.quantity = quantity;
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

	
}
