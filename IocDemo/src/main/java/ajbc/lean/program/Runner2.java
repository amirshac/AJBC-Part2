package ajbc.lean.program;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ajbc.lean.config.AppConfig;
import ajbc.lean.dao.ProductDao;
import ajbc.learn.models.Category;

public class Runner2 {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);

		RowMapper<Category> rowMapper = new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category cat = new Category();
				cat.setCategoryId(rs.getInt(1));
				cat.setCategoryName(rs.getString("categoryName"));
				cat.setDescription(rs.getString("description"));
				cat.setPicture(rs.getBytes("picture"));
				return cat;
			}
		};
		
		
		// CRUD
		//insertNewShipper(template);
		//updateShipperPhone(template, 4, "(012) 345-6789");
				
		// query that reutns a value
		
		//printNumProducts(template);
		//printShipperName(template, 4);
		//printCityOfcustomerById(template, "FISSA");
		
		
		//query that returns row (map)
		
		//printProductDetails(template, 3);
		//printAnyOrderOfEmployeeById(template, 5);
		
		// query that returns list
		//printAllOrdersOfEmployeeById(template, 5);
		//printAllShippers(template);
		//printAllShipperNames(template);
		
		// using row mapper to map object
		//printCategoryById(template, rowMapper, 5);
		printAllCategories(template, rowMapper);
	}

	private static void printAllCategories(JdbcTemplate template, RowMapper<Category> rowMapper) {
		String sql = "select * from categories";
		List<Category> catList = template.query(sql, rowMapper);
		System.out.println(catList);		
	}

	private static void printCategoryById(JdbcTemplate template, RowMapper<Category> rowMapper, int id) {
		String sql = "select * from categories where categoryId = ?";
		Category cat = template.queryForObject(sql, rowMapper, id);
		System.out.println(cat);
		
	}

	private static void printAllShipperNames(JdbcTemplate template) {
		String sql = "Select companyName from shippers";
		List<String> list = template.queryForList(sql, String.class);
		list.forEach(p->System.out.println(p));
	}

	private static void printAllShippers(JdbcTemplate template) {
		String sql = "Select * from shippers";
		List<Map<String, Object>> data = template.queryForList(sql);
		for (Map<String,Object> map : data) {
			System.out.println(map);
		}
	}

	private static void printAnyOrderOfEmployeeById(JdbcTemplate template, int employeeId) {
		String sql = "Select top(1) * from orders where employeeId = ?";
		Map<String, Object> data = template.queryForMap(sql, employeeId);
		System.out.println(data);	
	}
	
	private static void printAllOrdersOfEmployeeById(JdbcTemplate template, int employeeId) {
		String sql = "Select * from orders where employeeId = ?";
		List<Map<String, Object>> data = template.queryForList(sql, employeeId);
		System.out.println(data);	
	}

	private static void printProductDetails(JdbcTemplate template, int id) { 
		String sql = "select * from products where productId = ?";
		Map<String, Object> data = template.queryForMap(sql, id);
		System.out.println(data);
		System.out.println(data.keySet());
		System.out.println(data.values());
	}

	private static void printCityOfcustomerById(JdbcTemplate template, String id) {
		String sql = "select City from Customers where CustomerId = ?";
		String name = template.queryForObject(sql, String.class, id);
		System.out.println("city Name - " + name);		
	}

	private static void printShipperName(JdbcTemplate template, int id) {
		String sql = "select companyName from shippers where shipperId = ?";
		String name = template.queryForObject(sql, String.class, id);
		System.out.println("shipper company name - " + name);
	}

	private static void printNumProducts(JdbcTemplate template) {
		String sql = "select count(*) from products";
		int count = template.queryForObject(sql, int.class);
		System.out.println("count: " + count);
	}

	private static void insertNewShipper(JdbcTemplate template) {
		String sql = "insert into shippers values (?, ?)";
		int rowsAffected = template.update(sql, "Shachar Imports", "052-20202020");
		System.out.println("rows affected : " + rowsAffected);
	}
	
	private static void updateShipperPhone(JdbcTemplate template, int id, String phone) {
		String sql = "update shippers set phone = ? where ShipperId = ?";
		int rowsAffected = template.update(sql, phone, id);
		System.out.println("rows affected updated shippers: " + rowsAffected);
	}
}
