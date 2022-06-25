package ajbc.lean.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;

import ajbc.lean.dao.JdbcProductDao;
import ajbc.lean.dao.MongoProductDao;
import ajbc.lean.program.SpringDemo;


// places to scan for components
@ComponentScan(basePackages = {"ajbc.lean.dao"} )

@Configuration

// classpath tells it to search the properties file within classpath inner directories
@PropertySource("classpath:jdbc.properties")

public class AppConfig {
	private static final int INIT_SIZE = 10, MAX_SIZE = 100, MAX_WAIT = 500, MAX_IDLE = 50, MIN_IDLE = 2;
	
	// this is how we import from properties file
	@Value("${server_address}")
	private String serverAddress;
	@Value("${port}")
	private String port;
	@Value("${db_name}")
	private String databaseName;
	@Value("${server_name}")
	private String serverName;
	@Value("${user}")
	private String userName;
	@Value("${password}")
	private String password;

	
	// datasource - like thread pool for connections
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		
		// credentials
		dataSource.setUsername(userName);
		dataSource.setPassword(password);
		dataSource.setUrl(buildUrl());
		
		// preferences
		dataSource.setInitialSize(INIT_SIZE);
		dataSource.setMaxTotal(MAX_SIZE);
		dataSource.setMaxWaitMillis(MAX_WAIT);
		dataSource.setMaxIdle(MAX_IDLE);
		dataSource.setMinIdle(MIN_IDLE);
		
		return dataSource;
	}
	
	
	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
	
//	@Bean
//	public SpringDemo sprignDemo() {
//		return new SpringDemo();
//	}

	private String buildUrl() {
		return "jdbc:sqlserver://" + serverAddress + ":" + port + ";databaseName=" + databaseName + ";servername="
				+ serverName + ";" + ";encrypt=false";
	}
}







// Bean is created by spring freamework at runtime

// Scope is by default singleton
// @Scope("singleton")
//@Lazy // forces singleton to be lazy, means it won't be created in the beginning, only when we request for it
//@Scope("singleton")
// scope prototype - able to create more than one bean of that type

//@Scope("prototype") // lazy instantiated - only made when we request for it

/*
 * //option 1
 * 
 * @Bean public JDBCProductDao jdbcDao() throws SQLException { JDBCProductDao
 * dao = new JDBCProductDao(); dao.setConnection(createConnection()); return
 * dao; }
 */
/*
 * //option2
 * 
 * @Bean public JDBCProductDao jdbcDao(Connection createConnection) throws
 * SQLException { //injection JDBCProductDao dao = new JDBCProductDao();
 * dao.setConnection(createConnection);//manual wiring return dao; }
 */

// option3
//@Bean
//public Connection connection() throws SQLException {
//	return DriverManager.getConnection(buildUrl(), userName, password);
//}