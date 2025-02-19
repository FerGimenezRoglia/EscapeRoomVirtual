package config;

import services.RoomService;
import services.DecorationService;
import services.HintService;
import services.TicketService;
import services.ClientService;

import controllers.ManagementController;
import controllers.InitializationController;
import controllers.TransactionController;

import java.sql.Connection;

public class AppInitializer {
    private final DatabaseConnection dbConnection;
    private final Connection connection;
    private final RoomService roomService;
    private final DecorationService decorationService;
    private final HintService hintService;
    private final TicketService ticketService;
    private final ClientService clientService;
    private final ManagementController managementController;
    private final InitializationController initializationController;
    private final TransactionController transactionController;

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();

        // Instanciar servicios (cada uno maneja sus repositorios internamente)
        clientService = new ClientService(connection);
        ticketService = new TicketService(connection);
        roomService = new RoomService(connection);
        decorationService = new DecorationService(connection);
        hintService = new HintService(connection);

        // Instanciar controladores
        managementController = new ManagementController(roomService, decorationService, hintService);
        initializationController = new InitializationController();
        transactionController = new TransactionController(ticketService, clientService);
    }

    public ManagementController getManagementController() {
        return managementController;
    }

    public InitializationController getInitializationController() {
        return initializationController;
    }

    public TransactionController getTransactionController() {
        return transactionController;
    }

    public void runScheme() {
        dbConnection.ejecutarSchema();
    }

    public void close() {
        dbConnection.closeConnection();
    }
}
