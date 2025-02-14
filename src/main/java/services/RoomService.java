package services;

import models.Room;
import repositories.RoomRepository;
import exceptions.DataAccessException;
import java.sql.Connection;

public class RoomService {
    private final RoomRepository roomRepository;

    public RoomService(Connection connection) {
        this.roomRepository = new RoomRepository(connection);
    }

    public Room addRoom(Integer escapeRoomId, String name, Room.DifficultyLevel difficulty, double price) throws DataAccessException {
        if (escapeRoomId == null || escapeRoomId <= 0) {
            throw new DataAccessException("No se puede agregar una sala sin un Escape Room válido.");
        }
        Room room = new Room();
        room.setEscapeRoomId(escapeRoomId);
        room.setName(name);
        room.setDifficultyLevel(difficulty);
        room.setPrice(price);

        roomRepository.add(room);
        return room;
    }

    public boolean deleteRoom(int roomId) throws DataAccessException {
        try {
            roomRepository.delete(roomId);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
