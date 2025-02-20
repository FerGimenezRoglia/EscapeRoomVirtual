package models;

import java.sql.Timestamp;

public class EscapeRoom {

    private int id;
    private String name;
    private Timestamp createdAt; // Timestamp mejor opción en Java para manejar fechas y horas con precisión
    private Timestamp updatedAt; // Timestamp mejor opción en Java para manejar fechas y horas con precisión

    // Constructor vacío
    public EscapeRoom() {}

    // Constructor con todos los atributos (para recuperar datos de la BD)
    public EscapeRoom(int id, String name, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Constructor para nuevos registros (sin ID ni timestamps)
    public EscapeRoom(String name) {
        this.name = name;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public Timestamp getCreatedAt() { return createdAt; }
    public Timestamp getUpdatedAt() { return updatedAt; }

    // Setters
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
