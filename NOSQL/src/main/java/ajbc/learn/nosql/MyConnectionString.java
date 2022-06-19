package ajbc.learn.nosql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.mongodb.ConnectionString;


public class MyConnectionString {

	private static String PROPERTIES_FILE = "nosql.properties";
	
	public static ConnectionString uri() {
		return uri(PROPERTIES_FILE);
	}
	
	public static ConnectionString uri(String propertiesFilename) {
		
		Properties props = new Properties();
		FileInputStream fis = null;
		
		
		try {
			fis = new FileInputStream(propertiesFilename);
			props.load(fis);
			
			String user = props.getProperty("user");
			String password = props.getProperty("password");
			String cluster = props.getProperty("cluster");
			String serverAddress = props.getProperty("serverAddress");
			String param1 = props.getProperty("param1");
			String param2 = props.getProperty("param2");
			String uri = "mongodb+srv://%s:%s@%s.%s/?%s&%s".formatted(user, password, cluster, serverAddress, param1, param2);
			
			
			Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
			root.setLevel(Level.ERROR);
			
			return new ConnectionString(uri);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				fis.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return null;
	}
}
