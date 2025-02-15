package repositories;

import exceptions.DataAccessException;
import java.util.List;

public interface Repository<T> {

    void add(T entity) throws DataAccessException;
    List<T> getAll() throws DataAccessException;
    void update(T entity) throws DataAccessException;
    void delete(int id) throws DataAccessException;

    // ✅ getById ya maneja internamente si debe lanzarse una excepción
    default T getById(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Este repositorio no usa claves simples. Usa getByIds().");
    }

    // 🔹 Método para claves compuestas
    default T getByIds(int id1, int id2) throws DataAccessException {
        throw new UnsupportedOperationException("Este repositorio no usa claves compuestas.");
    }
}
