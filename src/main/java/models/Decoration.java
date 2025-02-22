package models;

import java.sql.Timestamp;

public class Decoration {

    private int id;
    private int roomId;
    private String name;
    private String material;
    private double price;
    private Timestamp createdAt;

    public Decoration() {}


    public Decoration(int id, int roomId, String name, String material, double price, Timestamp createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.material = material;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Decoration(int roomId, String name, String material, double price) {
        this.roomId = roomId;
        this.name = name;
        this.material = material;
        this.price = price;
    }

    public int getId() { return id; }
    public int getRoomId() { return roomId; }
    public String getName() { return name; }
    public String getMaterial() { return material; }
    public double getPrice() { return price; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) {this.id = id;}
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setName(String name) { this.name = name; }
    public void setMaterial(String material) { this.material = material; }
    public void setPrice(double price) { this.price = price; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return String.format("Decoration: ID = %d | RoomID = %d | Name = '%s' | Material = '%s' | Price = %.2f€ | Created At = %s",
                id, roomId, name, material != null ? material : "NULL", price, createdAt);
    }
}
