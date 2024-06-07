package librerie;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogError {
	
	
	public static void logErrorToDatabase(Exception e) {

		String url = "jdbc:mysql://localhost:3306/azienda2";
		String username = "root";
		String password = "Orthogonal2-Seascape5";

		String sql = "INSERT INTO error_log (timestamp, error_type, error_message, stackTrace ) VALUES (?, ?, ?, ?)";

		try (Connection connection = DriverManager.getConnection(url, username, System.getenv("env_var_password"));
//		try (Connection connection = DriverManager.getConnection(url,username, password);
				PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			statement.setString(2, e.getClass().getName());
			statement.setString(3, e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			String stackTrace = sw.toString();
			statement.setString(4, stackTrace);
			statement.executeUpdate();

		} catch (SQLException sqle) {
			System.err.println("Errore durante la scrittura del log nel database: " + sqle.getMessage());
		}
	}
	
	
}
