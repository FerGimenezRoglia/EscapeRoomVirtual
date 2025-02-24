package config;

import view.MenuManagement;

public class AppRunner {
    private final AppInitializer initializer;
    private final MenuManagement menu;

    public AppRunner() {
        this.initializer = new AppInitializer();
        this.menu = new MenuManagement(
                initializer.getInitializationController(),
                initializer.getManagementController(),
                initializer.getTransactionController(),
                initializer,
                initializer.getUserController(),
                initializer.getInventoryService()
        );
    }

    public void start() {
        initializer.runScheme();
        menu.showMenu();
        initializer.close();
    }
}