package config;

import services.RoomService;
import controllers.Operaciones;
import java.sql.Connection;

public class AppInitializer {
    private DatabaseConnection dbConnection;
    private Connection connection;
    private RoomService roomService;
    private Operaciones operaciones;

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();
        roomService = new RoomService(connection);
        operaciones = new Operaciones(roomService);
    }

    public Operaciones getOperaciones() {
        return operaciones;
    }

    public void ejecutarSchema() {
        dbConnection.ejecutarSchema();
    }

    public void close() {
        dbConnection.closeConnection();
    }
}