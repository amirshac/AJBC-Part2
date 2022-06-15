package dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.Location;

public class LocationDBService {
	
	protected Location parseLocationFromResultSet(ResultSet resultSet) {
		Location location = null;
		
		try {
			if (resultSet.next()) {
				location = new Location();
				location.setLocationID(resultSet.getInt(1));
				location.setName(resultSet.getString(2));
				location.setAccessCode(resultSet.getString(3));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			location = null;
		}	
		return location;	
	}
	
	public Location getLocation(Connection connection, int locationID) {

		Location location = null;

		ResultSet resultSet = null;
		String query = "select * from Location where LocationID = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, locationID);

			resultSet = statement.executeQuery();

			location = parseLocationFromResultSet(resultSet);

		} catch (SQLException e) {
			e.printStackTrace();
			location = null;
		}

		return location;
	}
	
	public Location getLatestLocation(Connection connection) {
		Location location = null;
		ResultSet resultSet = null;
		String query = "select TOP 1 * FROM Location ORDER BY LocationID DESC";
		try (PreparedStatement statement = connection.prepareStatement(query)){
			
			resultSet = statement.executeQuery();
			
			location = parseLocationFromResultSet(resultSet);
			
		} catch (SQLException e) {
			e.printStackTrace();
			location = null;
		}
		
		return location;
	}
	
	public Location insertLocation(Connection connection, Location location) {
		String query = "Insert into Location Values(?, ?)";
		int affectedRows = 0;
		Location newLocation = null;
		
		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setString(1, location.getName());
			statement.setString(2, location.getAccessCode());

			affectedRows = statement.executeUpdate();

			System.out.println("insert> affected rows " + affectedRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			newLocation = null;
		}
		
		newLocation = getLatestLocation(connection);
		return newLocation;
	}
	
	public Location deleteLocation(Connection connection, int locationID) {
		Location location = getLocation(connection, locationID);

		if (location  == null)
			return null;

		// step 1 - delete from itemlocation by location
		ItemLocationDBService ilDB = new ItemLocationDBService();
		ilDB.deleteAllByLocation(connection, locationID);
		
		// step 2 - delete location
		String query = "delete from Location where LocationID = ?";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setInt(1, locationID);

			affectedRows = statement.executeUpdate();

			System.out.println("delete> affected rows " + affectedRows);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return location;
	}
	
	public Location UpdateLocation(Connection connection, Location location) {
		if (location == null) return null;
		
		Location locationInDB = getLocation(connection, location.getLocationID());

		if (locationInDB == null) {
			System.out.println("update> no location in DB");
			return null;
		}

		if (locationInDB.equals(location)) {
			System.out.println("update> no need to update location - same item!");
			return null;
		}

		String query = "update Location set Name = ?, AccessCode = ? WHERE LocationID = ?";
		int affectedRows = 0;

		try (PreparedStatement statement = connection.prepareStatement(query)) {
			statement.setString(1, location.getName());
			statement.setString(2, location.getAccessCode());
			statement.setInt(3, location.getLocationID());
			
			affectedRows = statement.executeUpdate();
			
			System.out.println("update> affected rows " + affectedRows);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		locationInDB = getLocation(connection, location.getLocationID());
		return locationInDB;
	}
}
