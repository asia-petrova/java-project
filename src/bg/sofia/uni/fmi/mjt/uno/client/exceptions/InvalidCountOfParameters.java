package bg.sofia.uni.fmi.mjt.uno.client.exceptions;

public class InvalidCountOfParameters extends Exception {
    public InvalidCountOfParameters(String message) {
        super(message);
    }

    public InvalidCountOfParameters(String message, Throwable cause) {
        super(message, cause);
    }
}
