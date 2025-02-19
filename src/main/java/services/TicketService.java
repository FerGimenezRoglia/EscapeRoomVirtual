package services;

import repositories.TicketRepository;
import models.Ticket;
import exceptions.DataAccessException;

import java.sql.Connection;
import java.util.List;

public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(Connection connection) {
        this.ticketRepository = new TicketRepository(connection);
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
}
