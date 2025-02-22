package models;

import java.sql.Timestamp;

public class Hint {

    private int id;
    private int roomId;
    private String description;
    private double price;
    private Timestamp createdAt;

    public Hint() {}

    public Hint(int id, int roomId, String description, double price, Timestamp createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Hint(int roomId, String description, double price) {
        this.roomId = roomId;
        this.description = description;
        this.price = price;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setDescription(String description) { this.description = description; }
    public void setPrice(double price) { this.price = price; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("Hint: ID = %d | RoomID = %d | Description = '%s' | Price = %.2f€ | Created At = %s",
                id, roomId, description, price, createdAt);
    }
}