package services;

import models.Decoration;
import repositories.DecorationRepository;
import exceptions.DataAccessException;
import java.sql.Connection;

public class DecorationService {
    private final DecorationRepository decorationRepository;
    public DecorationService(Connection connection) {
        this.decorationRepository = new DecorationRepository(connection);
    }

    public void addDecoration(int roomId, String name, String material, double price) {
        if (roomId <= 0) {
            throw new DataAccessException("No se puede agregar una decoración sin una sala válida.");
        }
        Decoration decoration = new Decoration(roomId, name, material, price);
        decorationRepository.add(decoration);
    }

    public boolean deleteDecoration(int decorationId) {
        try {
            decorationRepository.delete(decorationId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
