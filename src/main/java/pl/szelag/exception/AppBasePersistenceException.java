package pl.szelag.exception;

public class AppBasePersistenceException extends AppBaseException {

    protected AppBasePersistenceException() {
        super("persistence");
    }

    protected AppBasePersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public static AppBasePersistenceException createPersistenceException() {
        return new AppBasePersistenceException();
    }
}
