package view.management;

import controllers.UserController;
import java.util.Scanner;

public class UserManagement {
    private final UserController userController;
    private final Scanner scanner;

    public UserManagement(UserController userController) {
        this.userController = userController;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n===== GESTIÓN DE USUARIOS =====");
            System.out.println("1. Marcar sala como completada");
            System.out.println("2. Otorgar certificado");
            System.out.println("3. Entregar recompensa");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            String option = scanner.nextLine();

            switch (option) {
                case "1":
                    markRoomAsCompleted();
                    break;
                case "2":
                    giveCertificate();
                    break;
                case "3":
                    giveReward();
                    break;
                case "4":
                    continuar = false;
                    break; // Faltaba el break
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private void markRoomAsCompleted() {
        System.out.print("Ingrese el ID del cliente: ");
        int clientId = getIntInput();

        System.out.print("Ingrese el ID de la sala: ");
        int roomId = getIntInput();

        userController.markRoomAsCompleted(clientId, roomId);
    }

    private void giveCertificate() {
        System.out.print("Ingrese el ID del cliente: ");
        int clientId = getIntInput();

        System.out.print("Ingrese el ID de la sala: ");
        int roomId = getIntInput();

        userController.giveCertificate(clientId, roomId);
    }

    private void giveReward() {
        System.out.print("Ingrese el ID del cliente: ");
        int clientId = getIntInput();

        System.out.print("Ingrese el ID de la sala: ");
        int roomId = getIntInput();

        userController.giveReward(clientId, roomId);
    }

    // ✅ Método para limpiar buffer correctamente
    private int getIntInput() {
        while (true) {
            try {
                int number = Integer.parseInt(scanner.nextLine().trim());
                return number;
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.");
            }
        }
    }
}