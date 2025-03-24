package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class GameAlreadyFullException extends Exception {
    public GameAlreadyFullException(String message) {
        super(message);
    }

    public GameAlreadyFullException(String message, Throwable cause) {
        super(message, cause);
    }
}
