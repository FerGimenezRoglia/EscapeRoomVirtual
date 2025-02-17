package view;

import config.DatabaseConnection;
import exceptions.AppException;

import controllers.ControllerManagement;

import repositories.DecorationRepository;
import repositories.EscapeRoomRepository;
import repositories.HintRepository;
import repositories.RoomRepository;

import services.EscapeRoomService;
import services.RoomService;

import view.management.RoomManagement;
import java.sql.Connection;
import java.util.Scanner;

public class MenuManagement implements IMenuGestion {
    private final ControllerManagement controllerManagement;
    private final RoomManagement roomManagement; // Instancia de RoomManagement
    private final Scanner scanner;

    // Constructor: Recibe ControllerManagement y RoomService
    public MenuManagement(ControllerManagement controllerManagement, RoomService roomService) {
        this.controllerManagement = controllerManagement;
        this.roomManagement = new RoomManagement(roomService); // Inicializa RoomManagement con roomService
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        try {
            while (true) {
                ShowMenuInit();
                int option = getOption();
                switch (option) {
                    case 1 -> init();
                    case 2 -> showMenuManagement();
                    case 3 -> {
                        System.out.println("Gracias por participar!");
                        return;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en el menú inicial: " + e.getMessage());
        }
    }

    private void ShowMenuInit() {
        System.out.println("\nBIENVENIDO");
        System.out.println("\n====== MENÚ PRINCIPAL=====");
        System.out.println("1. Inicializar");
        System.out.println("2. Gestionar");
        System.out.println("3. Salir");
        System.out.print("Elige una opción: ");
    }

    private void init() {
        Connection connection = DatabaseConnection.getInstance().getConnection();
        EscapeRoomRepository escapeRoomRepo = new EscapeRoomRepository(connection);
        RoomRepository roomRepo = new RoomRepository(connection);
        HintRepository hintRepo = new HintRepository(connection);
        DecorationRepository decorationRepo = new DecorationRepository(connection);

        EscapeRoomService escapeRoomService = new EscapeRoomService(escapeRoomRepo, roomRepo, hintRepo, decorationRepo, connection);
        escapeRoomService.initializeEscapeRoom();
        System.out.println("Inicialización completada con éxito.");
    }

    private void showMenuManagement() {
        try {
            while (true) {
                showMenuManagementInit();
                int option = getOption();
                switch (option) {
                    case 1 -> roomManagement.manageRooms();
                    case 9 -> {
                        System.out.println("Volviendo...");
                        return;
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
        System.out.println("2. Pistas");
        System.out.println("3. Decoraciones");
        System.out.println("9. Volver");
        System.out.print("Elige una opción: ");
    }


    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new AppException("Error: Ingresa un número válido.", e);
        }
    }
}