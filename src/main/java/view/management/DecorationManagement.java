package view.management;

import config.AppInitializer;
import controllers.ManagementController;
import exceptions.AppException;
import java.util.Scanner;

public class DecorationManagement {
    private final ManagementController managementController;
    private final AppInitializer appInitializer;
    private final Scanner scanner;

    public DecorationManagement(ManagementController managementController, AppInitializer appInitializer) {
        this.managementController = managementController;
        this.appInitializer = appInitializer;
        this.scanner = new Scanner(System.in);
    }

    public void manageDecorations() {
        boolean continuar = true; // Variable de control en lugar de return
        try {
            while (continuar) {
                System.out.println("\n===== GESTIÓN DE DECORACIONES =====");
                System.out.println("1. Agregar Decoración");
                System.out.println("2. Eliminar Decoración");
                System.out.println("3. Volver");
                System.out.print("Elige una opción: ");

                int option = getOption();
                switch (option) {
                    case 1 -> addDecoration();
                    case 2 -> deleteDecoration();
                    case 3 -> {
                        System.out.println("Volviendo...");
                        continuar = false; // Se cambia la variable para salir del bucle
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en la gestión de decoraciones: " + e.getMessage());
        }
    }

    private void addDecoration() {
        int roomId;
        do {
            System.out.print("Ingrese el ID de la Sala: ");
            roomId = getOption();
            if (roomId == -1) {
                System.out.println("Error: ID inválido. Inténtalo de nuevo.");
            }
        } while (roomId == -1);

        System.out.print("Ingrese el nombre de la decoración: ");
        String name = scanner.nextLine();

        System.out.print("Ingrese el material de la decoración: ");
        String material = scanner.nextLine();

        double price;
        do {
            System.out.print("Ingrese el precio de la decoración: ");
            price = getDouble();
            if (price == -1) {
                System.out.println("Error: Precio inválido. Inténtalo de nuevo.");
            }
        } while (price == -1);

        managementController.addDecoration(roomId, name, material, price);
        System.out.println("Decoración agregada correctamente.");

        // 👁🔹👁️ Agregamos la notificación al Observer
        appInitializer.getEventNotifier().notifyObservers("Nueva decoración agregada: " + name);

    }

    private void deleteDecoration() {
        int decorationId;
        do {
            System.out.print("Ingrese el ID de la decoración a eliminar: ");
            decorationId = getOption();
            if (decorationId == -1) {
                System.out.println("Error: ID inválido. Inténtalo de nuevo.");
            }
        } while (decorationId == -1);

        boolean success = managementController.deleteDecoration(decorationId);
        if (success) {
            System.out.println("Decoración eliminada con éxito.");

            // 👁🔹👁️ Agregamos la notificación al Observer
            appInitializer.getEventNotifier().notifyObservers("Decoración eliminada: ID " + decorationId);

        } else {
            System.out.println("No se pudo eliminar la decoración.");
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
