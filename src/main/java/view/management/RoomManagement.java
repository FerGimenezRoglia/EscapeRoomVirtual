package view.management;

import models.Room;
import exceptions.AppException;
import services.RoomService;
import java.util.Scanner;

public class RoomManagement {
    private final RoomService roomService;
    private final Scanner scanner;

    public RoomManagement(RoomService roomService) {
        this.roomService = roomService;
        this.scanner = new Scanner(System.in);
    }

    public void manageRooms() {
        try {
            while (true) {
                System.out.println("\n===== GESTIÓN DE SALAS =====");
                System.out.println("1. Agregar Sala");
                System.out.println("2. Eliminar Sala");
                System.out.println("3. Volver");
                System.out.print("Elige una opción: ");

                int option = getOption();
                switch (option) {
                    case 1 -> addRoom();
                    case 2 -> deleteRoom();
                    case 3 -> {
                        System.out.println("Volviendo...");
                        return;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en la gestión de salas: " + e.getMessage());
        }
    }

    private void addRoom() {
        System.out.print("Ingrese el ID del Escape Room: ");
        int escapeRoomId = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el nombre de la sala: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese la dificultad (EASY, MEDIUM, HARD): ");
        Room.DifficultyLevel difficulty = Room.DifficultyLevel.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Ingrese el precio de la sala: ");
        double price = Double.parseDouble(scanner.nextLine());

        Room room = roomService.addRoom(escapeRoomId, name, difficulty, price);
        System.out.println("Sala agregada: " + room);
    }

    private void deleteRoom() {
        System.out.print("Ingrese el ID de la sala a eliminar: ");
        int roomId = Integer.parseInt(scanner.nextLine());

        boolean success = roomService.deleteRoom(roomId);
        if (success) {
            System.out.println("Sala eliminada con éxito.");
        } else {
            System.out.println("No se pudo eliminar la sala.");
        }
    }

    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new AppException("Error: Ingresa un número válido.", e);
        }
    }
}