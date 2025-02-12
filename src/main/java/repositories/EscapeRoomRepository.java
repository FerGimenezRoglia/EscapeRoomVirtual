package repositories;

import models.EscapeRoom;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EscapeRoomRepository implements Repository<EscapeRoom> {
    private final Connection connection;

    public EscapeRoomRepository(Connection connection) {
        this.connection = connection;
    }

    private EscapeRoom mapResultSet(ResultSet resultSet) throws SQLException {
        return new EscapeRoom(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getTimestamp("created_at"),
                resultSet.getTimestamp("updated_at")
        );
    }

    @Override
    public void add(EscapeRoom escapeRoom) throws DataAccessException {
        String sql = "INSERT INTO escape_room (name) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, escapeRoom.getName());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar la Escape Room.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    escapeRoom.setId(keys.getInt(1));

                    // Nueva consulta para recuperar created_at y updated_at
                    String fetchSql = "SELECT created_at, updated_at FROM escape_room WHERE id = ?";
                    try (PreparedStatement fetchStatement = connection.prepareStatement(fetchSql)) {
                        fetchStatement.setInt(1, escapeRoom.getId());
                        try (ResultSet rs = fetchStatement.executeQuery()) {
                            if (rs.next()) {
                                escapeRoom.setCreatedAt(rs.getTimestamp("created_at"));
                                escapeRoom.setUpdatedAt(rs.getTimestamp("updated_at"));
                            }
                        }
                    }
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar la Escape Room", e);
        }
    }

    @Override
    public EscapeRoom getById(int id) throws DataAccessException {
        String sql = "SELECT * FROM escape_room WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                } else {
                    throw new DataAccessException("No se encontró la Escape Room con ID " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la Escape Room con ID " + id, e);
        }
    }

    @Override
    public List<EscapeRoom> getAll() throws DataAccessException {
        String sql = "SELECT * FROM escape_room";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<EscapeRoom> escapeRooms = new ArrayList<>();
            while (resultSet.next()) {
                escapeRooms.add(mapResultSet(resultSet));
            }
            return escapeRooms;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la lista de Escape Rooms", e);
        }
    }

    @Override
    public void update(EscapeRoom escapeRoom) throws DataAccessException {
        String sql = "UPDATE escape_room SET name = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, escapeRoom.getName());
            statement.setInt(2, escapeRoom.getId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la Escape Room con ID " + escapeRoom.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar la Escape Room", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM escape_room WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la Escape Room con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar la Escape Room", e);
        }
    }
}