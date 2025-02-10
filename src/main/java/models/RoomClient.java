package models;

public class RoomClient {

    private int clientId;
    private int roomId;
    private String startTime;
    private String endTime;
    private boolean completed;

    public RoomClient() {
    }

    public RoomClient(int clientId, int roomId, String startTime, String endTime, boolean completed) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.completed = completed;
    }

    public int getClientId() {
        return clientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
