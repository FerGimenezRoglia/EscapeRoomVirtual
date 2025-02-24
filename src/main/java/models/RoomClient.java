package models;

import java.sql.Timestamp;

public class RoomClient {

    private int clientId;
    private int roomId;
    private Timestamp startTime;
    private Timestamp endTime;
    private boolean completed;

    public RoomClient() {}

    public RoomClient(int clientId, int roomId, Timestamp startTime, Timestamp endTime, boolean completed) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completed = completed;
    }

    public RoomClient(int clientId, int roomId) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.completed = false;
    }

    public int getClientId() { return clientId; }
    public int getRoomId() { return roomId; }
    public Timestamp getStartTime() { return startTime; }
    public Timestamp getEndTime() { return endTime; }
    public boolean isCompleted() { return completed; }

    public void setClientId(int clientId) {this.clientId = clientId;}
    public void setRoomId(int roomId) {this.roomId = roomId;}
    public void setStartTime(Timestamp startTime) { this.startTime = startTime; }
    public void setEndTime(Timestamp endTime) { this.endTime = endTime; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("RoomClient: ClientID = %d | RoomID = %d | Start Time = %s | End Time = %s | Completed = %b",
                clientId, roomId, startTime, endTime != null ? endTime : "NULL", completed);
    }
}