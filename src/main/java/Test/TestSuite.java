package Test;

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

            stmt.execute("ALTER TABLE ticket AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE room_client AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE hint AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE decoration AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE room AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE escape_room AUTO_INCREMENT = 1");
            stmt.execute("ALTER TABLE client AUTO_INCREMENT = 1");

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
        List<EscapeRoom> escapeRooms = escapeRoomRepo.getAll();
        if (!escapeRooms.isEmpty()) {
            Room room = new Room(escapeRooms.get(0).getId(), "La Bóveda Secreta", Room.DifficultyLevel.MEDIUM, 30.0);
            roomRepo.add(room);
            System.out.println("✅ Sala agregada: " + room);
        } else {
            System.out.println("⚠️ No hay Escape Rooms disponibles. No se puede probar Room.");
        }
    }

    private void testHint() {
        System.out.println("\n▶️ PRUEBA: Hint");
        List<Room> rooms = roomRepo.getAll();
        if (!rooms.isEmpty()) {
            Hint hint = new Hint(rooms.get(0).getId(), "Mira debajo del cuadro", 5.0);
            hintRepo.add(hint);
            System.out.println("✅ Pista agregada: " + hint);
        } else {
            System.out.println("⚠️ No hay Salas disponibles. No se puede probar Hint.");
        }
    }

    private void testDecoration() {
        System.out.println("\n▶️ PRUEBA: Decoration");
        List<Room> rooms = roomRepo.getAll();
        if (!rooms.isEmpty()) {
            Decoration decoration = new Decoration(rooms.get(0).getId(), "Cuadro Gótico", "Madera", 15.0);
            decorationRepo.add(decoration);
            System.out.println("✅ Decoración agregada: " + decoration);
        } else {
            System.out.println("⚠️ No hay Salas disponibles. No se puede probar Decoration.");
        }
    }

    private void testClient() {
        System.out.println("\n▶️ PRUEBA: Client");
        Client client = new Client("Juan Pérez", "juan@example.com", true);
        clientRepo.add(client);
        System.out.println("✅ Cliente agregado: " + client);
    }

    private void testTicket() {
        System.out.println("\n▶️ PRUEBA: Ticket");
        List<Client> clients = clientRepo.getAll();
        List<Room> rooms = roomRepo.getAll();
        if (!clients.isEmpty() && !rooms.isEmpty()) {
            Ticket ticket = new Ticket(clients.get(0).getId(), rooms.get(0).getId(), 50.0);
            ticketRepo.add(ticket);
            System.out.println("✅ Ticket agregado: " + ticket);
        } else {
            System.out.println("⚠️ No hay Clientes o Salas disponibles. No se puede probar Ticket.");
        }
    }

    private void testRoomClient() {
        System.out.println("\n▶️ PRUEBA: Room Client");
        List<Client> clients = clientRepo.getAll();
        List<Room> rooms = roomRepo.getAll();
        if (!clients.isEmpty() && !rooms.isEmpty()) {
            RoomClient roomClient = new RoomClient(clients.get(0).getId(), rooms.get(0).getId(), new java.sql.Timestamp(System.currentTimeMillis()), null, false);
            roomClientRepo.add(roomClient);
            System.out.println("✅ Cliente asignado a Sala: " + roomClient);
        } else {
            System.out.println("⚠️ No hay Clientes o Salas disponibles. No se puede probar Room Client.");
        }
    }

    private void testDeletes() {
        System.out.println("\n▶️ PRUEBA: Eliminaciones");

        List<Hint> hints = hintRepo.getAll();
        if (!hints.isEmpty()) hintRepo.delete(hints.get(0).getId());

        List<Decoration> decorations = decorationRepo.getAll();
        if (!decorations.isEmpty()) decorationRepo.delete(decorations.get(0).getId());

        List<Room> rooms = roomRepo.getAll();
        if (!rooms.isEmpty()) roomRepo.delete(rooms.get(0).getId());

        List<EscapeRoom> escapeRooms = escapeRoomRepo.getAll();
        if (!escapeRooms.isEmpty()) escapeRoomRepo.delete(escapeRooms.get(0).getId());

        System.out.println("✅ Eliminaciones completadas.");
    }
}
