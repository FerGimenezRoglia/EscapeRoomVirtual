package services;

import repositories.RoomRepository;
import repositories.HintRepository;
import repositories.DecorationRepository;
import exceptions.DataAccessException;
import models.Room;
import models.Hint;
import models.Decoration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InventoryService {
    private final RoomRepository roomRepository;
    private final HintRepository hintRepository;
    private final DecorationRepository decorationRepository;

    public InventoryService(RoomRepository roomRepository, HintRepository hintRepository, DecorationRepository decorationRepository) {
        this.roomRepository = roomRepository;
        this.hintRepository = hintRepository;
        this.decorationRepository = decorationRepository;
    }

    public void showInventory() throws DataAccessException {
        // 📌 Obtener fecha actual
        String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());

        // 📌 Obtener todos los datos sin tocar repositorios
        List<Room> rooms = roomRepository.getAll();
        List<Hint> hints = hintRepository.getAll();
        List<Decoration> decorations = decorationRepository.getAll();

        // 📌 Calcular el precio total
        double totalRoomPrice = rooms.stream().mapToDouble(Room::getPrice).sum();
        double totalHintPrice = hints.stream().mapToDouble(Hint::getPrice).sum();
        double totalDecorationPrice = decorations.stream().mapToDouble(Decoration::getPrice).sum();
        double totalPrice = totalRoomPrice + totalHintPrice + totalDecorationPrice;

        // 📌 Imprimir inventario
        System.out.println("\n📦 INVENTARIO DEL ESCAPE ROOM 📦");
        System.out.println("📅 Fecha: " + currentDate);
        System.out.println("---------------------------------");
        System.out.println("🏠 Total de Salas: " + rooms.size());
        System.out.println("🧩 Total de Pistas: " + hints.size());
        System.out.println("🎭 Total de Decoraciones: " + decorations.size());
        System.out.println("💰 Valor Total del Inventario: " + totalPrice + "€");
    }

    public List<Room> getAllRooms() throws DataAccessException {
        return roomRepository.getAll();
    }

    public List<Hint> getAllHints() throws DataAccessException {
        return hintRepository.getAll();
    }

    public List<Decoration> getAllDecorations() throws DataAccessException {
        return decorationRepository.getAll();
    }

}