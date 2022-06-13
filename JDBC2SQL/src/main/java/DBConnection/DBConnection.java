package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	protected String hostName, dbName, serverName, userName, userPass;
	protected String port;
	
	protected String connectionUrl;
	protected Connection connection;
	
	public DBConnection(String hostName, String port, String dbName, String serverName, String userName, String userPass) {
		setHostName(hostName);
		setDbName(dbName);
		setPort(port);
		setServerName(serverName);
		setUserName(userName);
		setUserPass(userPass);
		buildUrl();
		connection = null;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPass() {
		return userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	
	protected String buildUrl() {
		connectionUrl = "jdbc:sqlserver://" + serverName + ":" + port + ";databaseName=" + 
		dbName + ";servername=" + serverName + ";encrypt=false;";
		
		return connectionUrl;
	}
	
	public Connection connect() throws SQLException {
		connection = DriverManager.getConnection(connectionUrl, userName, userPass);
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println("Can't close connection");
			e.printStackTrace();
		}
	}
	
}
