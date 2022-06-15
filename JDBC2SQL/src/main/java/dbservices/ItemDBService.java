package dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import models.Item;

public class ItemDBService {

	protected Item parseItemFromResultSet(ResultSet resultSet) {
		Item item = null;
		
		try {
			if (resultSet.next()) {
				item = new Item();
				item.setItemID(resultSet.getInt(1));
				item.setName(resultSet.getString(2));
				item.setUnitPrice(resultSet.getFloat(3));
				item.setPurchaseDate(resultSet.getDate(4));
				item.setQuantity(resultSet.getInt(5));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			item = null;
		}
		
		return item;
	}
	
	public Item getItem(Connection connection, int itemID) {
		Item item = null;

		ResultSet resultSet = null;
		String query = "select * from Item where ItemID = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, itemID);

			resultSet = statement.executeQuery();

			item = parseItemFromResultSet(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			item = null;
		}

		return item;
	}
	
	public Item getLatestItem(Connection connection) {
		Item item = null;
		
		ResultSet resultSet = null;
		String query = "select TOP 1 * FROM Item ORDER BY ItemID DESC";
		
		try (PreparedStatement statement = connection.prepareStatement(query)){
			
			resultSet = statement.executeQuery();
			
			item = parseItemFromResultSet(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
			item = null;
		}
		
		return item;
	}

	public Item insertItem(Connection connection, Item item) {
		String query = "Insert into Item Values(?, ?, ?, ?)";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1, item.getName());
			statement.setFloat(2, item.getUnitPrice());
			statement.setDate(3, item.getPurchaseDate());
			statement.setInt(4, item.getQuantity());

			affectedRows = statement.executeUpdate();

			System.out.println("insert> affected rows " + affectedRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Item itemInDB = getLatestItem(connection);
		return itemInDB;
	}

	public Item deleteItem(Connection connection, int itemID) {
		Item item = getItem(connection, itemID);

		// if we didn't find an item, we won't delete
		if (item == null)
			return null;

		String query = "delete from Item where itemID = ?";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, itemID);

			affectedRows = statement.executeUpdate();

			System.out.println("delete> affected rows " + affectedRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return item;
	}

	public Item updateItem(Connection connection, Item item) {
		if (item == null) return null;
		
		Item itemInDB = getItem(connection, item.getItemID());

		if (itemInDB == null) {
			System.out.println("update> no item in DB");
			return null;
		}

		if (itemInDB.equals(item)) {
			System.out.println("update> no need to update item - same item!");
			return null;
		}

		String query = "update Item set Name = ?, Unit_Price = ?, Purchase_Date = ?, Quantity = ? WHERE itemID = ?";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, item.getName());
			statement.setFloat(2, item.getUnitPrice());
			statement.setDate(3, item.getPurchaseDate());
			statement.setInt(4, item.getQuantity());
			statement.setInt(5, item.getItemID());
			
			affectedRows = statement.executeUpdate();
			
			System.out.println("update> affected rows " + affectedRows);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		itemInDB = getItem(connection, item.getItemID());
		return itemInDB;
	}
	
}
