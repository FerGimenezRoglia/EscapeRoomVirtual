package controllers;

import models.Room;
import services.RoomService;

public class Operaciones {
    private final RoomService roomService;

    public Operaciones(RoomService clientService) {
        this.roomService = clientService;
    }

    public void gestionarSalas() {
        // Lógica para gestionar clientes
        roomService.addRoom(1, "Sala 1", Room.DifficultyLevel.EASY, 100.0);
        roomService.deleteRoom(1);
    }
}