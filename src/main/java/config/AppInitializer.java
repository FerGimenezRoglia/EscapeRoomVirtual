package config;

import services.RoomService;
import controllers.Operations;
import java.sql.Connection;

public class AppInitializer {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private RoomService roomService;
    private Operations operations;

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        roomService = new RoomService(connection);
        operations = new Operations(roomService);
    }

    public Operations getOperaciones() {
        return operations;
    }

    public void runScheme() {
        dbConnection.ejecutarSchema();
    }

    public void close() {
        dbConnection.closeConnection();
    }
}