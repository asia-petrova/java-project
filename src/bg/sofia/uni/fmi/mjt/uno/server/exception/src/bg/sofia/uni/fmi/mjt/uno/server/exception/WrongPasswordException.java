package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class WrongPasswordException extends Exception {
    public WrongPasswordException(String message) {
        super(message);
    }
}
