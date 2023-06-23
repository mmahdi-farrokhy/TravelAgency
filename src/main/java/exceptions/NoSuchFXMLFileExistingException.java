package exceptions;

public class NoSuchFXMLFileExistingException extends RuntimeException {
    public NoSuchFXMLFileExistingException(String message) {
        super(message);
    }
}
