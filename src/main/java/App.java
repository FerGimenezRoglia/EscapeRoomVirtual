import config.DatabaseConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public void run() {
        logger.info("El proyecto Maven funciona correctamente desde App.");

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            logger.info("Conexión establecida en App.");
        } else {
            logger.error("No se pudo establecer la conexión en App.");
        }

        try {
            if (connection != null) {
                dbConnection.closeConnection();
                logger.info("Conexión cerrada correctamente.");
            }
        } catch (Exception e) {
            logger.error("Error al cerrar la conexión: {}", e.getMessage());
        }
    }

    // PRUEBA TEMPORAL PARA VALIDAR LA CONEXIÓN A MYSQL
    public void testConexion() {
        logger.info("Iniciando prueba de conexión a MySQL...");

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        Connection connection = dbConnection.getConnection();

        if (connection != null) {
            logger.info("Conexión establecida con éxito.");

            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT CURRENT_USER()")) {

                if (rs.next()) {
                    logger.info("Usuario conectado a MySQL: {}", rs.getString(1));
                }
            } catch (SQLException e) {
                logger.error("Error al ejecutar la consulta de prueba: {}", e.getMessage());
            } finally {
                dbConnection.closeConnection();
            }
        } else {
            logger.error("No se pudo establecer la conexión a MySQL.");
        }
    }
}
