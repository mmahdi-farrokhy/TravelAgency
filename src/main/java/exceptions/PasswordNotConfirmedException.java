package exceptions;

public class PasswordNotConfirmedException extends RuntimeException {
    public PasswordNotConfirmedException(String message) {
        super(message);
    }
}
