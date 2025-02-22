package config;

import exceptions.DataAccessException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnection {

    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);
    private static DatabaseConnection instance;
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/escape_room_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";
//    private static final String PASSWORD = "iFer313.";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=" + USER + "&password=" + PASSWORD);

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS escape_room_db");
            }

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("\nConexión exitosa a la base de datos.");

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error al conectar con la base de datos.", e);
            throw new DataAccessException("Error al conectar con la base de datos.", e);
        }
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.info("\nConexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión.", e);
                throw new DataAccessException("Error al cerrar la conexión.", e);
            }
        }
    }

    public void ejecutarSchema() {
        String schemaPath = "src/main/resources/DatabaseSchema.sql";

        try {
            if (connection == null || connection.isClosed()) {
                throw new DataAccessException("No se puede ejecutar el esquema SQL porque la conexión está cerrada.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar el estado de la conexión.", e);
        }

        try (Statement stmt = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(schemaPath))) {

            stmt.execute("USE escape_room_db");

            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }

                sql.append(line).append(" ");

                if (line.endsWith(";")) {
                    stmt.execute(sql.toString().trim());
                    sql.setLength(0);
                }
            }
        } catch (IOException | SQLException e) {
            throw new DataAccessException("Error al ejecutar el esquema SQL.", e);
        }
    }
}
