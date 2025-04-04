package com.manager.main;

import javafx.stage.Stage;

public class AppContext {
    private static Stage primaryStage;

    private AppContext() {}

    public static void setPrimaryStage(Stage stage) {
        if (primaryStage == null) {
            primaryStage = stage;
        }
    }

    public static Stage getPrimaryStage() {
        if (primaryStage == null) {
            throw new IllegalStateException("Primary stage has not been set");
        }
        return primaryStage;
    }
}
