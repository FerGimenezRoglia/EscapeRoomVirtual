package view;

import config.AppInitializer;
import controllers.InitializationController;
import controllers.ManagementController;
import controllers.TransactionController;
import controllers.UserController;  // 📄
import exceptions.AppException;

import models.*;
import services.InventoryService;
import view.management.RoomManagement;
import view.management.DecorationManagement;
import view.management.HintManagement;
import view.management.TicketManagement;
import view.management.UserManagement; // 📄

import java.util.List;
import java.util.Scanner;

public class MenuManagement implements IMenuGestion {
    private final InitializationController initController;
    private final RoomManagement roomManagement;
    private final DecorationManagement decorationManagement;
    private final HintManagement hintManagement;
    private final TicketManagement ticketManagement;
    private final UserManagement userManagement;
    private final InventoryService inventoryService;
    private final AppInitializer appInitializer;
    private final Scanner scanner;

    public MenuManagement(InitializationController initController, ManagementController managementController, TransactionController transactionController, AppInitializer appInitializer, UserController userController, InventoryService inventoryService) {
        this.initController = initController;
        this.appInitializer = appInitializer;
        this.inventoryService = inventoryService;
        this.roomManagement = new RoomManagement(managementController, appInitializer);
        this.decorationManagement = new DecorationManagement(managementController, appInitializer);
        this.hintManagement = new HintManagement(managementController, appInitializer);
        this.ticketManagement = new TicketManagement(transactionController);
        this.userManagement = new UserManagement(userController);
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        boolean continuar = true;
        try {
            while (continuar) {
                showMenuInit();
                int option = getOption();
                switch (option) {
                    case 1 -> initController.startEscapeRoomSetup();
                    case 2 -> showMenuManagement();
                    case 3 -> {
                        System.out.println("Gracias por participar!");
                        continuar = false;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en el menú inicial: " + e.getMessage());
        }
    }

    private void showMenuInit() {
        System.out.println("\nBIENVENIDO");
        System.out.println("\n====== MENÚ PRINCIPAL ======");
        System.out.println("1. Inicializar");
        System.out.println("2. Gestionar");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    private void showMenuManagement() {

        mostrarResumenAdmin();

        boolean continuar = true;
        try {
            while (continuar) {
                showMenuManagementInit();
                int option = getOption();
                switch (option) {
                    case 1 -> roomManagement.manageRooms();
                    case 2 -> decorationManagement.manageDecorations();
                    case 3 -> hintManagement.manageHints();
                    case 4 -> ticketManagement.manageTickets();
                    case 5 -> userManagement.showMenu();
                    case 6 -> inventoryService.showInventory();
                    case 7 -> {
                        System.out.println("Volviendo al menú principal...");
                        continuar = false;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en el menú de Gestión: " + e.getMessage());
        }
    }

    private void showMenuManagementInit() {
        System.out.println("\n===== MENÚ DE GESTIÓN =====");
        System.out.println("1. Salas");
        System.out.println("2. Decoraciones");
        System.out.println("3. Pistas");
        System.out.println("4. Tickets");
        System.out.println("5. Usuarios");
        System.out.println("6. Inventario");
        System.out.println("7. Volver al menú principal");
        System.out.print("Elige una opción: ");
    }

    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new AppException("Error: Ingresa un número válido.", e);
        }
    }

    private void mostrarResumenAdmin() {
        System.out.println("\n=====================================");

        List<Client> clients = appInitializer.getClientService().getAllClients();
        System.out.println("\n🔘 CLIENTES REGISTRADOS:");
        if (clients.isEmpty()) {
            System.out.println("   No hay clientes registrados.");
        } else {
            clients.forEach(c -> System.out.println("   ID: " + c.getId() + " | " + c.getName()));
        }
        System.out.println("_____________________________________");

        List<Ticket> tickets = appInitializer.getTransactionController().viewTickets();
        System.out.println("\n🔘 TICKETS VENDIDOS:");
        if (tickets.isEmpty()) {
            System.out.println("   No hay tickets vendidos.");
        } else {
            tickets.forEach(t -> System.out.println("   ID: " + t.getId() + " | Cliente ID: " + t.getClientId() + " | Sala ID: " + t.getRoomId() + " | 💰 Precio: " + t.getTotalPrice() + "€"));
        }
        System.out.println("_____________________________________");

        System.out.println("\n🔘 SALAS:");
        List<Room> rooms = appInitializer.getInventoryService().getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("   No hay salas registradas.");
        } else {
            rooms.forEach(r -> System.out.println("   ID: " + r.getId() + " | " + r.getName()));
        }
        System.out.println("_____________________________________");

        System.out.println("\n🔘 PISTAS:");
        List<Hint> hints = appInitializer.getInventoryService().getAllHints();
        if (hints.isEmpty()) {
            System.out.println("   No hay pistas registradas.");
        } else {
            hints.forEach(h -> System.out.println("   ID: " + h.getId() + " | " + h.getDescription()));
        }
        System.out.println("_____________________________________");

        System.out.println("\n🔘 DECORACIONES:");
        List<Decoration> decorations = appInitializer.getInventoryService().getAllDecorations();
        if (decorations.isEmpty()) {
            System.out.println("   No hay decoraciones registradas.");
        } else {
            decorations.forEach(d -> System.out.println("   ID: " + d.getId() + " | " + d.getName()));
        }

        System.out.println("\n=====================================");
    }
}
