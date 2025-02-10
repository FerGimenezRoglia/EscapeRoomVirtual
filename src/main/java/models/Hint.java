package models;

public class Hint {

    private int id;
    private int roomId;
    private String description;
    private double price;

    public Hint() {
    }

    public Hint(int id, int roomId, String description, double price) {
        this.id = id;
        this.roomId = roomId;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getRoomId() {
        return roomId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return String.format("Hint: ID = %d | Room ID = %d | Description = '%s' | Price = %.2f €",
                id, roomId, description, price);
    }

}
