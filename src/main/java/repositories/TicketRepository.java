package repositories;

import models.Ticket;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements Repository<Ticket> {
    private final Connection connection;

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

    // --------------------- CREATE ---------------------
    @Override
    public void add(Ticket ticket) throws DataAccessException {
        String sql = "INSERT INTO ticket (client_id, room_id, total_price, purchase_date) VALUES (?, ?, ?, ?)";

        // Usar un PreparedStatement para ejecutar la consulta de manera segura.
        // Statement.RETURN_GENERATED_KEYS permite recuperar el ID generado automáticamente.
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, ticket.getClientId());
            statement.setInt(2, ticket.getRoomId());
            statement.setDouble(3, ticket.getTotalPrice());
            statement.setTimestamp(4, ticket.getPurchaseDate());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar el ticket.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    // Asignar el ID generado al objeto Client.
                    ticket.setId(keys.getInt(1));

                    String fetchSql = "SELECT purchase_date ticket WHERE id = ?";
                    try (PreparedStatement fetchStatement = connection.prepareStatement(fetchSql)) {
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

    // --------------------- READ ---------------------
    @Override
    public Ticket getById(int id) throws DataAccessException {
        String sql = "SELECT * FROM ticket WHERE id = ?";

        // Usar PreparedStatement para prevenir inyección SQL y garantizar seguridad.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id); // Asignar el ID como parámetro.

            // Ejecutar la consulta y obtener el ResultSet.
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Si se encuentra el ticket, mapear y devolver el objeto Ticket.
                    return mapResultSet(resultSet);
                } else {
                    // Si no se encuentra el ticket, lanzar una excepción.
                    throw new DataAccessException("No se encontró el ticket con ID " + id);
                }
            }
        } catch (SQLException e) {
            // Capturar y relanzar cualquier excepción de SQL como DataAccessException.
            throw new DataAccessException("Error al obtener el ticket con ID " + id, e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public List<Ticket> getAll() throws DataAccessException {
        String sql = "SELECT * FROM ticket";

        try (PreparedStatement statement = connection.prepareStatement(sql);
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

    // --------------------- UPDATE ---------------------
    @Override
    public void update(Ticket ticket) throws DataAccessException {
        String sql = "UPDATE ticket SET client_id = ?, room_id = ?, total_price = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

    // --------------------- DELETE ---------------------
    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM ticket WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el ticket con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar el ticket", e);
        }
    }

}
