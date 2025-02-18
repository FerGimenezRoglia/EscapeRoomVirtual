package view.management;

import controllers.ControllerManagement;
import exceptions.AppException;
import java.util.Scanner;

public class DecorationManagement {
    private final ControllerManagement controllerManagement;
    private final Scanner scanner;

    public DecorationManagement(ControllerManagement controllerManagement) {
        this.controllerManagement = controllerManagement;
        this.scanner = new Scanner(System.in);
    }

    public void manageDecorations() {
        boolean continuar = true;
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
                        continuar = false;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en la gestión de decoraciones: " + e.getMessage());
        }
    }

    private void addDecoration() {
        System.out.print("Ingrese el ID de la Sala: ");
        int roomId = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el nombre de la decoración: ");
        String name = scanner.nextLine();
        System.out.print("Ingrese el material de la decoración: ");
        String material = scanner.nextLine();
        System.out.print("Ingrese el precio de la decoración: ");
        double price = Double.parseDouble(scanner.nextLine());

        controllerManagement.addDecoration(roomId, name, material, price);
    }

    private void deleteDecoration() {
        System.out.print("Ingrese el ID de la decoración a eliminar: ");
        int decorationId = Integer.parseInt(scanner.nextLine());

        boolean success = controllerManagement.deleteDecoration(decorationId);
        if (success) {
            System.out.println("Decoración eliminada con éxito.");
        } else {
            System.out.println("No se pudo eliminar la decoración.");
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
