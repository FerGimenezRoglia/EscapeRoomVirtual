package services;

import repositories.TicketRepository;
import models.Ticket;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

public class TicketService {
    private final TicketRepository ticketRepository;
    private final RoomClientService roomClientService; // 📄 NUEVO

    public TicketService(Connection connection, RoomClientService roomClientService) {
        this.ticketRepository = new TicketRepository(connection);
        this.roomClientService = roomClientService; // 📄
    }

    public List<Ticket> viewTickets() throws DataAccessException {
        return ticketRepository.getAll();
    }

    public boolean deleteTicket(int ticketId) throws DataAccessException {
        try {
            ticketRepository.delete(ticketId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public Ticket registerSale(int clientId, int roomId, double totalPrice) throws DataAccessException {
        Ticket ticket = new Ticket(clientId, roomId, totalPrice);
        ticket.setPurchaseDate(new Timestamp(System.currentTimeMillis())); // Se asigna manualmente la fecha
        ticketRepository.add(ticket);

        roomClientService.markRoomAsStarted(clientId, roomId); // 📄

        return ticket;
    }
}
