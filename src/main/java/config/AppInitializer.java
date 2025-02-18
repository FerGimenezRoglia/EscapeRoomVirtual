package config;

import services.RoomService;
import services.DecorationService;
import controllers.ControllerManagement;
import java.sql.Connection;

public class AppInitializer {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private RoomService roomService;
    private DecorationService decorationService;
    private ControllerManagement controllerManagement;

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        roomService = new RoomService(connection);
        decorationService = new DecorationService(connection);
        controllerManagement = new ControllerManagement(roomService, decorationService);
    }

    public ControllerManagement getOperaciones() {
        return controllerManagement;
    }

    public void runScheme() {
        dbConnection.ejecutarSchema();
    }

    public void close() {
        dbConnection.closeConnection();
    }
}
