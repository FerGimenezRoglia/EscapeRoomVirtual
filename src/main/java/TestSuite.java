import models.EscapeRoom;
import models.Room;
import models.Hint;
import repositories.EscapeRoomRepository;
import repositories.RoomRepository;
import repositories.HintRepository;
import config.DatabaseConnection;

import java.sql.Connection;
import java.util.List;

public class TestSuite {
    private final EscapeRoomRepository escapeRoomRepo;
    private final RoomRepository roomRepo;
    private final HintRepository hintRepo;
    private final Connection connection;

    public TestSuite(Connection connection) {
        this.connection = connection;
        this.escapeRoomRepo = new EscapeRoomRepository(connection);
        this.roomRepo = new RoomRepository(connection);
        this.hintRepo = new HintRepository(connection);
    }

    public void runTests() {

        DatabaseConnection.getInstance().ejecutarSchema();

        resetDatabase();

        testEscapeRoom();
        testRoom();
        testHint();
        testDeletes();
    }

    private void resetDatabase() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM hint");
            stmt.execute("ALTER TABLE hint AUTO_INCREMENT = 1");

            stmt.execute("DELETE FROM room");
            stmt.execute("ALTER TABLE room AUTO_INCREMENT = 1");

            stmt.execute("DELETE FROM escape_room");
            stmt.execute("ALTER TABLE escape_room AUTO_INCREMENT = 1");

            System.out.println("✅ Base de datos limpia y reseteada.");
        } catch (Exception e) {
            System.err.println("❌ Error al resetear la base de datos: " + e.getMessage());
        }
    }

    private void testEscapeRoom() {
        System.out.println("\n▶️ PRUEBA: Escape Room");

        EscapeRoom escapeRoom = new EscapeRoom("Misterio Submarino");
        escapeRoomRepo.add(escapeRoom);
        System.out.println("✅ Escape Room agregada: " + escapeRoom);

        System.out.println("▶️ Obtener Escape Room por ID...");
        System.out.println("✅ " + escapeRoomRepo.getById(escapeRoom.getId()));

        System.out.println("▶️ Listar todas las Escape Rooms:");
        escapeRoomRepo.getAll().forEach(System.out::println);
    }

    private void testRoom() {
        System.out.println("\n▶️ PRUEBA: Room");

        List<EscapeRoom> escapeRooms = escapeRoomRepo.getAll();
        if (escapeRooms.isEmpty()) {
            System.out.println("⚠️ No hay Escape Rooms disponibles. No se puede probar Room.");
        }

        Room room = new Room(escapeRooms.get(0).getId(), "La Bóveda Secreta", Room.DifficultyLevel.MEDIUM, 30.0);
        roomRepo.add(room);
        System.out.println("✅ Sala agregada: " + room);

        System.out.println("▶️ Obtener Sala por ID...");
        System.out.println("✅ " + roomRepo.getById(room.getId()));

        System.out.println("▶️ Listar todas las Salas:");
        roomRepo.getAll().forEach(System.out::println);
    }

    private void testHint() {
        System.out.println("\n▶️ PRUEBA: Hint");

        List<Room> rooms = roomRepo.getAll();
        if (rooms.isEmpty()) {
            System.out.println("⚠️ No hay Salas disponibles. No se puede probar Hint.");
        }

        Hint hint = new Hint(rooms.get(0).getId(), "Mira debajo del cuadro", 5.0);
        hintRepo.add(hint);
        System.out.println("✅ Pista agregada: " + hint);

        System.out.println("▶️ Obtener Pista por ID...");
        System.out.println("✅ " + hintRepo.getById(hint.getId()));

        System.out.println("▶️ Listar todas las Pistas:");
        hintRepo.getAll().forEach(System.out::println);
    }

    private void testDeletes() {
        System.out.println("\n▶️ PRUEBA: Eliminaciones");

        List<Hint> hints = hintRepo.getAll();
        if (!hints.isEmpty()) {
            hintRepo.delete(hints.get(0).getId());
            System.out.println("✅ Pista eliminada.");
        } else {
            System.out.println("⚠️ No hay pistas para eliminar.");
        }

        List<Room> rooms = roomRepo.getAll();
        if (!rooms.isEmpty()) {
            roomRepo.delete(rooms.get(0).getId());
            System.out.println("✅ Sala eliminada.");
        } else {
            System.out.println("⚠️ No hay salas para eliminar.");
        }

        List<EscapeRoom> escapeRooms = escapeRoomRepo.getAll();
        if (!escapeRooms.isEmpty()) {
            escapeRoomRepo.delete(escapeRooms.get(0).getId());
            System.out.println("✅ Escape Room eliminada.");
        } else {
            System.out.println("⚠️ No hay Escape Rooms para eliminar.");
        }
    }
}