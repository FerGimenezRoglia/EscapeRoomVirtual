package view.management;

import controllers.TransactionController;
import exceptions.AppException;
import models.Ticket;

import java.util.List;
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
                System.out.println("4. Volver");
                System.out.print("Seleccione una opción: ");

                int option = getOption();
                switch (option) {
                    case 1 -> registerTicketSale();
                    case 2 -> viewTickets();
                    case 3 -> deleteTicket();
                    case 4 -> {
                        System.out.println("Volviendo al menú de gestión...");
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

        // Obtener el estado de suscripción usando un método auxiliar
        boolean isSubscribed = getSubscriptionStatus();

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
        System.out.println("\n✅Venta de Ticket registrada correctamente.");
    }

    private boolean getSubscriptionStatus() {
        while (true) {
            System.out.print("¿El Cliente está suscrito? (sí/no): ");
            String subscribedInput = scanner.nextLine().trim().toLowerCase();

            if (subscribedInput.equals("sí") || subscribedInput.equals("si")) {
                return true;
            } else if (subscribedInput.equals("no")) {
                return false;
            } else {
                System.out.println("Error: Entrada inválida. Por favor, ingresa 'sí' o 'no'.");
            }
        }
    }

    private void viewTickets() {
        System.out.println("\n===== LISTA DE TICKETS VENDIDOS =====");
        List<Ticket> tickets = transactionController.viewTickets();

        if (tickets.isEmpty()) {
            System.out.println("No hay tickets vendidos.");
        } else {
            tickets.forEach(ticket -> {
                System.out.printf("\nTicket ID:       %d%n", ticket.getId());
                System.out.printf("Cliente ID:      %d%n", ticket.getClientId());
                System.out.printf("Sala ID:         %d%n", ticket.getRoomId());
                System.out.printf("Precio Total:    %.2f€%n", ticket.getTotalPrice());
                System.out.printf("Fecha de Compra: %s%n", ticket.getPurchaseDate());
                System.out.println("----------------------------------------");
            });
        }
    }



    private void deleteTicket() {
        int ticketId;
        do {
            System.out.print("Ingrese el ID del Ticket a eliminar: ");
            ticketId = getOption();
            if (ticketId == -1) {
                System.out.println("Error: ID de ticket inválido. Inténtalo de nuevo.");
            }
        } while (ticketId == -1);

        boolean success = transactionController.deleteTicket(ticketId);
        if (success) {
            System.out.println("\n✅Ticket eliminado con éxito.");
        } else {
            System.out.println("\n❌No se pudo eliminar el Ticket.");
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