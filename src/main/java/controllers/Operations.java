package controllers;

import models.Room;
import services.RoomService;

public class Operations {
    private final RoomService roomService;

    public Operations(RoomService clientService) {
        this.roomService = clientService;
    }

    public void RoomManagement() {
        // Lógica para gestionar clientes
        roomService.addRoom(1, "Sala 1", Room.DifficultyLevel.EASY, 100.0);
        roomService.deleteRoom(1);
    }
}