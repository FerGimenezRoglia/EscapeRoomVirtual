package models;

public class Room {

    private int id;
    private int escapeRoomId;
    private String name;
    private String difficultyLevel;
    private double price;

    public Room() {
    }

    public Room(int id, int escapeRoomId, String name, String difficultyLevel, double price) {
        this.id = id;
        this.escapeRoomId = escapeRoomId;
        this.name = name;
        this.difficultyLevel = difficultyLevel;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getEscapeRoomId() {
        return escapeRoomId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Room: id = %d | escapeRoomId = %d | name = '%s' | difficultyLevel = '%s' | price = %.2f €",
                id, escapeRoomId, name, difficultyLevel, price);
    }
}
