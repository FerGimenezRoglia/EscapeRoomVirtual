package models;

import java.sql.Timestamp;

public class Client {

    private int id;
    private String name;
    private String email;
    private boolean isSubscribed;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Client() {
    }

    public Client(int id, String name, String email, boolean isSubscribed, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isSubscribed = isSubscribed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Client(String name, String email, boolean isSubscribed) {
        this.name = name;
        this.email = email;
        this.isSubscribed = isSubscribed;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public boolean isSubscribed() { return isSubscribed; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setSubscribed(boolean subscribed) { isSubscribed = subscribed; }
    public void setId(int id) { this.id = id; }
    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}
    public void setUpdatedAt(Timestamp updatedAt) {this.updatedAt = updatedAt;}

    @Override
    public String toString() {
        return String.format("Client: ID = %d | Name = '%s' | Email = '%s' | Subscribed = %b | Created At = %s | Updated At = %s",
                id, name, email, isSubscribed, createdAt, updatedAt);
    }
}
