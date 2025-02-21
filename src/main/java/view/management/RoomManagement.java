package view.management;

import config.AppInitializer;
import models.Room;
import exceptions.AppException;
import controllers.ManagementController;
import java.util.Scanner;

public class RoomManagement {
    private final ManagementController managementController;
    private final AppInitializer appInitializer;
    private final Scanner scanner;

    public RoomManagement(ManagementController managementController, AppInitializer appInitializer) {
        this.managementController = managementController;
        this.appInitializer = appInitializer;
        this.scanner = new Scanner(System.in);
    }

    public void manageRooms() {
        boolean continuar = true;
        try {
            while (continuar) {
                System.out.println("\n===== GESTIÓN DE SALAS =====");
                System.out.println("1. Agregar Sala");
                System.out.println("2. Eliminar Sala");
                System.out.println("3. Volver");
                System.out.print("Seleccione una opción: ");

                int option = getOption();
                switch (option) {
                    case 1 -> addRoom();
                    case 2 -> deleteRoom();
                    case 3 -> {
                        System.out.println("Volviendo al menú de gestión...");
                        continuar = false;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en la gestión de salas: " + e.getMessage());
        }
    }

    private void addRoom() {
        int escapeRoomId;
        do {
            System.out.print("Ingrese el ID del Escape Room: ");
            escapeRoomId = getOption();
            if (escapeRoomId == -1) {
                System.out.println("Error: ID inválido. Inténtalo de nuevo.");
            }
        } while (escapeRoomId == -1);

        System.out.print("Ingrese el nombre de la sala: ");
        String name = scanner.nextLine();

        Room.DifficultyLevel difficulty;
        do {
            System.out.print("Ingrese la dificultad (EASY, MEDIUM, HARD): ");
            try {
                difficulty = Room.DifficultyLevel.valueOf(scanner.nextLine().toUpperCase());
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Dificultad no válida. Inténtalo de nuevo.");
                difficulty = null;
            }
        } while (difficulty == null);

        double price;
        do {
            System.out.print("Ingrese el precio de la sala: ");
            price = getDouble();
            if (price == -1) {
                System.out.println("Error: Precio inválido. Inténtalo de nuevo.");
            }
        } while (price == -1);

        managementController.addRoom(escapeRoomId, name, difficulty.name(), price);
        System.out.println("Sala agregada correctamente.");

        // 👁🔹👁️ Aquí agregamos la notificación al Observer
        appInitializer.getEventNotifier().notifyObservers("Nueva sala creada: " + name);
    }

    private void deleteRoom() {
        int roomId;
        do {
            System.out.print("Ingrese el ID de la sala a eliminar: ");
            roomId = getOption();
            if (roomId == -1) {
                System.out.println("Error: ID inválido. Inténtalo de nuevo.");
            }
        } while (roomId == -1);

        boolean success = managementController.deleteRoom(roomId);
        if (success) {
            System.out.println("Sala eliminada con éxito.");
            appInitializer.getEventNotifier().notifyObservers("Sala eliminada: ID " + roomId);
        } else {
            System.out.println("No se pudo eliminar la sala.");
        }
    }

    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido.");
            return -1;
        }
    }

    private double getDouble() {
        try {
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido.");
            return -1;
        }
    }
}
