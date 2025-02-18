package controllers;

import services.RoomService;
import services.DecorationService;
import models.Room;
import exceptions.DataAccessException;

public class ControllerManagement {
    private final RoomService roomService;
    private final DecorationService decorationService;

    // Constructor recibe los servicios
    public ControllerManagement(RoomService roomService, DecorationService decorationService) {
        this.roomService = roomService;
        this.decorationService = decorationService;
    }

    // !Métodos para gestionar salas
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

    // !Métodos para gestionar decoraciones
    public void addDecoration(int roomId, String name, String material, double price) {
        try {
            decorationService.addDecoration(roomId, name, material, price);
            System.out.println("Decoración agregada correctamente.");
        } catch (DataAccessException e) {
            System.err.println("Error al agregar la decoración: " + e.getMessage());
        }
    }

    public boolean deleteDecoration(int decorationId) {
        try {
            return decorationService.deleteDecoration(decorationId);
        } catch (DataAccessException e) {
            System.err.println("Error al eliminar la decoración: " + e.getMessage());
            return false;
        }
    }
}
