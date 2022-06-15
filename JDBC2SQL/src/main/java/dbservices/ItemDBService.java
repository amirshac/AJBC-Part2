package dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

	protected List<Item> parseItemsFromResultSet(ResultSet resultSet) {
		List<Item> items = new ArrayList<Item>();

		try {
			while (resultSet.next()) {
				Item item = new Item();
				item.setItemID(resultSet.getInt(1));
				item.setName(resultSet.getString(2));
				item.setUnitPrice(resultSet.getFloat(3));
				item.setPurchaseDate(resultSet.getDate(4));
				item.setQuantity(resultSet.getInt(5));
				
				items.add(item);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

		return items;
	}
	
	public Item getItem(Connection connection, int itemID) {
		Item item = null;

		ResultSet resultSet = null;
		String query = "select * from Item where ItemID = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)) {
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

		try (PreparedStatement statement = connection.prepareStatement(query)) {

			resultSet = statement.executeQuery();

			item = parseItemFromResultSet(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			item = null;
		}

		return item;
	}

	public List<Item> getTopLatestItems(Connection connection, int top) {
		List<Item> items = null;
		ResultSet resultSet = null;
		String query = "select TOP (?) * FROM Item ORDER BY ItemID DESC";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, top);
			resultSet = statement.executeQuery();

			items = parseItemsFromResultSet(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		return items;
	}

	public Item insertItem(Connection connection, Item item) {
		String query = "Insert into Item Values(?, ?, ?, ?)";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, item.getName());
			statement.setFloat(2, item.getUnitPrice());
			statement.setDate(3, item.getPurchaseDate());
			statement.setInt(4, item.getQuantity());

			affectedRows = statement.executeUpdate();
			connection.commit();

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

		// step 1 - delete from itemlocation by item
		ItemLocationDBService ilDB = new ItemLocationDBService();
		ilDB.deleteAllByItem(connection, itemID);

		// step 2- delete item from DB
		String query = "delete from Item where itemID = ?";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setInt(1, itemID);

			affectedRows = statement.executeUpdate();
			connection.commit();

			System.out.println("delete> affected rows " + affectedRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return item;
	}

	public Item updateItem(Connection connection, Item item) {
		if (item == null)
			return null;

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
			connection.commit();

			System.out.println("update> affected rows " + affectedRows);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		itemInDB = getItem(connection, item.getItemID());
		return itemInDB;
	}

	public List<Item> insertItemList(Connection connection, List<Item> itemList) {
		if ( itemList == null || itemList.isEmpty() ) return null;
		
		String query = "Insert into Item Values(?, ?, ?, ?)";

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			for (Item item : itemList) {
				statement.setString(1, item.getName());
				statement.setFloat(2, item.getUnitPrice());
				statement.setDate(3, item.getPurchaseDate());
				statement.setInt(4, item.getQuantity());

				statement.addBatch();
			}

			int[] affectedRows = statement.executeBatch();
			for (int i : affectedRows) {
				if (i == 0) {
					throw new SQLException();
				}
			}
			connection.commit();

		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}

		List<Item> itemsInDB = getTopLatestItems(connection, itemList.size());
		return itemsInDB;
	}
}
