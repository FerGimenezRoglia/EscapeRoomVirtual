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

    private DatabaseConnection() {
        try {
            // Cargar el driver de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Conectar primero sin base de datos
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=" + USER + "&password=" + PASSWORD);

            // Crear la base de datos si no existe
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("CREATE DATABASE IF NOT EXISTS escape_room_db");
            }

            // Conectar a la base de datos específica
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Conexión exitosa a la base de datos.");

        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error al conectar con la base de datos.", e);
            throw new DataAccessException("Error al conectar con la base de datos.", e);
        }
    }

    // Método para obtener la instancia única (Singleton)
    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    // Obtener la conexión actual
    public Connection getConnection() {
        return connection;
    }

    // Cerrar la conexión a la base de datos
    public void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    logger.info("Conexión cerrada correctamente.");
                }
            } catch (SQLException e) {
                logger.error("Error al cerrar la conexión.", e);
                throw new DataAccessException("Error al cerrar la conexión.", e);
            }
        }
    }

    // Método para ejecutar el esquema SQL
    public void ejecutarSchema() {
        String schemaPath = "src/main/resources/DatabaseSchema.sql";

        // Verificar si la conexión está activa
        try {
            if (connection == null || connection.isClosed()) {
                throw new DataAccessException("No se puede ejecutar el esquema SQL porque la conexión está cerrada.");
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar el estado de la conexión.", e);
        }

        try (Statement stmt = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(schemaPath))) {

            // Asegurar que estamos en la base de datos correcta
            stmt.execute("USE escape_room_db");

            // Leer y ejecutar el esquema SQL línea por línea
            StringBuilder sql = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Ignorar líneas vacías y comentarios
                if (line.isEmpty() || line.startsWith("--")) {
                    continue;
                }

                // Construir la sentencia SQL
                sql.append(line).append(" ");

                // Ejecutar la sentencia cuando se encuentra un punto y coma
                if (line.endsWith(";")) {
                    stmt.execute(sql.toString().trim());
                    sql.setLength(0); // Reiniciar para la siguiente sentencia
                }
            }

            // **Verificar si la columna price ya existe**
            ResultSet rs = stmt.executeQuery("SHOW COLUMNS FROM hint LIKE 'price';");
            if (!rs.next()) { // Si la columna no existe, se agrega
                stmt.execute("ALTER TABLE hint ADD COLUMN price DOUBLE NOT NULL;");
                logger.info("Columna 'price' agregada a la tabla 'hint'.");
            } else {
                logger.info("La columna 'price' ya existe en la tabla 'hint'. No es necesario modificarla.");
            }

            logger.info("Base de datos y esquema SQL ejecutados correctamente.");
        } catch (IOException | SQLException e) {
            throw new DataAccessException("Error al ejecutar el esquema SQL.", e);
        }
    }
}
