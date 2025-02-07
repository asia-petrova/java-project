package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class UserAlreadyExistsException extends Exception {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
