package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class CanNotPlayThisCardException extends Exception {
    public CanNotPlayThisCardException(String message) {
        super(message);
    }

    public CanNotPlayThisCardException(String message, Throwable cause) {
        super(message, cause);
    }
}
