package controllers;

import config.AppInitializer;
import models.Client;
import services.TicketService;
import services.ClientService;
import models.Ticket;
import exceptions.DataAccessException;

import java.util.List;

public class TransactionController {
    private final TicketService ticketService;
    private final ClientService clientService;
    private final AppInitializer appInitializer;

    public TransactionController(TicketService ticketService, ClientService clientService, AppInitializer appInitializer) {
        this.ticketService = ticketService;
        this.clientService = clientService;
        this.appInitializer = appInitializer;
    }

    public String registerTicketSale(String name, String email, boolean isSubscribed, int roomId, double totalPrice) {
        try {
            int clientId = clientService.getOrCreateClient(name, email, isSubscribed);

            if (isSubscribed) {
                Client client = clientService.getClientById(clientId);
                appInitializer.getClientNotifier().notifySubscriptionChange(client);
            }

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
