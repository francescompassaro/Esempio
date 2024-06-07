package librerie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import primo.Main;
import costants.Constants;

public class ConnessioneDB {

	
	
	public static void setParametri() {
		// Connessione al database
		Main.driverManager = new DriverManagerDataSource(Constants.DB_URL);
		Main.driverManager.setSchema("sakila");
		Main.driverManager.setUsername("root");
		Main.driverManager.setPassword("Orthogonal2-Seascape5");
		Main.jdbcTemplate = new JdbcTemplate(Main.driverManager);
		System.out.println("Connessione avvenuta con successo!");

	}

}
