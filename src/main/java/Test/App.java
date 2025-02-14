package Test;

import config.DatabaseConnection;
import exceptions.DataAccessException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
    private static final Logger logger = LogManager.getLogger(App.class);

    public void run() {
        logger.info("El proyecto Maven funciona correctamente desde Test.App.");

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        if (dbConnection.getConnection() != null) {
            logger.info("Conexión establecida en Test.App.");

            // Agregamos logs antes y después de ejecutar el esquema SQL
            logger.info("Ejecutando esquema SQL...");
            try {
                dbConnection.ejecutarSchema();
                logger.info("Esquema SQL ejecutado correctamente.");
            } catch (DataAccessException e) {
                logger.error("Error al ejecutar el esquema SQL: {}", e.getMessage());
            }

            //!------------AQUÍ VAN LAS PRUEBAS----------//

//            // Prueba del método add
//            try {
//                // Prueba del método add para Client
//                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());
//
//                // Crear un nuevo cliente
//                Client newClient = new Client("Jane Doe", "jane@example.com", true);
//
//                // Agregar el cliente a la base de datos
//                clientRepository.add(newClient);
//
//                // Verificar que el cliente se insertó correctamente
//                if (newClient.getId() > 0) {
//                    logger.info("Cliente agregado con éxito: " + newClient);
//                } else {
//                    logger.warn("No se pudo obtener el ID del cliente insertado.");
//                }
//            } catch (DataAccessException e) {
//                logger.error("Error al insertar cliente o ticket: {}", e.getMessage());
//            }
//
//            // Prueba del método getById
//            try {
//                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());
//
//                // ID del cliente a buscar (Reemplaza X con el ID real del cliente insertado)
//                int clientId = 1;
//                Client client = clientRepository.getById(clientId);
//
//                logger.info("Cliente encontrado: " + client);
//
//            } catch (DataAccessException e) {
//                if (e.getMessage().contains("No se encontró el cliente con ID")) {
//                    logger.warn("⚠ " + e.getMessage());
//                } else {
//                    logger.error("Error al recuperar cliente: {}", e.getMessage());
//                }
//            }
//
//            // Prueba del método getAll
//            try {
//                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());
//
//                // Recuperar todos los clientes
//                List<Client> clients = clientRepository.getAll();
//
//                // Verificar que la lista no esté vacía
//                if (clients != null && !clients.isEmpty()) {
//                    logger.info("Clientes recuperados con éxito: " + clients.size() + " clientes encontrados.");
//
//                    // Mostrar hasta 5 clientes en los logs
//                    clients.stream().limit(5).forEach(client ->
//                            logger.info("Cliente: " + client)
//                    );
//
//                } else {
//                    logger.warn("No se encontraron clientes en la base de datos.");
//                }
//
//            } catch (DataAccessException e) {
//                logger.error("Error al recuperar todos los clientes: {}", e.getMessage());
//            }
//
//            // Prueba del método update
//            try {
//                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());
//
//                int clientId = 1;
//                Client clientToUpdate;
//
//                try {
//                    clientToUpdate = clientRepository.getById(clientId);
//                } catch (DataAccessException e) {
//                    logger.warn("No se encontró el cliente con ID " + clientId);
//                    return; // Termina la prueba si el cliente no existe
//                }
//
//                // Actualizar los datos del cliente
//                clientToUpdate.setName("Jane Smith");
//                clientToUpdate.setEmail("jane.smith@example.com");
//                clientToUpdate.setSubscribed(false);
//
//                // Actualizar el cliente en la base de datos
//                clientRepository.update(clientToUpdate);
//
//                // Recuperar el cliente actualizado para verificar los cambios
//                Client updatedClient = clientRepository.getById(clientId);
//                if (updatedClient.getName().equals("Jane Smith") &&
//                        updatedClient.getEmail().equals("jane.smith@example.com") &&
//                        !updatedClient.isSubscribed()) {
//                    logger.info("Cliente actualizado con éxito: " + updatedClient);
//                } else {
//                    logger.warn("No se pudieron aplicar los cambios al cliente.");
//                }
//
//            } catch (DataAccessException e) {
//                logger.error("Error al actualizar cliente: {}", e.getMessage());
//            }
//
//            // Prueba del método delete
//            try {
//                ClientRepository clientRepository = new ClientRepository(dbConnection.getConnection());
//
//                int clientIdToDelete = 1; // ID de un cliente existente en la base de datos
//                Client clientBeforeDeletion;
//
//                try {
//                    clientBeforeDeletion = clientRepository.getById(clientIdToDelete);
//                } catch (DataAccessException e) {
//                    logger.warn("No se encontró el cliente con ID " + clientIdToDelete);
//                    return; // Termina el test si el cliente no existe
//                }
//
//                logger.info("Cliente encontrado: " + clientBeforeDeletion);
//
//                // Eliminar el cliente
//                clientRepository.delete(clientIdToDelete);
//
//                // Verificar que el cliente ha sido eliminado
//                try {
//                    clientRepository.getById(clientIdToDelete);
//                    logger.warn("El cliente con ID " + clientIdToDelete + " no se pudo eliminar.");
//                } catch (DataAccessException e) {
//                    logger.info("Cliente con ID " + clientIdToDelete + " eliminado con éxito.");
//                }
//
//            } catch (DataAccessException e) {
//                logger.error("Error al eliminar cliente: {}", e.getMessage());
//            }
            //!------------FIN DE LAS PRUEBAS----------//

        } else {
            throw new DataAccessException("No se pudo establecer la conexión en Test.App.");
        }

        dbConnection.closeConnection();
    }
}
