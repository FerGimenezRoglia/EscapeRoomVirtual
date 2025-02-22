package repositories;

import models.Room;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements Repository<Room> {
    private final Connection connection;

    private static final String INSERT_ROOM = "INSERT INTO room (escape_room_id, name, difficulty_level, price) VALUES (?, ?, ?, ?)";
    private static final String FETCH_CREATED_UPDATED = "SELECT created_at, updated_at FROM room WHERE id = ?";
    private static final String SELECT_ROOM_BY_ID = "SELECT * FROM room WHERE id = ?";
    private static final String SELECT_ALL_ROOMS = "SELECT * FROM room";
    private static final String UPDATE_ROOM = "UPDATE room SET name = ?, difficulty_level = ?, price = ? WHERE id = ?";
    private static final String DELETE_ROOM = "DELETE FROM room WHERE id = ?";

    public RoomRepository(Connection connection) {
        this.connection = connection;
    }

    private Room mapResultSet(ResultSet resultSet) throws SQLException {
        return new Room(
                resultSet.getInt("id"),
                resultSet.getInt("escape_room_id"),
                resultSet.getString("name"),
                Room.DifficultyLevel.valueOf(resultSet.getString("difficulty_level")),
                resultSet.getDouble("price"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }

    @Override
    public void add(Room room) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_ROOM, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, room.getEscapeRoomId());
            statement.setString(2, room.getName());
            statement.setString(3, room.getDifficultyLevel().name());
            statement.setDouble(4, room.getPrice());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar la sala.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    room.setId(keys.getInt(1));

                    try (PreparedStatement fetchStatement = connection.prepareStatement(FETCH_CREATED_UPDATED)) {
                        fetchStatement.setInt(1, room.getId());
                        try (ResultSet rs = fetchStatement.executeQuery()) {
                            if (rs.next()) {
                                room.setCreatedAt(rs.getTimestamp("created_at"));
                                room.setUpdatedAt(rs.getTimestamp("updated_at"));
                            }
                        }
                    }
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar la sala", e);
        }
    }

    @Override
    public Room getById(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ROOM_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                } else {
                    throw new DataAccessException("No se encontró la sala con ID " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la sala con ID " + id, e);
        }
    }

    @Override
    public List<Room> getAll() throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_ROOMS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Room> rooms = new ArrayList<>();
            while (resultSet.next()) {
                rooms.add(mapResultSet(resultSet));
            }
            return rooms;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la lista de salas", e);
        }
    }

    @Override
    public void update(Room room) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_ROOM)) {
            statement.setString(1, room.getName());
            statement.setString(2, room.getDifficultyLevel().name()); // Convertir Enum a String
            statement.setDouble(3, room.getPrice());
            statement.setInt(4, room.getId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la sala con ID " + room.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar la sala", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_ROOM)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la sala con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar la sala", e);
        }
    }
}