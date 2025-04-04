package com.manager.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Objects;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AppContext.setPrimaryStage(stage);
        Parent parent = FXMLLoader.load((Objects.requireNonNull(MainApplication.class.getResource("view.fxml"))));
        Scene scene = new Scene(parent);
        scene.setFill(Color.web("#1e1e1e"));

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("appicon.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Terminal");
        // This style should be placed only in production!!
        stage.initStyle(StageStyle.UNDECORATED);


        // Terminate the process
        stage.setOnCloseRequest(e -> System.exit(0));

        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}