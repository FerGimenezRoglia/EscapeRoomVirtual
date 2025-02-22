package models;

import java.sql.Timestamp;

public class EscapeRoom {

    private int id;
    private String name;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public EscapeRoom() {}

    public EscapeRoom(int id, String name, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public EscapeRoom(String name) {
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Timestamp updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return String.format("EscapeRoom: ID = %d | Name = '%s' | Created At = %s | Updated At = %s",
                id, name, createdAt, updatedAt);
    }
}
