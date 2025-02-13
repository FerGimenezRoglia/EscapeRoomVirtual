package repositories;

import models.RoomClient;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomClientRepository implements Repository<RoomClient> {
    private final Connection connection;

    private static final String INSERT_ROOM_CLIENT = "INSERT INTO room_client (client_id, room_id, start_time, end_time, completed) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_ROOM_CLIENT_BY_ID = "SELECT * FROM room_client WHERE client_id = ?";
    private static final String SELECT_ALL_ROOM_CLIENTS = "SELECT * FROM room_client";
    private static final String UPDATE_ROOM_CLIENT = "UPDATE room_client SET room_id = ?, start_time = ?, end_time = ?, completed = ? WHERE client_id = ? AND room_id = ?";
    private static final String DELETE_ROOM_CLIENT = "DELETE FROM room_client WHERE client_id = ? AND room_id = ?";

    public RoomClientRepository(Connection connection) {
        this.connection = connection;
    }

    private RoomClient mapResultSet(ResultSet resultSet) throws SQLException {
        return new RoomClient(
                resultSet.getInt("client_id"),
                resultSet.getInt("room_id"),
                resultSet.getTimestamp("start_time"),
                resultSet.getTimestamp("end_time"),
                resultSet.getBoolean("completed")
        );
    }

    @Override
    public void add(RoomClient roomClient) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, roomClient.getClientId());
            statement.setInt(2, roomClient.getRoomId());
            statement.setTimestamp(3, roomClient.getStartTime());
            statement.setTimestamp(4, roomClient.getEndTime());
            statement.setBoolean(5, roomClient.isCompleted());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar la relación room_client.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    roomClient.setClientId(keys.getInt(1));
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar la relación room_client", e);
        }
    }

    @Override
    public RoomClient getById(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROOM_CLIENT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                } else {
                    throw new DataAccessException("No se encontró la relación room_client con client_id " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la relación room_client con client_id " + id, e);
        }
    }

    @Override
    public List<RoomClient> getAll() throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROOM_CLIENTS);
             ResultSet resultSet = statement.executeQuery()) {

            List<RoomClient> roomClients = new ArrayList<>();

            while (resultSet.next()) {
                roomClients.add(mapResultSet(resultSet));
            }

            return roomClients;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener todas las relaciones room_client", e);
        }
    }

    @Override
    public void update(RoomClient roomClient) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROOM_CLIENT)) {
            statement.setInt(1, roomClient.getRoomId());
            statement.setTimestamp(2, roomClient.getStartTime());
            statement.setTimestamp(3, roomClient.getEndTime());
            statement.setBoolean(4, roomClient.isCompleted());
            statement.setInt(5, roomClient.getClientId());
            statement.setInt(6, roomClient.getRoomId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la relación room_client con client_id " + roomClient.getClientId() + " y room_id " + roomClient.getRoomId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar la relación room_client", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Debe llamar a delete con clientId y roomId");
    }

    public void delete(int clientId, int roomId) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROOM_CLIENT)) {
            statement.setInt(1, clientId);
            statement.setInt(2, roomId);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la relación room_client con client_id " + clientId + " y room_id " + roomId);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar la relación room_client", e);
        }
    }
}