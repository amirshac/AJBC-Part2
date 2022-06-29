package ajbc.lean.program;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.lean.config.AppConfig;
import ajbc.lean.dao.DaoException;
import ajbc.lean.dao.ProductDao;

public class Runner {

	public static void main(String[] args) throws DaoException {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		// The dependency
		ProductDao dao1;
		
		// The spring container
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		System.out.println("-------------------------------------------");
		
		// get bean - name and type. 
		// Theres no 'new', dependency reduced.
		dao1 = ctx.getBean("jdbcDao", ProductDao.class);
		
		
		System.out.println("-------------------------------------------");
		System.out.println("number of products in DB - " + dao1.count());
		System.out.println("-------------------------------------------");
	}

}
