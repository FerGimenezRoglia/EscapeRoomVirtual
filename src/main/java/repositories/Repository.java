package repositories;

import exceptions.DataAccessException;
import java.util.List;

public interface Repository<T> {

    void add(T entity) throws DataAccessException;
    List<T> getAll() throws DataAccessException;
    void update(T entity) throws DataAccessException;

    default void delete(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Este repositorio no usa claves simples. Usa delete(id1, id2).");
    }

    default void delete(int id1, int id2) throws DataAccessException {
        throw new UnsupportedOperationException("Este repositorio no usa claves compuestas.");
    }

    default T getById(int id) throws DataAccessException {
        throw new UnsupportedOperationException("Este repositorio no usa claves simples. Usa getByIds().");
    }

    default T getByIds(int id1, int id2) throws DataAccessException {
        throw new UnsupportedOperationException("Este repositorio no usa claves compuestas.");
    }
}