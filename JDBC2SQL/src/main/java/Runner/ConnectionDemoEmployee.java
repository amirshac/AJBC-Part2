package Runner;
import java.sql.Connection;
import java.sql.SQLException;
import DBConnection.DBConnection;
import dbservices.*;
import models.Employee;

public class ConnectionDemoEmployee {

	static Connection connection = null;
	static DBConnection dbConnection = null;
	static EmployeeDBService db = new EmployeeDBService();
	
	public static void connectToDB() {
		String hostName = "localhost", port="1433", dbName = "JDBC-DEMO", serverName = "DESK-YEEN";
		String userName = "amir", userPass = "amiramir";
		dbConnection = new DBConnection(hostName, port, dbName, serverName, userName, userPass);
		
		try {
			connection = dbConnection.connect();
			if(connection.isValid(5))
				System.out.println("Connected to DB");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public static void closeConnection() {
		if (connection == null) return;
		try {
			connection.close();
			System.out.println("Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void testAddEmployees() {
		Employee e1 = new Employee(0, "Jason", "Bourne", "jason@jason.com", "FBI", 9550);
		Employee e2 = new Employee(0, "Lady", "Gaga", "gaga@gaga.com", "Gag", 25000);
		db.addEmployee(connection, e1);
		db.addEmployee(connection, e2);
	}
	
	public static void testGetEmployee() {
		Employee emp = db.getEmployee(connection, 1023);
		System.out.println(emp);
	}
	
	public static void testUpdateEmployee() {
		Employee e1 = new Employee(1012, "Jason", "Burns", "jason@jason.com", "FBI", 9550);
		db.updateEmployee(connection, e1);
	}
	
	public static void testDeleteEmployee() {
		db.deleteEmployee(connection, 1013);
	}
	
	public static void main(String[] args) throws SQLException {
		connectToDB();
		
		//testAddEmployees();
		//testGetEmployee();
		//testUpdateEmployee();
		//testDeleteEmployee();
		
		closeConnection();
	}

}
