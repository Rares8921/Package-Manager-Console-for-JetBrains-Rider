package com.manager.main;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;

public class MainController  implements Initializable {

    @FXML private AnchorPane parent;
    @FXML private TextField inputField;
    @FXML private TextFlow outputFlow;

    private double xOffSet;
    private double yOffSet;

    @FXML
    public void onRunCommand() {
        String command = inputField.getText();
        outputFlow.getChildren().clear();
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                ProcessBuilder builder = buildShellCommand(command);
                Process process = builder.start();

                BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
                BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));

                String line;
                while ((line = stdout.readLine()) != null) {
                    appendText(line + "\n", Color.LIGHTGREEN);
                }

                while ((line = stderr.readLine()) != null) {
                    appendText(line + "\n", Color.SALMON);
                }

                int exitCode = process.waitFor();
                appendText("Exit code: " + exitCode + "\n", exitCode == 0 ? Color.DARKGRAY : Color.RED);

            } catch (UnsupportedOperationException ex) {
                showErrorAndExit("Unsupported OS or shell:\n" + ex.getMessage());
            } catch (Exception ex) {
                appendText("Error: " + ex.getMessage() + "\n", Color.RED);
            }
        });
    }

    private void appendText(String text, Color color) {
        Text t = new Text(text);
        t.setFill(color);
        Platform.runLater(() -> outputFlow.getChildren().add(t));
    }

    private void showErrorAndExit(String message) {
        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Fatal Error");
            alert.setHeaderText("Incompatible OS or Shell");
            alert.setContentText(message);
            alert.setOnHidden(e -> Platform.exit());
            alert.show();
        });
    }

    ProcessBuilder buildShellCommand(String command) throws UnsupportedOperationException {
        String os = System.getProperty("os.name").toLowerCase();


        if (os.contains("win")) {
            return new ProcessBuilder("cmd.exe", "/c", command);
        }

        if (isExecutable("/bin/sh")) {
            return new ProcessBuilder("/bin/sh", "-c", command);
        }

        if (isExecutable("/bin/bash")) {
            return new ProcessBuilder("/bin/bash", "-c", command);
        }

        throw new UnsupportedOperationException("No compatible shell found for OS: " + os);
    }

    private boolean isExecutable(String path) {
        return new java.io.File(path).canExecute();
    }

    public void close() {
        AppContext.getPrimaryStage().close();
    }

    public void minimize() {
        AppContext.getPrimaryStage().setIconified(true);
    }

    public void setDraggable() {
        parent.setOnMousePressed((event) -> { // getting the position
            xOffSet = event.getSceneX();
            yOffSet = event.getSceneY();
        });
        parent.setOnMouseDragged((event) -> { // reposition
            AppContext.getPrimaryStage().setX(event.getScreenX() - xOffSet);
            AppContext.getPrimaryStage().setY(event.getScreenY() - yOffSet);
        });
    }

    private void addListeners() {
        inputField.setOnKeyPressed(event -> {
            if(event.getCode() == KeyCode.ENTER) {
                onRunCommand();
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setDraggable();
        addListeners();
    }
}