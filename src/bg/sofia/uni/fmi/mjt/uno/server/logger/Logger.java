package bg.sofia.uni.fmi.mjt.uno.server.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Logger {
    private static final String LOG_FILE = "logs.txt";

    public void logProblem(String message) {
        log(message, LOG_FILE);
    }

    private void log(String message, String file) {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file, true))) {
            printWriter.write("\n>" + message);
            printWriter.flush();
        } catch (IOException e) {
            System.out.println("Unable to perform log!");
        }
    }
}
