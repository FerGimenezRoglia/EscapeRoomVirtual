package controllers;

import services.RoomClientService;
import exceptions.DataAccessException;

public class UserController {
    private final RoomClientService roomClientService;

    public UserController(RoomClientService roomClientService) {
        this.roomClientService = roomClientService;
    }

    public void markRoomAsCompleted(int clientId, int roomId) {
        try {
            roomClientService.markRoomAsCompleted(clientId, roomId);
            System.out.println("☑️ La sala ha sido marcada como completada.");
        } catch (DataAccessException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void giveCertificate(int clientId, int roomId) {
        try {
            if (roomClientService.hasCompletedRoom(clientId, roomId)) {
                System.out.println("\n🎉 ¡Felicidades! 🎉");
                System.out.println("📜 Cliente " + clientId + " ha recibido un certificado por completar la sala " + roomId);
            } else {
                System.out.println("⚠️ No se puede otorgar el certificado. El cliente no ha completado la sala.");
            }
        } catch (DataAccessException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public void giveReward(int clientId, int roomId) {
        try {
            if (roomClientService.hasCompletedRoom(clientId, roomId)) {
                System.out.println("\n🎁 ¡Recompensa otorgada! 🎁");
                System.out.println("Cliente " + clientId + " ha recibido una recompensa especial por completar la sala " + roomId);
            } else {
                System.out.println("⚠️ No se puede otorgar la recompensa. El cliente no ha completado la sala.");
            }
        } catch (DataAccessException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}