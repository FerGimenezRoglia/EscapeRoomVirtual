package config;

import view.MenuManagement;

public class AppRunner {
    private final AppInitializer initializer;
    private final MenuManagement menu;

    public AppRunner() {
        // Inicializar configuración y controladores
        this.initializer = new AppInitializer();
        this.menu = new MenuManagement(
                initializer.getInitializationController(),
                initializer.getManagementController(),
                initializer.getTransactionController(),
                initializer,
                initializer.getUserController()
        );
    }

    public void start() {
        initializer.runScheme();
        menu.showMenu();
        initializer.close();
    }
}
