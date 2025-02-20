import config.AppInitializer;
import controllers.InitializationController;
import controllers.ManagementController;
import controllers.TransactionController;
import view.MenuManagement;

public class Main {
    public static void main(String[] args) {
        // Inicializar la aplicación
        AppInitializer initializer = new AppInitializer();

        // Ejecutar el esquema SQL para asegurar que la base de datos esté lista
        initializer.runScheme();

        // Obtener los controladores desde AppInitializer
        InitializationController initializationController = initializer.getInitializationController();
        ManagementController managementController = initializer.getManagementController();
        TransactionController transactionController = initializer.getTransactionController();

        // Crear e iniciar el menú con los controladores
        MenuManagement menu = new MenuManagement(
                initializationController,
                managementController,
                transactionController,
                initializer
        );
        menu.showMenu();

        // Cerrar la conexión a la base de datos
        initializer.close();
    }
}
