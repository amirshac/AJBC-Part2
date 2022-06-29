package ajbc.learn.dao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Product;

@Component(value = "jdbcDao")
@Getter
@Setter
@NoArgsConstructor

public class JdbcProductDao implements ProductDao {
	
	@Autowired
	private JdbcTemplate template;
	
	private RowMapper<Product> rowMapper = (rs, rowNum) ->{
		Product prod = new Product();
		prod.setProductId(rs.getInt("ProductId"));
		prod.setProductName(rs.getString("ProductName"));
		prod.setCategoryId(rs.getInt("CategoryId"));
		prod.setSupplierId(rs.getInt("SupplierID"));
		prod.setQuantityPerUnit(rs.getString("QuantityPerUnit"));
		prod.setUnitPrice(rs.getDouble("UnitPrice"));
		prod.setUnitsInStock(rs.getInt("UnitsInStock"));
		prod.setUnitsOnOrder(rs.getInt("UnitsOnOrder"));
		prod.setReorderLevel(rs.getInt("ReorderLevel"));
		prod.setDiscontinued(rs.getInt("Discontinued"));

		return prod;
	};

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		if (productId == null) throw new DaoException("getProduct -> Incorrect productId");
		
		Product prod = null;
		
		String sql = "select * from products where ProductId = ?";
		try {
			prod = template.queryForObject(sql, rowMapper, productId);
		}catch (EmptyResultDataAccessException e) {
			throw new DaoException("GetProduct -> Product not found");
		}
		
		return prod;
	}
	
	@Override 
	public void addProduct(Product p) throws DaoException {
		String sql = "insert into products values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		int rows = template.update(sql, p.getProductName(), p.getSupplierId(),
				p.getCategoryId(), p.getQuantityPerUnit(), p.getUnitPrice(), p.getUnitsInStock(),
				p.getUnitsOnOrder(), p.getReorderLevel(), p.getDiscontinued());

		if (rows == 0) throw new DaoException("addProduct -> couldn't insert product");
	}
	
	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		String sql = "delete from products where productId = ?";
		int rows = template.update(sql, productId);
		
		if (rows == 0) throw new DaoException("deleteProduct -> couldn't delete product with id " + productId);
	}
	
	@Override
	public void updateProduct(Product p) throws DaoException{
		String sql = "update products set productname = ?, supplierid = ?, categoryid = ?, quantityperunit = ?, "
				+ "unitprice = ?, unitsinstock = ?, unitsonorder = ?, reorderlevel = ?, "
				+ "discontinued = ? where productid = ?";
		
		int rows = template.update(sql, p.getProductName(), p.getSupplierId(),
				p.getCategoryId(), p.getQuantityPerUnit(), p.getUnitPrice(), p.getUnitsInStock(),
				p.getUnitsOnOrder(), p.getReorderLevel(), p.getDiscontinued(), p.getProductId() );
		
		if (rows == 0) throw new DaoException("updateProduct -> couldn't update product with id " + p.getProductId());
	}
	
	// Queries
	@Override
	public List<Product> getAllProducts() throws DaoException {
		String sql = "select * from products";
		List<Product> productList = template.query(sql, rowMapper);
		return productList;
	}
	
	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		String sql = "select * from products where unitPrice >= ? and unitprice <= ?";
		List<Product> productList = template.query(sql, rowMapper, min, max);
		
		if (productList.isEmpty()) throw new DaoException("getProductsByPriceRange -> no products found");
		return productList;
	}
	
	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		String sql = "select * from products where categoryid = ?";
		List<Product> productList = template.query(sql, rowMapper, categoryId);
		
		if (productList.isEmpty()) throw new DaoException("getProductsInCategory -> no products found in categoryid " + categoryId);

		return productList;
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		String sql = "select * from products where UnitsInStock = 0";
		List<Product> productList = template.query(sql, rowMapper);
		
		if (productList.isEmpty()) throw new DaoException("getProductsNotInStock -> no products found not in stock");
		return productList;
	}
	
	@Override
	public List<Product> getProductsOnOrder() throws DaoException{
		String sql = "select * from products where UnitsOnOrder > 0";
		List<Product> productList = template.query(sql, rowMapper);
		if (productList.isEmpty()) throw new DaoException("getProductsOnOrder -> no products found");
		return productList;
	}
	
	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException{
		String sql = "select * from products where Discontinued = 1";
		List<Product> productList = template.query(sql, rowMapper);
		if (productList.isEmpty()) throw new DaoException("getDiscontinuedProducts -> no products found");
		return productList;
	}
	
	@Override
	public long count() throws DaoException {
		String sql = "select count(*) from products";
		Long count = template.queryForObject(sql, long.class);
		return count;
	}
}


/*
	@Autowired(required=false)
	private Connection connection;
	
	@Autowired(required=false)
	private DataSource dataSource;

	// add a connection to db
	@Override
	public long count() {

		String sql = "select count(*) from products";
		try (Connection conn = createConnection(); // reference to connection bean so it closes 
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet resultSet = statement.executeQuery();
		) {
			if(resultSet.next()) {
				return resultSet.getLong(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

	private Connection createConnection() throws SQLException {
		if (dataSource!=null)
			return dataSource.getConnection();
		if (connection == null || connection.isClosed())
			throw new RuntimeException("connection closed");
		return connection;
	}

*/