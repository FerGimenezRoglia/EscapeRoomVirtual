package models;

import java.sql.Timestamp;

public class RoomClient {

    private int clientId;
    private int roomId;
    private Timestamp startTime; // Timestamp mejor opción en Java para manejar fechas y horas con precisión
    private Timestamp endTime; // Timestamp mejor opción en Java para manejar fechas y horas con precisión
    private boolean completed;

    // Constructor vacío
    public RoomClient() {}

    // Constructor con todos los atributos (para recuperar datos de la BD)
    public RoomClient(int clientId, int roomId, Timestamp startTime, Timestamp endTime, boolean completed) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completed = completed;
    }

    //!-------- NO es redundante tener los dos constructores en RoomClient.
    //! Cada uno cumple un propósito específico en la estructura de datos y el flujo del repositorio
    //! --------//

    // Constructor para nuevos registros (sin timestamps ni endTime)
    public RoomClient(int clientId, int roomId) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.completed = false; // Por defecto, una reserva no está completada
    }

    // Getters
    public int getClientId() { return clientId; }
    public int getRoomId() { return roomId; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public boolean isCompleted() { return completed; }

    // Setters
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("RoomClient: ClientID = %d | RoomID = %d | Start Time = %s | End Time = %s | Completed = %b",
                clientId, roomId, startTime, endTime != null ? endTime : "NULL", completed);
    }
}
