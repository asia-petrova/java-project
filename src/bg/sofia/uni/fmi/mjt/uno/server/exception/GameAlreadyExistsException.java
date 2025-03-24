package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class GameAlreadyExistsException extends Exception {
    public GameAlreadyExistsException(String message) {
        super(message);
    }

    public GameAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}
