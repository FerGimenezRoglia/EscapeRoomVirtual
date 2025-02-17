package config;

import services.RoomService;
import controllers.ControllerManagement;
import java.sql.Connection;

public class AppInitializer {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private RoomService roomService;
    private ControllerManagement controllerManagement;

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        roomService = new RoomService(connection);
        controllerManagement = new ControllerManagement(roomService);
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