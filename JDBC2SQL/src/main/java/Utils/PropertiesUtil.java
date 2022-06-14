package Utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
	
	public static Properties readPropertiesFromFile(String fileName) {
		Properties prop = null;
		
		try (FileInputStream fis = new FileInputStream(fileName)){
			prop = new Properties();
			prop.load(fis);	
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
			e.printStackTrace();
			prop = null;
		} catch (IOException e) {
			System.out.println("Can't load properties from file");
			e.printStackTrace();
			prop = null;
		}
		
		return prop;
	}
	
}
