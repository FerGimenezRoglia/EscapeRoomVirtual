import models.*;
import repositories.*;
import config.DatabaseConnection;
import java.sql.Connection;
import java.util.List;

public class TestSuite {
    private final Connection connection;
    private final EscapeRoomRepository escapeRoomRepo;
    private final RoomRepository roomRepo;
    private final HintRepository hintRepo;
    private final DecorationRepository decorationRepo;
    private final ClientRepository clientRepo;
    private final TicketRepository ticketRepo;
    private final RoomClientRepository roomClientRepo;

    public TestSuite(Connection connection) {
        this.connection = connection;
        this.escapeRoomRepo = new EscapeRoomRepository(connection);
        this.roomRepo = new RoomRepository(connection);
        this.hintRepo = new HintRepository(connection);
        this.decorationRepo = new DecorationRepository(connection);
        this.clientRepo = new ClientRepository(connection);
        this.ticketRepo = new TicketRepository(connection);
        this.roomClientRepo = new RoomClientRepository(connection);
    }

    public void runTests() {
        DatabaseConnection.getInstance().ejecutarSchema();
        resetDatabase();

        testEscapeRoom();
        testRoom();
        testHint();
        testDecoration();
        testClient();
        testTicket();
        testRoomClient();
        testDeletes();
    }

    private void resetDatabase() {
        try (var stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM ticket");
            stmt.execute("DELETE FROM room_client");
            stmt.execute("DELETE FROM hint");
            stmt.execute("DELETE FROM decoration");
            stmt.execute("DELETE FROM room");
            stmt.execute("DELETE FROM escape_room");
            stmt.execute("DELETE FROM client");
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
    }

    private void testRoom() {
        System.out.println("\n▶️ PRUEBA: Room");
        EscapeRoom escapeRoom = escapeRoomRepo.getAll().get(0);
        Room room = new Room(escapeRoom.getId(), "La Bóveda Secreta", Room.DifficultyLevel.MEDIUM, 30.0);
        roomRepo.add(room);
        System.out.println("✅ Sala agregada: " + room);
    }

    private void testHint() {
        System.out.println("\n▶️ PRUEBA: Hint");
        Room room = roomRepo.getAll().get(0);
        Hint hint = new Hint(room.getId(), "Mira debajo del cuadro", 5.0);
        hintRepo.add(hint);
        System.out.println("✅ Pista agregada: " + hint);
    }

    private void testDecoration() {
        System.out.println("\n▶️ PRUEBA: Decoration");
        Room room = roomRepo.getAll().get(0);
        Decoration decoration = new Decoration(room.getId(), "Cuadro Gótico", "Madera", 15.0);
        decorationRepo.add(decoration);
        System.out.println("✅ Decoración agregada: " + decoration);
    }

    private void testClient() {
        System.out.println("\n▶️ PRUEBA: Client");
        Client client = new Client("Juan Pérez", "juan@example.com", true);
        clientRepo.add(client);
        System.out.println("✅ Cliente agregado: " + client);
    }

    private void testTicket() {
        System.out.println("\n▶️ PRUEBA: Ticket");
        Client client = clientRepo.getAll().get(0);
        Room room = roomRepo.getAll().get(0);
        Ticket ticket = new Ticket(client.getId(), room.getId(), 50.0);
        ticketRepo.add(ticket);
        System.out.println("✅ Ticket agregado: " + ticket);
    }

    private void testRoomClient() {
        System.out.println("\n▶️ PRUEBA: Room Client");
        Client client = clientRepo.getAll().get(0);
        Room room = roomRepo.getAll().get(0);
        RoomClient roomClient = new RoomClient(client.getId(), room.getId(), new java.sql.Timestamp(System.currentTimeMillis()), null, false);
        roomClientRepo.add(roomClient);
        System.out.println("✅ Cliente asignado a Sala: " + roomClient);
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

        List<Decoration> decorations = decorationRepo.getAll();
        if (!decorations.isEmpty()) {
            decorationRepo.delete(decorations.get(0).getId());
            System.out.println("✅ Decoración eliminada.");
        } else {
            System.out.println("⚠️ No hay decoraciones para eliminar.");
        }

        List<Room> rooms = roomRepo.getAll();
        if (!rooms.isEmpty()) {
            roomRepo.delete(rooms.get(0).getId());
            System.out.println("✅ Sala eliminada.");
        } else {
            System.out.println("⚠️ No hay salas para eliminar.");
        }

        List<EscapeRoom> escapeRooms = escapeRoomRepo.getAll();
        if (!escapeRooms.isEmpty()) {  // ✅ Agregar esta verificación
            escapeRoomRepo.delete(escapeRooms.get(0).getId());
            System.out.println("✅ Escape Room eliminada.");
        } else {
            System.out.println("⚠️ No hay Escape Rooms para eliminar.");
        }
    }
}
