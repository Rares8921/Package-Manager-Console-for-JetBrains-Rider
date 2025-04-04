package com.manager.main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShellDetectorTest {

    @Test
    void testShellBuilder_WindowsOrUnix() {
        String os = System.getProperty("os.name").toLowerCase();
        ProcessBuilder pb = new MainController().buildShellCommand("echo unit test nr 1");
        if (os.contains("win")) {
            assertEquals("cmd.exe", pb.command().getFirst());
        } else {
            assertTrue(pb.command().getFirst().contains("sh") || pb.command().getFirst().contains("bash"));
        }
    }
}
