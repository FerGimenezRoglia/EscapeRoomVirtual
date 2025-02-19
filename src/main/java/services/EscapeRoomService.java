package services;

import config.DatabaseConnection;
import models.EscapeRoom;
import models.Room;
import models.Hint;
import models.Decoration;
import repositories.EscapeRoomRepository;
import repositories.RoomRepository;
import repositories.HintRepository;
import repositories.DecorationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

public class EscapeRoomService {
    private static final Logger logger = LogManager.getLogger(EscapeRoomService.class);
    private final EscapeRoomRepository escapeRoomRepository;
    private final RoomRepository roomRepository;
    private final HintRepository hintRepository;
    private final DecorationRepository decorationRepository;
    private final Connection connection;

    //No crea repositorios internamente, sino que los recibe como parámetros en el constructor.
    public EscapeRoomService(EscapeRoomRepository escapeRoomRepository, RoomRepository roomRepository, HintRepository hintRepository, DecorationRepository decorationRepository, Connection connection) {
        this.escapeRoomRepository = escapeRoomRepository;
        this.roomRepository = roomRepository;
        this.hintRepository = hintRepository;
        this.decorationRepository = decorationRepository;
        this.connection = connection;
    }

    public void initializeEscapeRoom() {
        DatabaseConnection.getInstance().ejecutarSchema();
        resetDatabase();
        createDefaultEscapeRoom();
    }

    private void resetDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM hint");
            stmt.execute("ALTER TABLE hint AUTO_INCREMENT = 1");

            stmt.execute("DELETE FROM decoration");
            stmt.execute("ALTER TABLE decoration AUTO_INCREMENT = 1");

            stmt.execute("DELETE FROM room");
            stmt.execute("ALTER TABLE room AUTO_INCREMENT = 1");

            stmt.execute("DELETE FROM escape_room");
            stmt.execute("ALTER TABLE escape_room AUTO_INCREMENT = 1");

            logger.info("Base de datos limpia y reseteada.");
        } catch (Exception e) {
            logger.error("Error al resetear la base de datos: {}", e.getMessage(), e);
        }
    }

    private void createDefaultEscapeRoom() {
        List<EscapeRoom> existingEscapeRooms = escapeRoomRepository.getAll();

        existingEscapeRooms.forEach(escapeRoom -> logger.info("Ya existe un Escape Room en la base de datos. No se necesita inicialización."));

        if (existingEscapeRooms.isEmpty()) {
            EscapeRoom escapeRoom = new EscapeRoom("Escape Room Virtual");
            escapeRoomRepository.add(escapeRoom);

            Room room = new Room(escapeRoom.getId(), "El Misterio de Pepe", Room.DifficultyLevel.MEDIUM, 50.0);
            roomRepository.add(room);

            Hint hint = new Hint(room.getId(), "Busca la llave en el Cielo", 10.0);
            hintRepository.add(hint);

            Decoration decoration = new Decoration(room.getId(), "Cuadro Nublado", "Madera", 20.0);
            decorationRepository.add(decoration);

            logger.info("Inicializando...\n" +
                            "┌───────────────────────────────────────────────┐\n" +
                            "│  * {}: Por defecto           │\n" +
                            "│  └── + Sala: {}              │\n" +
                            "│      ├── + Pista: {}  │\n" +
                            "│      └── + Decoración: {}         │\n" +
                            "└───────────────────────────────────────────────┘",
                    escapeRoom.getName(),
                    room.getName(),
                    hint.getDescription(),
                    decoration.getName());
        }
    }
}