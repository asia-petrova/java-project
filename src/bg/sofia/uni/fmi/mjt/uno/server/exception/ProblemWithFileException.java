package bg.sofia.uni.fmi.mjt.uno.server.exception;

public class ProblemWithFileException extends Exception {
    public ProblemWithFileException(String message) {
        super(message);
    }

    public ProblemWithFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
