package dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.ItemLocation;

public class ItemLocationDBService {
	
	public ItemLocation getItemLocation(Connection connection, ItemLocation itemLocation) {
		ItemLocation il = null;
		ResultSet resultSet = null;
		
		String query = "select * from ItemLocation where ItemID = ? AND LocationID = ?";
		
		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, itemLocation.getItemID());
			statement.setInt(2, itemLocation.getLocationID());
			
			resultSet = statement.executeQuery();
			
			if (resultSet.next()) {
				il = new ItemLocation();
				il.setItemID(resultSet.getInt(1));
				il.setLocationID(resultSet.getInt(2));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			il = null;
		}
		return il;
	}
	
	public ItemLocation getItemLocation(Connection connection, int itemID, int locationID) {
		return getItemLocation(connection, new ItemLocation(itemID, locationID));
	}
	
	public ItemLocation insertItemLocation(Connection connection, ItemLocation il) {
		String query = "Insert into ItemLocation Values(?, ?)";
		int affectedRows = 0;
		ItemLocation newIL = null;
		
		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, il.getItemID());
			statement.setInt(2, il.getLocationID());
			
			affectedRows = statement.executeUpdate();
			
			System.out.println("insert> affected rows " + affectedRows);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		newIL = getItemLocation(connection, il.getItemID(), il.getLocationID());
		return newIL;
	}
	
	public ItemLocation insertItemLocation(Connection connection, int itemID, int locationID) {
		return insertItemLocation(connection, new ItemLocation(itemID, locationID));
	}
	
	public ItemLocation deleteItemLocation(Connection connection, ItemLocation itemLocation) {
		ItemLocation il = getItemLocation(connection, itemLocation);
		
		// if we didn't find an item, we won't delete
		if (il == null)
			return null;
		
		String query = "delete from ItemLocation where itemID = ? and locationID = ?";
		int affectedRows = 0;
		

		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, itemLocation.getItemID());
			statement.setInt(2, itemLocation.getLocationID());

			affectedRows = statement.executeUpdate();

			System.out.println("delete> affected rows " + affectedRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return il;
	}
	
	public ItemLocation updateItemLocation(Connection connection, ItemLocation itemLocation, ItemLocation toUpdate) {
		if (itemLocation == null) return null;
		
		ItemLocation itemInDB = getItemLocation(connection, itemLocation);

		if (itemInDB == null) {
			System.out.println("update> no item in DB");
			return null;
		}

		if (itemInDB.equals(toUpdate)) {
			System.out.println("update> no need to update item - same item!");
			return null;
		}

		String query = "update ItemLocation set itemID = ?, locationID = ? WHERE itemID = ? and locationID = ?";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, itemLocation.getItemID());
			statement.setInt(2, itemLocation.getLocationID());
			statement.setInt(3, toUpdate.getItemID());
			statement.setInt(4, toUpdate.getLocationID());
			
			affectedRows = statement.executeUpdate();
			
			System.out.println("update> affected rows " + affectedRows);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		itemInDB = getItemLocation(connection, toUpdate);
		return itemInDB;
	}
	
	
	
}
