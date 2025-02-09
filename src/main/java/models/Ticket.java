package models;

public class Ticket {

    private int id;
    private int clientId;
    private int roomId;
    private double totalPrice;
    private String purchaseDate;

    public Ticket() {
    }

    public Ticket(int id, int clientId, int roomId, double totalPrice, String purchaseDate) {
        this.id = id;
        this.clientId = clientId;
        this.roomId = roomId;
        this.totalPrice = totalPrice;
        this.purchaseDate = purchaseDate;
    }

    public int getId() {
        return id;
    }

    public int getClientId() {
        return clientId;
    }

    public int getRoomId() {
        return roomId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return String.format("Ticket: ID = %d | Client ID = %d | Room ID = %d | Total Price = %.2f € | Purchase Date = %s",
                id, clientId, roomId, totalPrice, purchaseDate);
    }

}
