package repositories;

import models.Decoration;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DecorationRepository implements Repository<Decoration> {
    private final Connection connection;

    public DecorationRepository(Connection connection) {
        this.connection = connection;
    }

    private Decoration mapResultSet(ResultSet resultSet) throws SQLException {
        return new Decoration(
                resultSet.getInt("id"),
                resultSet.getInt("room_id"), // Relación con la tabla room
                resultSet.getString("name"),
                resultSet.getString("material"),
                resultSet.getDouble("price"),
                resultSet.getTimestamp("created_at")
        );
    }

    // --------------------- CREATE ---------------------
    @Override
    public void add(Decoration decoration) throws DataAccessException {
        String sql = "INSERT INTO decoration (room_id, name, material, price) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, decoration.getRoomId());
            statement.setString(2, decoration.getName());
            statement.setString(3, decoration.getMaterial());
            statement.setDouble(4, decoration.getPrice());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar la decoración.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    decoration.setId(keys.getInt(1));

                    String fetchSql = "SELECT created_at FROM decoration WHERE id = ?";
                    try (PreparedStatement fetchStatement = connection.prepareStatement(fetchSql)) {
                        fetchStatement.setInt(1, decoration.getId());
                        try (ResultSet rs = fetchStatement.executeQuery()) {
                            if (rs.next()) {
                                decoration.setCreatedAt(rs.getTimestamp("created_at"));
                            }
                        }
                    }
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar la decoración", e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public Decoration getById(int id) throws DataAccessException {
        String sql = "SELECT * FROM decoration WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {

                    return mapResultSet(resultSet);
                } else {

                    throw new DataAccessException("No se encontró la decoración con ID " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la decoración con ID " + id, e);
        }
    }

    // --------------------- READ ---------------------
    @Override
    public List<Decoration> getAll() throws DataAccessException {
        String sql = "SELECT * FROM decoration";

        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            List<Decoration> decorations = new ArrayList<>();

            while (resultSet.next()) {
                decorations.add(mapResultSet(resultSet));
            }

            return decorations;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener todas las decoraciones", e);
        }
    }

    // --------------------- UPDATE ---------------------
    @Override
    public void update(Decoration decoration) throws DataAccessException {
        String sql = "UPDATE decoration SET room_id = ?, name = ?, material = ?, price = ? WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, decoration.getRoomId());
            statement.setString(2, decoration.getName());
            statement.setString(3, decoration.getMaterial());
            statement.setDouble(4, decoration.getPrice());
            statement.setInt(5, decoration.getId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la decoración con ID " + decoration.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar la decoración", e);
        }
    }

    // --------------------- DELETE ---------------------
    @Override
    public void delete(int id) throws DataAccessException {
        String sql = "DELETE FROM decoration WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la decoración con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar la decoración", e);
        }
    }
}