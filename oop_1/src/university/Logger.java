package src.university;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class Logger {

    private static final String LOG_FILE = "system.log";

    public static void log(String action) {
    	try (PrintWriter out =new PrintWriter(
                             new FileWriter(LOG_FILE, true))) {

            out.println(
                    new Date()
                            + " | INFO: "
                            + action
            );

        } catch (IOException e) {

            System.err.println(
                    "Error: "
                            + e.getMessage()
            );
        }
    }

    public static List<String> readLogs() {

        List<String> logs =
                new ArrayList<>();

        File file =
                new File(LOG_FILE);

        if (!file.exists()) {

            return logs;
        }

        try (BufferedReader br =
                     new BufferedReader(
                             new FileReader(file))) {

            String line;

            while ((line = br.readLine()) != null) {

                logs.add(line);
            }

        } catch (IOException e) {

            System.err.println(
                    "Error reading logs: "
                            + e.getMessage()
            );
        }

        return logs;
    }
}