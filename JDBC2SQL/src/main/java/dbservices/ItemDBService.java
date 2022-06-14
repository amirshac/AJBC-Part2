package dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import models.Item;

import java.sql.ResultSet;

public class ItemDBService {
	
	public Item getItem(Connection connection, int itemID) {
		
		Item item = null;
		
		ResultSet resultSet = null;
		String query = "select * from Item where ItemID = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, itemID);
			resultSet = statement.executeQuery();
			
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
}
