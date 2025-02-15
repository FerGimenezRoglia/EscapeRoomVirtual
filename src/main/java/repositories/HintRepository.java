package repositories;

import models.Hint;
import exceptions.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HintRepository implements Repository<Hint> {
    private final Connection connection;

    private static final String INSERT_HINT = "INSERT INTO hint (room_id, description, price) VALUES (?, ?, ?)";
    private static final String FETCH_CREATED = "SELECT created_at FROM hint WHERE id = ?";
    private static final String SELECT_HINT_BY_ID = "SELECT * FROM hint WHERE id = ?";
    private static final String SELECT_ALL_HINTS = "SELECT * FROM hint";
    private static final String UPDATE_HINT = "UPDATE hint SET description = ?, price = ? WHERE id = ?";
    private static final String DELETE_HINT = "DELETE FROM hint WHERE id = ?";

    public HintRepository(Connection connection) {
        this.connection = connection;
    }

    private Hint mapResultSet(ResultSet resultSet) throws SQLException {
        return new Hint(
                resultSet.getInt("id"),
                resultSet.getInt("room_id"),
                resultSet.getString("description"),
                resultSet.getDouble("price"),
                resultSet.getTimestamp("created_at")
        );
    }

    @Override
    public void add(Hint hint) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_HINT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, hint.getRoomId());
            statement.setString(2, hint.getDescription());
            statement.setDouble(3, hint.getPrice());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se pudo insertar la pista.");
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    hint.setId(keys.getInt(1));

                    try (PreparedStatement fetchStatement = connection.prepareStatement(FETCH_CREATED)) {
                        fetchStatement.setInt(1, hint.getId());
                        try (ResultSet rs = fetchStatement.executeQuery()) {
                            if (rs.next()) {
                                hint.setCreatedAt(rs.getTimestamp("created_at"));
                            }
                        }
                    }
                } else {
                    throw new DataAccessException("No se pudo obtener el ID generado.");
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al agregar la pista", e);
        }
    }

    @Override
    public Hint getById(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_HINT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapResultSet(resultSet);
                } else {
                    throw new DataAccessException("No se encontró la pista con ID " + id);
                }
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la pista con ID " + id, e);
        }
    }

    @Override
    public List<Hint> getAll() throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_HINTS);
             ResultSet resultSet = statement.executeQuery()) {

            List<Hint> hints = new ArrayList<>();
            while (resultSet.next()) {
                hints.add(mapResultSet(resultSet));
            }
            return hints;
        } catch (SQLException e) {
            throw new DataAccessException("Error al obtener la lista de pistas", e);
        }
    }

    @Override
    public void update(Hint hint) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_HINT)) {
            statement.setString(1, hint.getDescription());
            statement.setDouble(2, hint.getPrice());
            statement.setInt(3, hint.getId());

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la pista con ID " + hint.getId());
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar la pista", e);
        }
    }

    @Override
    public void delete(int id) throws DataAccessException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_HINT)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() == 0) {
                throw new DataAccessException("No se encontró la pista con ID " + id);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error al eliminar la pista", e);
        }
    }
}