package config;

import services.RoomService;
import services.DecorationService;
import services.HintService;
import controllers.ManagementController;
import controllers.InitializationController; // Importamos InitializationController
import java.sql.Connection;

public class AppInitializer {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private RoomService roomService;
    private DecorationService decorationService;
    private HintService hintService;
    private ManagementController managementController;
    private InitializationController initializationController; // Nueva variable para InitializationController

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();

        // Instanciar servicios
        roomService = new RoomService(connection);
        decorationService = new DecorationService(connection);
        hintService = new HintService(connection);

        // ✅ Instanciar controladores
        managementController = new ManagementController(roomService, decorationService, hintService);
        initializationController = new InitializationController(); // Se instancia correctamente
    }

    public ManagementController getManagementController() {
        return managementController;
    }

    public InitializationController getInitializationController() { //  Método para obtener InitializationController
        return initializationController;
    }

    public void runScheme() {
        dbConnection.ejecutarSchema();
    }

    public void close() {
        dbConnection.closeConnection();
    }
}
