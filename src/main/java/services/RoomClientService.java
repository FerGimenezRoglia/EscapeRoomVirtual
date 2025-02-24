package services;

import exceptions.DataAccessException;
import models.RoomClient;
import repositories.RoomClientRepository;
import java.sql.Timestamp;

public class RoomClientService {
    private final RoomClientRepository roomClientRepository;

    public RoomClientService(RoomClientRepository roomClientRepository) {
        this.roomClientRepository = roomClientRepository;
    }

    public void markRoomAsStarted(int clientId, int roomId) throws DataAccessException {
        RoomClient roomClient = new RoomClient(clientId, roomId);
        roomClient.setStartTime(new Timestamp(System.currentTimeMillis()));
        roomClientRepository.add(roomClient);
    }

    public void markRoomAsCompleted(int clientId, int roomId) throws DataAccessException {
        RoomClient roomClient = roomClientRepository.getByIds(clientId, roomId);

        if (roomClient == null) {
            throw new DataAccessException("No existe relación entre el cliente y la sala.");
        }

        if (roomClient.isCompleted()) {
            throw new DataAccessException("El cliente ya ha completado esta sala.");
        }

        roomClient.setCompleted(true);
        roomClient.setEndTime(new Timestamp(System.currentTimeMillis()));

        roomClientRepository.update(roomClient);
    }

    public boolean hasCompletedRoom(int clientId, int roomId) throws DataAccessException {
        RoomClient roomClient = roomClientRepository.getByIds(clientId, roomId);
        return roomClient != null && roomClient.isCompleted();
    }
}