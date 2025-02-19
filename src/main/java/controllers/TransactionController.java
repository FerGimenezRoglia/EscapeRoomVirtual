package controllers;

import services.TicketService;
import services.ClientService;
import models.Ticket;
import exceptions.DataAccessException;

import java.util.List;

public class TransactionController {
    private final TicketService ticketService;
    private final ClientService clientService;

    public TransactionController(TicketService ticketService, ClientService clientService) {
        this.ticketService = ticketService;
        this.clientService = clientService;
    }

    public String registerTicketSale(String name, String email, boolean isSubscribed, int roomId, double totalPrice) {
        try {
            // Obtener o crear el cliente directamente con `getOrCreateClient()`
            int clientId = clientService.getOrCreateClient(name, email, isSubscribed);

            // Registrar la venta del ticket con el `clientId`
            Ticket newTicket = ticketService.registerSale(clientId, roomId, totalPrice);
            return "Ticket vendido con éxito: " + newTicket;

        } catch (DataAccessException e) {
            return "Error al registrar la venta del ticket: " + e.getMessage();
        }
    }

    public List<Ticket> viewTickets() {
        try {
            return ticketService.viewTickets();
        } catch (DataAccessException e) {
            throw new DataAccessException("Error al recuperar los tickets: " + e.getMessage(), e);
        }
    }

    public boolean deleteTicket(int ticketId) {
        try {
            return ticketService.deleteTicket(ticketId);
        } catch (DataAccessException e) {
            return false;
        }
    }
}
