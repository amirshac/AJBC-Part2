package dbservices;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeDBService {
	
	protected String query;
	protected int rowsAffected;
	
	public EmployeeDBService() {}
	
	public Employee addEmployee(Connection connection, Employee e) {
		try (Statement statement = connection.createStatement()){
			
			query = "Insert into Employees (lastName, firstName, email, department, salary)"
					+ "values('%s', '%s', '%s', '%s', %f)"
					.formatted(e.getLastName(), e.getFirstName(), e.getEmail(), e.getDepartment(), e.getSalary());
			
			System.out.println(query);
			
			rowsAffected = statement.executeUpdate(query);
			
			if (rowsAffected == 1) {
				return e;
			}
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return null;
	}

}
