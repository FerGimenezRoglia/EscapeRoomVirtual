package models;

import java.sql.Timestamp;

public class Room {

    public enum DifficultyLevel { EASY, MEDIUM, HARD }

    private int id;
    private Integer escapeRoomId;
    private String name;
    private DifficultyLevel difficultyLevel;
    private double price;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Room() {}

    public Room(int id, Integer escapeRoomId, String name, DifficultyLevel difficultyLevel, double price, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.escapeRoomId = escapeRoomId;
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Room(Integer escapeRoomId, String name, DifficultyLevel difficultyLevel, double price) {
        this.escapeRoomId = escapeRoomId;
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
    }

    public int getId() { return id; }
    public Integer getEscapeRoomId() { return escapeRoomId; }
    public String getName() { return name; }
    public DifficultyLevel getDifficultyLevel() { return difficultyLevel; }
    public double getPrice() { return price; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }


    public void setId(int id) { this.id = id; }
    public void setEscapeRoomId(Integer escapeRoomId) { this.escapeRoomId = escapeRoomId; }
    public void setName(String name) { this.name = name; }
    public void setDifficultyLevel(DifficultyLevel difficultyLevel) { this.difficultyLevel = difficultyLevel; }
    public void setPrice(double price) { this.price = price; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return String.format("Room: ID = %d | EscapeRoomID = %s | Name = '%s' | Difficulty = %s | Price = %.2f€ | Created At = %s | Updated At = %s",
                id, escapeRoomId != null ? escapeRoomId : "NULL", name, difficultyLevel, price, createdAt, updatedAt);
    }
}
