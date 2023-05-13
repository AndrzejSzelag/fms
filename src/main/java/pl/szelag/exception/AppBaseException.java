package pl.szelag.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public abstract class AppBaseException extends Exception {

    public static final String KEY_TX_RETRY_ROLLBACK = "error.tx.retry.rollback";

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AppBaseException(String message) {
        super(message);
    }
}
