package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class GameDoesNotExistsException extends Exception {
    public GameDoesNotExistsException(String message) {
        super(message);
    }

    public GameDoesNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
