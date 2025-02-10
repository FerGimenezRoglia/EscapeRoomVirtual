import config.DatabaseConnection;
import exceptions.DatabaseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public void run() {
        logger.info("El proyecto Maven funciona correctamente desde App.");

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        if (dbConnection.getConnection() != null) {
            logger.info("Conexión establecida en App.");

            // Agregamos logs antes y después de ejecutar el esquema SQL
            logger.info("Ejecutando esquema SQL...");
            try {
                dbConnection.ejecutarSchema();
                logger.info("Esquema SQL ejecutado correctamente.");
            } catch (DatabaseException e) {
                logger.error("Error al ejecutar el esquema SQL: {}", e.getMessage());
            }

        } else {
            throw new DatabaseException("No se pudo establecer la conexión en App.");
        }

        dbConnection.closeConnection();
    }
}
