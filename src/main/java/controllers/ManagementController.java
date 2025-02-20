package controllers;

import observer.EventNotifier;
import services.RoomService;
import services.DecorationService;
import services.HintService;
import models.Room;
import exceptions.DataAccessException;

public class ManagementController {
    private final RoomService roomService;
    private final DecorationService decorationService;
    private final HintService hintService;
    private final EventNotifier eventNotifier; // 👁🔹👁️

    public ManagementController(RoomService roomService, DecorationService decorationService, HintService hintService, EventNotifier eventNotifier) {
        this.roomService = roomService;
        this.decorationService = decorationService;
        this.hintService = hintService;
        this.eventNotifier = eventNotifier; // 👁🔹👁️ Guardamos el notifier en la clase
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
        } catch (DataAccessException e) {
            System.err.println("Error al agregar la decoración: " + e.getMessage());
        }
    }

    public boolean deleteDecoration(int decorationId) {
        boolean success = decorationService.deleteDecoration(decorationId);
        if (!success) {
            System.err.println("Error al eliminar la decoración.");
        }
        return success;
    }

    // !Métodos para gestionar pistas (Hint)
    public void addHint(int roomId, String description, double price) {
        try {
            hintService.addHint(roomId, description, price);
        } catch (DataAccessException e) {
            System.err.println("Error al agregar la pista: " + e.getMessage());
        }
    }

    public boolean deleteHint(int hintId) {
        boolean success = hintService.deleteHint(hintId);
        if (!success) {
            System.err.println("Error al eliminar la pista.");
        }
        return success;
    }

}
