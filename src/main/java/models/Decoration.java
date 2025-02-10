package models;

public class Decoration {

    private int id;
    private int roomId;
    private String name;
    private String material;
    private double price;

    public Decoration() {
    }

    public Decoration(int id, int roomId, String name, String material, double price) {
        this.id = id;
        this.roomId = roomId;
        this.name = name;
        this.material = material;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public String getMaterial() {
        return material;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Decoration: ID = %d | Room ID = %d | Name = '%s' | Material = '%s' | Price = %.2f €",
                id, roomId, name, material, price);
    }

}


