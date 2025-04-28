package exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("Utente non trovato.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
