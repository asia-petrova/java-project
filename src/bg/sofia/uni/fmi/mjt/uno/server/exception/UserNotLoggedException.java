package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class UserNotLoggedException extends Exception {
    public UserNotLoggedException(String message) {
        super(message);
    }

    public UserNotLoggedException(String message, Throwable cause) {
        super(message, cause);
    }
}
