package view;

import config.DatabaseConnection;
import exceptions.AppException;
import controllers.Operaciones;
import repositories.DecorationRepository;
import repositories.EscapeRoomRepository;
import repositories.HintRepository;
import repositories.RoomRepository;
import services.EscapeRoomService;

import java.sql.Connection;
import java.util.Scanner;

public class GestionMenu implements IMenuGestion {
    private final Operaciones operaciones;
    private final Scanner scanner;

    public GestionMenu(Operaciones operaciones) {
        this.operaciones = operaciones;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void mostrarMenu() {
        try {
            while (true) {
                mostrarMenuInicio();
                int opcion = obtenerOpcion();
                switch (opcion) {
                    case 1 -> inicializar();
                    case 2 -> mostrarMenuGestion();
                    case 3 -> {
                        System.out.println("Gracias por participar!");
                        return;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en el menú inicial: " + e.getMessage());
        }
    }

    private void mostrarMenuInicio() {
        System.out.println("\nBIENVENIDO");
        System.out.println();

        System.out.println("\n====== MENÚ PRINCIPAL=====");
        System.out.println("1. Inicializar");
        System.out.println("2. Gestionar");
        System.out.println("3. Salir");
        System.out.println();
        System.out.print("Elige una opción: ");
    }

    private void inicializar() {

        // *** Fer: ----> estoy probando el EscapeRoomService *** //
        Connection connection = DatabaseConnection.getInstance().getConnection();
        EscapeRoomRepository escapeRoomRepo = new EscapeRoomRepository(connection);
        RoomRepository roomRepo = new RoomRepository(connection);
        HintRepository hintRepo = new HintRepository(connection);
        DecorationRepository decorationRepo = new DecorationRepository(connection);

        EscapeRoomService escapeRoomService = new EscapeRoomService(escapeRoomRepo, roomRepo, hintRepo, decorationRepo, connection);
        escapeRoomService.initializeEscapeRoom();
        System.out.println("Inicialización completada con éxito.");

        //System.out.println("Inicializando elementos...");
        // Aquí puedes incluir lógica específica para crear salas, pistas y decoraciones
    }

    private void mostrarMenuGestion() {
        try {
            while (true) {
                System.out.println("\n===== MENÚ DE GESTIÓN =====");
                System.out.println("1. Salas");
                System.out.println("2. Pistas");
                System.out.println("3. Decoraciones");
                System.out.println("4. Inventario");
                System.out.println("5. Tickets");
                System.out.println("6. Ingresos");
                System.out.println("7. Notificaciones");
                System.out.println("8. Certificados");
                System.out.println("9. Volver");
                System.out.print("Elige una opción: ");

                int opcion = obtenerOpcion();
                switch (opcion) {
                    case 1 -> operaciones.gestionarSalas();
//                    case 2 -> operaciones.gestionarPistas();
//                    case 3 -> operaciones.gestionarDecoraciones();
//                    case 4 -> operaciones.gestionarInventario();
//                    case 5 -> operaciones.gestionarTickets();
//                    case 6 -> operaciones.gestionarIngresos();
//                    case 7 -> operaciones.gestionarNotificaciones();
//                    case 8 -> operaciones.gestionarCertificados();
                    case 9 -> {
                        System.out.println("Volviendo...");
                        return;
                    }
                    default -> System.out.println("Opción no válida. Inténtalo de nuevo.");
                }
            }
        } catch (AppException e) {
            System.err.println("Error en el menú de Gestión: " + e.getMessage());
        }
    }

    private int obtenerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            throw new AppException("Error: Ingresa un número válido.", e);
        }
    }
}
