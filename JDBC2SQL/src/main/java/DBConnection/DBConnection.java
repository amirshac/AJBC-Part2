package DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import Utils.PropertiesUtil;

public class DBConnection {
	
	private static final int NUM_OF_PROPERTIES = 6;
	
	protected String hostName, dbName, serverName, userName, userPass;
	protected String port;
	
	protected String connectionUrl;
	protected Connection connection;
	
	public DBConnection(String hostName, String port, String dbName, String serverName, String userName, String userPass) {
		setHostName(hostName);
		setPort(port);
		setDbName(dbName);
		setServerName(serverName);
		setUserName(userName);
		setUserPass(userPass);
		buildUrl();
		connection = null;
	}

	/**
	 * Constructs DBConnection object from properties file
	 * @param propertiesFileName
	 */
	public DBConnection(String propertiesFileName) {
		Properties props = PropertiesUtil.readPropertiesFromFile(propertiesFileName);
		
		if (props==null) {
			System.out.println("Error - can't load propertie file");
			return;
		}
		
		String[] propsStr = parseProperties(props);
		
		if (propsStr == null) {
			System.out.println("Error - can't parse properties");
			return;
		}
		
		setHostName(propsStr[0]);
		setPort(propsStr[1]);
		setDbName(propsStr[2]);
		setServerName(propsStr[3]);
		setUserName(propsStr[4]);
		setUserPass(propsStr[5]);
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
	
	/**
	 * Parses server properties
	 * @param properties
	 * @return array of property values, NULL if can't parse properties
	 */
	private String[] parseProperties(Properties props) {
		String[] propsStr = new String[NUM_OF_PROPERTIES];
		propsStr[0] = props.getProperty("server_address");
		propsStr[1] = props.getProperty("port");
		propsStr[2] = props.getProperty("db_name");
		propsStr[3] = props.getProperty("server_name");
		propsStr[4] = props.getProperty("user");
		propsStr[5] = props.getProperty("password");
		
		for (String str : propsStr) {
			if (str == null) return null;
		}
		
		return propsStr;
	}
	
}
