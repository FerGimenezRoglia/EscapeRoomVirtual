import config.AppInitializer;
import view.GestionMenu;

public class Main {
    public static void main(String[] args) {
        // Inicializar la aplicación
        AppInitializer initializer = new AppInitializer();

        // Ejecutar el esquema SQL para asegurar que la base de datos esté lista
        initializer.ejecutarSchema();

        // Crear e iniciar el menú
        GestionMenu menu = new GestionMenu(initializer.getOperaciones());
        menu.mostrarMenu();

        // Cerrar la conexión a la base de datos
        initializer.close();

    }
}