package repositories;

import exceptions.DataAccessException;
import java.util.List;

public interface Repository<T> {

    // --------------------- CREATE ---------------------
    void add(T entity) throws DataAccessException;

    // --------------------- READ ---------------------
    T getById(int id) throws DataAccessException;

    List<T> getAll() throws DataAccessException;

    // --------------------- UPDATE ---------------------
    void update(T entity) throws DataAccessException;

    // --------------------- DELETE ---------------------
    void delete(int id) throws DataAccessException;
}