package ajbc.lean.program;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ajbc.lean.config.AppConfig;
import ajbc.lean.dao.DaoException;
import ajbc.lean.dao.JdbcProductDao;
import ajbc.lean.dao.ProductDao;
import ajbc.learn.models.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class SpringDemo {
	
	private AnnotationConfigApplicationContext ctx;
//	private JdbcTemplate template;
	
	@Autowired
	private ProductDao productDao; 
	
	public SpringDemo() {
		init();
	}
	
	private void init() {
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		//template = ctx.getBean(JdbcTemplate.class);
		productDao = ctx.getBean(ProductDao.class);
	}
	
	public void run() {

		try {
			//testGetProduct();
			//testAddProduct();
			//testDeleteProduct();
			//testUpdateProduct();
			//testGetAllProducts();
			//testGetProductsByPriceRange();
			//testGetProductsInCategory();
			//testGetProductsNotInStock();
			//testGetProductsOnOrder();
			//testGetDiscontinuedProducts();
			testCount();
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetProduct() {
		Product prod;
		try {
			
			prod = productDao.getProduct(1);
			System.out.println(prod);
			
		} catch (DaoException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void testAddProduct() {
		Product prod = new Product();
		prod.setProductId(0);
		prod.setProductName("test");
		prod.setSupplierId(1);
		prod.setCategoryId(1);
		prod.setQuantityPerUnit("alot");
		prod.setUnitPrice(10.0);
		prod.setUnitsInStock(0);
		prod.setUnitsOnOrder(0);
		prod.setReorderLevel(0);
		prod.setDiscontinued(0);
		
		try {
			productDao.addProduct(prod);
		} catch (DaoException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void testDeleteProduct() {
		try {
			productDao.deleteProduct(79);
		} catch (DaoException e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}
	
	public void testUpdateProduct() {
		Product prod;
		try {
			prod = productDao.getProduct(80);
			prod.setProductName("test2");
			productDao.updateProduct(prod);
		} catch (DaoException e) {
			e.printStackTrace();
		}
	}
	
	public void testGetAllProducts() {
		List<Product> prods = null;
		try {
			prods = productDao.getAllProducts();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		prods.forEach(p->System.out.println(p));
	}
	
	public void testGetProductsByPriceRange() {
		List<Product> prods = null;
		try {
			prods = productDao.getProductsByPriceRange(10.0, 20.0);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		prods.forEach(p->System.out.println(p));
	}
	
	public void testGetProductsInCategory() {
		List<Product> prods = null;
		try {
			prods = productDao.getProductsInCategory(1);
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		prods.forEach(p->System.out.println(p));
	}
	
	public void testGetProductsNotInStock() {
		List<Product> prods = null;
		try {
			prods = productDao.getProductsNotInStock();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		prods.forEach(p->System.out.println(p));
	}
	
	public void testGetProductsOnOrder() {
		List<Product> prods = null;
		try {
			prods = productDao.getProductsOnOrder();
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
		prods.forEach(p->System.out.println(p));
	}
	
	public void testGetDiscontinuedProducts() throws DaoException {
		List<Product> prods = productDao.getDiscontinuedProducts();
		prods.forEach(p->System.out.println(p));
	}
	
	public void testCount() throws DaoException {
		long count = productDao.count();
		System.out.println("count: " + count);
	}
}
