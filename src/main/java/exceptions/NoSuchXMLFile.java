package exceptions;

public class NoSuchXMLFile extends RuntimeException {
    public NoSuchXMLFile(String message) {
        super(message);
    }
}
