package config;

import exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnection {

    private static final Logger logger = LogManager.getLogger(DatabaseConnection.class);

    private static DatabaseConnection instance;
    private static Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/escape_room_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión exitosa a la base de datos.");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error al conectar con la base de datos.", e);
            throw new DatabaseException("Error al conectar con la base de datos.", e);
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
                connection.close();
                logger.info("Conexión cerrada correctamente.");
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión.", e);
                throw new DatabaseException("Error al cerrar la conexión.", e);
            }
        }
    }
}
