import config.AppInitializer;
import view.MenuManagement;

public class Main {
    public static void main(String[] args) {
        // Inicializar la aplicación
        AppInitializer initializer = new AppInitializer();

        // Ejecutar el esquema SQL para asegurar que la base de datos esté lista
        initializer.runScheme();

        // Crear e iniciar el menú
        MenuManagement menu = new MenuManagement(initializer.getOperaciones());
        menu.showMenu();

        // Cerrar la conexión a la base de datos
        initializer.close();

    }
}