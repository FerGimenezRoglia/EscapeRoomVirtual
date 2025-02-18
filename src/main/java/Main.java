import config.AppInitializer;
import controllers.InitializationController;
import controllers.ManagementController;
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

        // Crear e iniciar el menú con ambos controladores
        MenuManagement menu = new MenuManagement(initializationController, managementController);
        menu.showMenu();

        // Cerrar la conexión a la base de datos
        initializer.close();
    }
}
