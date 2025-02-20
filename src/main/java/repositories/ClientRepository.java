package repositories;

import models.Client;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements Repository<Client> {
    private final Connection connection;

    // 🔹 Definir las consultas SQL como constantes
    private static final String INSERT_CLIENT = "INSERT INTO client (name, email, is_subscribed) VALUES (?, ?, ?)";
    private static final String SELECT_CLIENT_BY_ID = "SELECT * FROM client WHERE id = ?";
    private static final String SELECT_ALL_CLIENTS = "SELECT * FROM client";
    private static final String UPDATE_CLIENT = "UPDATE client SET name = ?, email = ?, is_subscribed = ? WHERE id = ?";
    private static final String DELETE_CLIENT = "DELETE FROM client WHERE id = ?";

    public ClientRepository(Connection connection) {
        this.connection = connection;
    }

    private Client mapResultSet(ResultSet resultSet) throws SQLException {
        return new Client(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("email"),
                resultSet.getBoolean("is_subscribed"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }

    // --------------------- CREATE ---------------------
    @Override
    public void add(Client client) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setBoolean(3, client.isSubscribed());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar el cliente.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    client.setId(keys.getInt(1));
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar el cliente", e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public Client getById(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_CLIENT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                } else {
                    throw new DataAccessException("No se encontró el cliente con ID " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener el cliente con ID " + id, e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public List<Client> getAll() throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_CLIENTS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Client> clients = new ArrayList<>();

            while (resultSet.next()) {
                clients.add(mapResultSet(resultSet));
            }

            return clients;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la lista de clientes", e);
        }
    }

    // --------------------- UPDATE ---------------------
    @Override
    public void update(Client client) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENT)) {
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setBoolean(3, client.isSubscribed());
            statement.setInt(4, client.getId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el cliente con ID " + client.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar el cliente", e);
        }
    }

    // --------------------- DELETE ---------------------
    @Override
    public void delete(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_CLIENT)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró el cliente con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar el cliente", e);
        }
    }
}