package models;

import java.sql.Timestamp;

public class Ticket {

    private int id;
    private int clientId;
    private int roomId;
    private double totalPrice;
    private Timestamp purchaseDate; // Timestamp mejor opción en Java para manejar fechas y horas con precisión

    // Constructor vacío
    public Ticket() {}

    // Constructor con todos los atributos (para recuperar datos de la BD)
    public Ticket(int id, int clientId, int roomId, double totalPrice, Timestamp purchaseDate) {
        this.id = id;
        this.clientId = clientId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    // Constructor para nuevos registros (sin ID ni timestamp)
    public Ticket(int clientId, int roomId, double totalPrice) {
        this.clientId = clientId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
    }

    // Getters
    public int getId() { return id; }
    public int getClientId() { return clientId; }
    public int getRoomId() { return roomId; }
    public double getTotalPrice() { return totalPrice; }
    public Timestamp getPurchaseDate() { return purchaseDate; }

    // Setters
    public void setClientId(int clientId) { this.clientId = clientId; }
    public void setRoomId(int roomId) { this.roomId = roomId; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
    public void setPurchaseDate(Timestamp purchaseDate) { this.purchaseDate = purchaseDate; }

    @Override
    public String toString() {
        return String.format("Ticket: ID = %d | ClientID = %d | RoomID = %d | Total Price = %.2f€ | Purchase Date = %s",
                id, clientId, roomId, totalPrice, purchaseDate);
    }
}
