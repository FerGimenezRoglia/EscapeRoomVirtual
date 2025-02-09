package models;

public class Client {

    private int id;
    private String name;
    private String email;
    private boolean isSubscribed;

    public Client() {
    }

    public Client(int id, String name, String email, boolean isSubscribed) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.isSubscribed = isSubscribed;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    @Override
    public String toString() {
        return String.format("Client: ID = %d | Name = '%s' | Email = '%s' | Subscribed = %b",
                id, name, email, isSubscribed);
    }


}
