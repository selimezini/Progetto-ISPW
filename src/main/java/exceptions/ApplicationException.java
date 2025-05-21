package exceptions;



/** Eccezione da usare nel livello applicativo per comunicare errori alla GUI/CLI */
public class ApplicationException extends RuntimeException {
    public ApplicationException(String message) {
        super(message);
    }
}

