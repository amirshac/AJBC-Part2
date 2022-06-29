package ajbc.learn.program;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.HibernateTemplateProductDao;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;
import ajbc.learn.models.Product;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner3 {
	public static void main(String[] args) throws DaoException {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class)) {
//			ProductDao dao = ctx.getBean("htDao", ProductDao.class);	
//			
//			List<Product> products = dao.getAllProducts();
//			System.out.println(products.size());
//		
//			Double min = 50.0;
//			Double max = 200.0;
//			products = dao.getProductsByPriceRange(min , max);
//			
//			products.forEach(System.out::println);
//			
//			Product product = dao.getProduct(1);
//			System.out.println(product);
//			
//			//update product
//			product.setUnitPrice(product.getUnitPrice()+1);
//			dao.updateProduct(product);
		}
	}
}

//public static void main(String[] args) {
//
//Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
//root.setLevel(Level.DEBUG);
//
//try(AnnotationConfigApplicationContext ctx= new AnnotationConfigApplicationContext(AppConfig.class);
//		SessionFactory factory = ctx.getBean(SessionFactory.class);
//		Session session = factory.openSession();){
//	
//	//read from DB
//	Category cat1 = session.get(Category.class, 1);
//	System.out.println(cat1);
//	
//	//write to DB - we have to do it inside a transaction to prevent race conditions
//	Category cat2 = new Category();
//	//cat2.setCategoryId(14);
//	cat2.setCatName("baby");
//	cat2.setDescription("Diapers, Napkins");
//	
//	Transaction transaction = session.beginTransaction();
//	try {
//		session.persist(cat2);
//		transaction.commit();
//		System.out.println("Category in DB");
//	}catch(Exception e) {
//		transaction.rollback();
//		System.out.println("Insertion rolled back");
//	}
//}