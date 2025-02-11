import config.DatabaseConnection;
import exceptions.DataAccessException;
import models.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import repositories.ClientRepository;

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
            } catch (DataAccessException e) {
                logger.error("Error al ejecutar el esquema SQL: {}", e.getMessage());
            }

            //!------------AQUÍ VAN LAS PRUEBAS----------//
            // Prueba del método add
            try {
                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());

                // Crear un nuevo cliente
                Client newClient = new Client("Jane Doe", "jane@example.com", true);

                // Agregar el cliente a la base de datos
                clientRepository.add(newClient);

                // Verificar que el cliente se insertó correctamente
                if (newClient.getId() > 0) {
                    logger.info("Cliente agregado con éxito: " + newClient);
                } else {
                    logger.warn("⚠No se pudo obtener el ID del cliente insertado.");
                }

            } catch (DataAccessException e) {
                logger.error("Error al insertar cliente: {}", e.getMessage());
            }


            // Prueba del método getById
            try {
                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());

                // ID del cliente a buscar (Reemplaza X con el ID real del cliente insertado)
                int clientId = 1;
                Client client = clientRepository.getById(clientId);

                if (client != null) {
                    logger.info("Cliente encontrado: " + client);
                } else {
                    logger.warn(" No se encontró el cliente con ID " + clientId);
                }
            } catch (DataAccessException e) {
                logger.error("Error al recuperar cliente: {}", e.getMessage());
            }
            //!------------FIN DE LAS PRUEBAS----------//

        } else {
            throw new DataAccessException("No se pudo establecer la conexión en App.");
        }

        dbConnection.closeConnection();
    }
}
