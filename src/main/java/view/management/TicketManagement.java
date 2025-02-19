package view.management;

import controllers.TransactionController;
import exceptions.AppException;

import java.util.Scanner;

public class TicketManagement {
    private final TransactionController transactionController;
    private final Scanner scanner;

    public TicketManagement(TransactionController transactionController) {
        this.transactionController = transactionController;
        this.scanner = new Scanner(System.in);
    }

    public void manageTickets() {
        boolean continuar = true;
        try {
            while (continuar) {
                System.out.println("\n===== GESTIÓN DE TICKETS =====");
                System.out.println("1. Registrar Venta de Ticket");
                System.out.println("2. Ver Tickets Vendidos");
                System.out.println("3. Eliminar Ticket");
                System.out.println("4. Volver al menú principal");
                System.out.print("Elige una opción: ");

                int option = getOption();
                switch (option) {
                    case 1 -> registerTicketSale();
                    case 2 -> viewTickets();
                    case 3 -> deleteTicket();
                    case 4 -> {
                        System.out.println("Volviendo al menú principal...");
                        continuar = false;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en la gestión de tickets: " + e.getMessage());
        }
    }

    private void registerTicketSale() {
        System.out.println("\n===== REGISTRAR VENTA DE TICKET =====");

        System.out.print("Nombre del Cliente: ");
        String name = scanner.nextLine().trim();

        System.out.print("Email del Cliente: ");
        String email = scanner.nextLine().trim();

        System.out.print("¿El Cliente está suscrito? (true/false): ");
        boolean isSubscribed = Boolean.parseBoolean(scanner.nextLine().trim());

        int roomId;
        do {
            System.out.print("ID de la Sala: ");
            roomId = getOption();
            if (roomId == -1) {
                System.out.println("Error: ID de sala inválido. Inténtalo de nuevo.");
            }
        } while (roomId == -1);

        double price;
        do {
            System.out.print("Precio del Ticket: ");
            price = getDouble();
            if (price == -1) {
                System.out.println("Error: Precio inválido. Inténtalo de nuevo.");
            }
        } while (price == -1);

        transactionController.registerTicketSale(name, email, isSubscribed, roomId, price);
        System.out.println("Venta de Ticket registrada correctamente.");
    }

    private void viewTickets() {
        System.out.println("\n===== LISTA DE TICKETS VENDIDOS =====");
        transactionController.viewTickets();
    }

    private void deleteTicket() {
        int ticketId;
        do {
            System.out.print("ID del Ticket a eliminar: ");
            ticketId = getOption();
            if (ticketId == -1) {
                System.out.println("Error: ID de ticket inválido. Inténtalo de nuevo.");
            }
        } while (ticketId == -1);

        boolean success = transactionController.deleteTicket(ticketId);
        if (success) {
            System.out.println("Ticket eliminado con éxito.");
        } else {
            System.out.println("No se pudo eliminar el Ticket.");
        }
    }

    private int getOption() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido.");
            return -1;
        }
    }

    private double getDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingresa un número válido.");
            return -1;
        }
    }
}
