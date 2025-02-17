package controllers;

import services.RoomService;

import models.Room;
import exceptions.DataAccessException;

public class ControllerManagement {
    private final RoomService roomService;

    // Constructor: Recibe todos los servicios
    public ControllerManagement(RoomService roomService) {
        this.roomService = roomService;

    }

    // Métodos para gestionar salas
    public void addRoom(int escapeRoomId, String name, String difficulty, double price) {
        try {
            Room.DifficultyLevel difficultyLevel = Room.DifficultyLevel.valueOf(difficulty);
            roomService.addRoom(escapeRoomId, name, difficultyLevel, price);
        } catch (DataAccessException e) {
            System.err.println("Error al agregar la sala: " + e.getMessage());
        }
    }

    public boolean deleteRoom(int roomId) {
        try {
            return roomService.deleteRoom(roomId);
        } catch (DataAccessException e) {
            System.err.println("Error al eliminar la sala: " + e.getMessage());
            return false;
        }
    }

}