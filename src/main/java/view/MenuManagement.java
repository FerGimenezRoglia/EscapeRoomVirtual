package view;

import controllers.InitializationController;
import controllers.ManagementController;
import controllers.TransactionController;
import exceptions.AppException;

import view.management.RoomManagement;
import view.management.DecorationManagement;
import view.management.HintManagement;
import view.management.TicketManagement;

import java.util.Scanner;

public class MenuManagement implements IMenuGestion {
    private final InitializationController initController;
    private final RoomManagement roomManagement;
    private final DecorationManagement decorationManagement;
    private final HintManagement hintManagementView;
    private final TicketManagement ticketManagement;
    private final Scanner scanner;

    public MenuManagement(InitializationController initController, ManagementController managementController, TransactionController transactionController) {
        this.initController = initController;
        this.roomManagement = new RoomManagement(managementController);
        this.decorationManagement = new DecorationManagement(managementController);
        this.hintManagementView = new HintManagement(managementController);
        this.ticketManagement = new TicketManagement(transactionController);
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
        boolean continuar = true;
        try {
            while (continuar) {
                showMenuManagementInit();
                int option = getOption();
                switch (option) {
                    case 1 -> roomManagement.manageRooms();
                    case 2 -> decorationManagement.manageDecorations();
                    case 3 -> hintManagementView.manageHints();
                    case 4 -> ticketManagement.manageTickets();
                    case 9 -> {
                        System.out.println("Volviendo...");
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
