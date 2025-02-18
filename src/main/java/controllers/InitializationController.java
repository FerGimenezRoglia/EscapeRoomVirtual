package controllers;

import config.DatabaseConnection;
import repositories.DecorationRepository;
import repositories.EscapeRoomRepository;
import repositories.HintRepository;
import repositories.RoomRepository;
import services.EscapeRoomService;

import java.sql.Connection;

public class InitializationController {

    private final EscapeRoomService escapeRoomService;

    public InitializationController() {

        Connection connection = DatabaseConnection.getInstance().getConnection();

        EscapeRoomRepository escapeRoomRepo = new EscapeRoomRepository(connection);
        RoomRepository roomRepo = new RoomRepository(connection);
        HintRepository hintRepo = new HintRepository(connection);
        DecorationRepository decorationRepo = new DecorationRepository(connection);

        this.escapeRoomService = new EscapeRoomService(escapeRoomRepo, roomRepo, hintRepo, decorationRepo, connection);
    }

    public void startEscapeRoomSetup() {

        escapeRoomService.initializeEscapeRoom();
    }
}