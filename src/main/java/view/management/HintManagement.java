package view.management;

import config.AppInitializer;
import controllers.ManagementController;
import exceptions.AppException;
import java.util.Scanner;

public class HintManagement {
    private final ManagementController managementController;
    private final AppInitializer appInitializer;
    private final Scanner scanner;

    public HintManagement(ManagementController managementController, AppInitializer appInitializer) {
        this.managementController = managementController;
        this.appInitializer = appInitializer;
        this.scanner = new Scanner(System.in);
    }

    public void manageHints() {
        boolean continuar = true; // Variable de control para salir del bucle
        try {
            while (continuar) {
                System.out.println("\n===== GESTIÓN DE PISTAS =====");
                System.out.println("1. Agregar una pista");
                System.out.println("2. Eliminar una pista");
                System.out.println("3. Volver al menú principal");
                System.out.print("Elige una opción: ");

                int option = getOption();
                switch (option) {
                    case 1 -> addHint();
                    case 2 -> deleteHint();
                    case 3 -> {
                        System.out.println("Volviendo al menú principal...");
                        continuar = false;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en la gestión de pistas: " + e.getMessage());
        }
    }

    private void addHint() {
        int roomId;
        do {
            System.out.print("ID de la sala donde agregar la pista: ");
            roomId = getOption();
            if (roomId == -1) {
                System.out.println("Error: ID de sala inválido. Inténtalo de nuevo.");
            }
        } while (roomId == -1);

        System.out.print("Descripción de la pista: ");
        String description = scanner.nextLine();

        double price;
        do {
            System.out.print("Precio de la pista: ");
            price = getDouble();
            if (price == -1) {
                System.out.println("Error: Precio inválido. Inténtalo de nuevo.");
            }
        } while (price == -1);

        managementController.addHint(roomId, description, price);
        System.out.println("Pista agregada correctamente.");

        // 👁🔹👁️ Agregamos la notificación al Observer
        appInitializer.getEventNotifier().notifyObservers("Nueva pista agregada: " + description);

    }

    private void deleteHint() {
        int hintId;
        do {
            System.out.print("ID de la pista a eliminar: ");
            hintId = getOption();
            if (hintId == -1) {
                System.out.println("Error: ID de pista inválido. Inténtalo de nuevo.");
            }
        } while (hintId == -1);

        boolean success = managementController.deleteHint(hintId);
        if (success) {
            System.out.println("Pista eliminada con éxito.");

            // 👁🔹👁️ Agregamos la notificación al Observer
            appInitializer.getEventNotifier().notifyObservers("Pista eliminada: ID " + hintId);

        } else {
            System.out.println("No se pudo eliminar la pista.");
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
