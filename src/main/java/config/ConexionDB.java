package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:sqlite:data/turnos.db";

    public static Connection getConexion() throws SQLException {
        try {
            // Forzar el registro de la clase Driver de SQLite
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de SQLite en el classpath.", e);
        }
        return DriverManager.getConnection(URL);
    }
}
