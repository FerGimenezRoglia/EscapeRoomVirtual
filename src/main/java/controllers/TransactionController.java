package controllers;

import services.TicketService;
import models.Ticket;
import exceptions.DataAccessException;

import java.util.List;

public class TransactionController {
    private final TicketService ticketService;

    public TransactionController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    public void viewTickets() {
        try {
            List<Ticket> tickets = ticketService.viewTickets();
            if (tickets.isEmpty()) {
                System.out.println("No hay tickets vendidos.");
            } else {
                System.out.println("\n===== LISTA DE TICKETS VENDIDOS =====");
                for (Ticket ticket : tickets) {
                    System.out.println(ticket);
                }
            }
        } catch (DataAccessException e) {
            System.err.println("Error al recuperar los tickets: " + e.getMessage());
        }
    }

    public boolean deleteTicket(int ticketId) {
        try {
            return ticketService.deleteTicket(ticketId);
        } catch (DataAccessException e) {
            System.err.println("Error al eliminar el ticket: " + e.getMessage());
            return false;
        }
    }
}
