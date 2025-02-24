package services;

import models.Hint;
import repositories.HintRepository;
import exceptions.DataAccessException;
import java.sql.Connection;

public class HintService {
    private final HintRepository hintRepository;

    public HintService(Connection connection) {
        this.hintRepository = new HintRepository(connection);
    }

    public void addHint(int roomId, String description, double price) {
        if (roomId <= 0) {
            throw new DataAccessException("No se puede agregar una pista sin una sala válida.");
        }
        Hint hint = new Hint(roomId, description, price);
        hintRepository.add(hint);
    }

    public boolean deleteHint(int hintId) {
        try {
            hintRepository.delete(hintId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
