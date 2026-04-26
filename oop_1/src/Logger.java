package src;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class Logger {
    private static final String LOG_FILE = "system.log";

    public static void log(String action) {
        try (PrintWriter out = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            out.println(new Date() + " | INFO: " + action);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}