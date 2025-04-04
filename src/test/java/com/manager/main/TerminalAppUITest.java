package com.manager.main;

import javafx.scene.text.TextFlow;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import javafx.stage.Stage;
import javafx.scene.control.TextField;

import static org.junit.jupiter.api.Assertions.*;

public class TerminalAppUITest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        new MainApplication().start(stage);
    }

    @Test
    void testCommandInputFieldExists() {
        TextField input = lookup("#inputField").query();
        assertNotNull(input);
    }

    @Test
    void testCommandExecutionOutputAppears() {
        TextField input = lookup("#inputField").query();
        clickOn(input).write("echo Test");

        clickOn("Run Command");

        TextFlow output = lookup("#outputFlow").query();
        sleep(1000);

        String content = output.getChildren().stream()
                .map(n -> n.toString().toLowerCase())
                .reduce("", String::concat);

        assertTrue(content.contains("test"), "Output should contain 'Test'");
    }
}
