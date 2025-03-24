package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class UserDoesNotExistException extends Exception {
    public UserDoesNotExistException(String message) {
        super(message);
    }

    public UserDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
