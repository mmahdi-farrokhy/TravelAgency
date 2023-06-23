package exceptions;

public class NoUserLoggedInException extends RuntimeException {
    public NoUserLoggedInException(String message) {
        super(message);
    }
}
