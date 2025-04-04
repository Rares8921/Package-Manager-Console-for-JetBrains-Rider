package com.manager.main;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ShellDetectorTest {

    @Test
    void testShellBuilder_WindowsOrUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        MainController controller = new MainController();
        ProcessBuilder pb = controller.buildShellCommand("echo unit test nr 1");

        List<String> cmd = pb.command();

        if (os.contains("win")) {
            assertEquals(List.of("cmd.exe", "/c", "echo unit test nr 1"), cmd);
        } else if (new java.io.File("/bin/sh").canExecute()) {
            assertEquals(List.of("/bin/sh", "-c", "echo unit test nr 1"), cmd);
        } else if (new java.io.File("/bin/bash").canExecute()) {
            assertEquals(List.of("/bin/bash", "-c", "echo unit test nr 1"), cmd);
        } else {
            fail("No valid shell or OS found.");
        }
    }
}
