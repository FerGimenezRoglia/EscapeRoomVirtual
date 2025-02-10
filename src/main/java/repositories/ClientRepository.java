package repositories;

import models.Client;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientRepository implements Repository<Client> {
    private final Connection connection;

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
        // Definir la consulta SQL para insertar un nuevo cliente.
        // Se insertan los campos: name, email y is_subscribed.
        String sql = "INSERT INTO client (name, email, is_subscribed) VALUES (?, ?, ?)";

        // Usar un PreparedStatement para ejecutar la consulta de manera segura.
        // Statement.RETURN_GENERATED_KEYS permite recuperar el ID generado automáticamente.
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Asignar los valores del cliente a los parámetros de la consulta.
            statement.setString(1, client.getName());
            statement.setString(2, client.getEmail());
            statement.setBoolean(3, client.isSubscribed());
            //**No se incluyen created_at y updated_at porque suelen manejarse automáticamente en la base de datos.

            // Ejecutar la consulta y verificar si se insertó correctamente.
            // executeUpdate() devuelve el número de filas afectadas.
            if (statement.executeUpdate() == 0) {
                // Si no se afectó ninguna fila, lanzar una excepción.
                throw new DataAccessException("No se pudo insertar el cliente.");
            }

            // Recuperar el ID generado automáticamente por la base de datos.
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    // Asignar el ID generado al objeto Client.
                    client.setId(keys.getInt(1));

                    // Nueva consulta para recuperar created_at y updated_at
                    String fetchSql = "SELECT created_at, updated_at FROM client WHERE id = ?";
                    try (PreparedStatement fetchStatement = connection.prepareStatement(fetchSql)) {
                        fetchStatement.setInt(1, client.getId());
                        try (ResultSet rs = fetchStatement.executeQuery()) {
                            if (rs.next()) {
                                client.setCreatedAt(rs.getTimestamp("created_at"));
                                client.setUpdatedAt(rs.getTimestamp("updated_at"));
                            }
                        }
                    }

                } else {
                    // Si no se pudo obtener el ID, lanzar una excepción.
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            // Capturar y relanzar cualquier excepción de SQL como DataAccessException.
            throw new DataAccessException("Error al agregar el cliente", e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public Client getById(int id) throws DataAccessException {
        String sql = "SELECT * FROM client WHERE id = ?";

        // Usar PreparedStatement para prevenir inyección SQL y garantizar seguridad.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id); // Asignar el ID como parámetro.

            // Ejecutar la consulta y obtener el ResultSet.
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Si se encuentra el cliente, mapear y devolver el objeto Client.
                    return mapResultSet(resultSet);
                } else {
                    // Si no se encuentra el cliente, lanzar una excepción.
                    throw new DataAccessException("No se encontró el cliente con ID " + id);
                }
            }
        } catch (SQLException e) {
            // Capturar y relanzar cualquier excepción de SQL como DataAccessException.
            throw new DataAccessException("Error al obtener el cliente con ID " + id, e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public List<Client> getAll() throws DataAccessException {
        // Definir la consulta SQL para obtener todos los clientes.
        String sql = "SELECT * FROM client";

        // Usar PreparedStatement para ejecutar la consulta de manera segura.
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            // Crear una lista para almacenar los clientes recuperados.
            List<Client> clients = new ArrayList<>();

            // Recorrer el ResultSet y mapear cada fila a un objeto Client.
            while (resultSet.next()) {
                clients.add(mapResultSet(resultSet));
            }

            // Devolver la lista de clientes.
            return clients;
        } catch (SQLException e) {
            // Capturar y relanzar cualquier excepción de SQL como DataAccessException.
            throw new DataAccessException("Error al obtener la lista de clientes", e);
        }
    }

    // --------------------- UPDATE ---------------------
    @Override
    public void update(Client client) throws DataAccessException {
        // Definir la consulta SQL para actualizar un cliente existente.
        // Se actualizan los campos: name, email y is_subscribed.
        String sql = "UPDATE client SET name = ?, email = ?, is_subscribed = ? WHERE id = ?";

        // Usar PreparedStatement para ejecutar la consulta de manera segura.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // Asignar los valores del cliente a los parámetros de la consulta.
            statement.setString(1, client.getName());      // Asignar el nombre.
            statement.setString(2, client.getEmail());     // Asignar el email.
            statement.setBoolean(3, client.isSubscribed()); // Asignar el estado de suscripción.
            statement.setInt(4, client.getId());           // Asignar el ID del cliente a actualizar.

            // Ejecutar la consulta y verificar si se actualizó correctamente.
            // executeUpdate() devuelve el número de filas afectadas.
            if (statement.executeUpdate() == 0) {
                // Si no se encontró el cliente, lanzar una excepción.
                throw new DataAccessException("No se encontró el cliente con ID " + client.getId());
            }
        } catch (SQLException e) {
            // Capturar y relanzar cualquier excepción de SQL como DataAccessException.
            throw new DataAccessException("Error al actualizar el cliente", e);
        }
    }

    // --------------------- DELETE ---------------------
    @Override
    public void delete(int id) throws DataAccessException {
        // Definir la consulta SQL para eliminar un cliente por su ID.
        String sql = "DELETE FROM client WHERE id = ?";

        // Usar PreparedStatement para ejecutar la consulta de manera segura y evitar inyección SQL.
        try (PreparedStatement statement = connection.prepareStatement(sql)) {

            // Asignar el ID del cliente al parámetro de la consulta.
            statement.setInt(1, id);

            // Ejecutar la eliminación y verificar si se afectó alguna fila.
            if (statement.executeUpdate() == 0) {
                // Si no se encontró el cliente, lanzar una excepción.
                throw new DataAccessException("No se encontró el cliente con ID " + id);
            }
        } catch (SQLException e) {
            // Capturar y relanzar cualquier excepción de SQL como DataAccessException.
            throw new DataAccessException("Error al eliminar el cliente", e);
        }
    }
}
