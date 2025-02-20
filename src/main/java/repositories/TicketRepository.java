package repositories;

import models.Ticket;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements Repository<Ticket> {
    private final Connection connection;

    private static final String INSERT_TICKET = "INSERT INTO ticket (client_id, room_id, total_price, purchase_date) VALUES (?, ?, ?, ?)";
    private static final String FETCH_PURCHASE_DATE = "SELECT purchase_date FROM ticket WHERE id = ?";
    private static final String SELECT_TICKET_BY_ID = "SELECT * FROM ticket WHERE id = ?";
    private static final String SELECT_ALL_TICKETS = "SELECT * FROM ticket";
    private static final String UPDATE_TICKET = "UPDATE ticket SET client_id = ?, room_id = ?, total_price = ? WHERE id = ?";
    private static final String DELETE_TICKET = "DELETE FROM ticket WHERE id = ?";

    public TicketRepository(Connection connection) {
        this.connection = connection;
    }

    private Ticket mapResultSet(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getInt("id"),
                resultSet.getInt("client_id"),
                resultSet.getInt("room_id"),
                resultSet.getDouble("total_price"),
                resultSet.getTimestamp("purchase_date")
        );
    }

    @Override
    public void add(Ticket ticket) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_TICKET, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, ticket.getClientId());
            statement.setInt(2, ticket.getRoomId());
            statement.setDouble(3, ticket.getTotalPrice());
            statement.setTimestamp(4, ticket.getPurchaseDate());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar el ticket.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    ticket.setId(keys.getInt(1));

                    try (PreparedStatement fetchStatement = connection.prepareStatement(FETCH_PURCHASE_DATE)) {
                        fetchStatement.setInt(1, ticket.getId());
                        try (ResultSet rs = fetchStatement.executeQuery()) {
                            if (rs.next()) {
                                ticket.setPurchaseDate(rs.getTimestamp("purchase_date"));
                            }
                        }
                    }
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar el ticket", e);
        }
    }

    @Override
    public Ticket getById(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_TICKET_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                } else {
                    throw new DataAccessException("No se encontró el ticket con ID " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener el ticket con ID " + id, e);
        }
    }

    @Override
    public List<Ticket> getAll() throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TICKETS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Ticket> tickets = new ArrayList<>();
            while (resultSet.next()) {
                tickets.add(mapResultSet(resultSet));
            }
            return tickets;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la lista de tickets", e);
        }
    }

    @Override
    public void update(Ticket ticket) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_TICKET)) {
            statement.setInt(1, ticket.getClientId());
            statement.setInt(2, ticket.getRoomId());
            statement.setDouble(3, ticket.getTotalPrice());
            statement.setInt(4, ticket.getId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el ticket con ID " + ticket.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar el ticket", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_TICKET)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el ticket con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar el ticket", e);
        }
    }
}