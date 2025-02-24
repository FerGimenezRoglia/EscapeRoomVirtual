package config;

import controllers.*;
import observer.*;
import repositories.DecorationRepository;
import repositories.HintRepository;
import repositories.RoomRepository;
import services.*;
import repositories.RoomClientRepository; // 📄
import java.sql.Connection;

public class AppInitializer {
    private final DatabaseConnection dbConnection;
    private final Connection connection;
    private final RoomService roomService;
    private final DecorationService decorationService;
    private final HintService hintService;
    private final TicketService ticketService;
    private final ClientService clientService;
    private final RoomClientService roomClientService;
    private final InventoryService inventoryService;
    private final ManagementController managementController;
    private final InitializationController initializationController;
    private final TransactionController transactionController;
    private final UserController userController;
    private final ClientNotifier clientNotifier;
    private final EventNotifier eventNotifier;

    public AppInitializer() {
        dbConnection = DatabaseConnection.getInstance();
        connection = dbConnection.getConnection();

        roomClientService = new RoomClientService(new RoomClientRepository(connection));
        clientService = new ClientService(connection);
        ticketService = new TicketService(connection, roomClientService);
        roomService = new RoomService(connection);
        decorationService = new DecorationService(connection);
        hintService = new HintService(connection);
        inventoryService = new InventoryService(
                new RoomRepository(connection),
                new HintRepository(connection),
                new DecorationRepository(connection));

        clientNotifier = new ClientNotifier();
        eventNotifier = new EventNotifier();

        clientNotifier.addObserver(new EmailNotifier());
        eventNotifier.addObserver(new SMSNotifier());

        managementController = new ManagementController(roomService, decorationService, hintService, eventNotifier);
        initializationController = new InitializationController();
        transactionController = new TransactionController(ticketService, clientService, this);
        userController = new UserController(roomClientService);
    }

    public ClientNotifier getClientNotifier() {
        return clientNotifier;
    }

    public EventNotifier getEventNotifier() {
        return eventNotifier;
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

    public UserController getUserController() {  // 📄 Agregado
        return userController;
    }

    public InventoryService getInventoryService() {
        return inventoryService;
    }

    public ClientService getClientService() {
        return clientService;
    }

    public void runScheme() {
        dbConnection.ejecutarSchema();
    }

    public void close() {
        dbConnection.closeConnection();
    }
}
