package bg.sofia.uni.fmi.mjt.uno.client.exceptions;

public class NoSuchCommand extends Exception {
    public NoSuchCommand(String message) {
        super(message);
    }

    public NoSuchCommand(String message, Throwable cause) {
        super(message, cause);
    }
}
