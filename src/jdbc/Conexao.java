package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	public Connection getConnection() {
		try {
			return DriverManager.getConnection(
					"jdbc:mysql://localhost/Comercio", "root", "123");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
