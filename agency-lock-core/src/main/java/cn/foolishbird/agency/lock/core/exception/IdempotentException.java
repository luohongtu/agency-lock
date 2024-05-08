package cn.foolishbird.agency.lock.core.exception;

/**
 * Idempotent anomaly
 *
 * @author foolishbird
 */
public class IdempotentException extends RuntimeException {

    public IdempotentException() {
        super();
    }

    public IdempotentException(String message) {
        super(message);
    }

    public IdempotentException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdempotentException(Throwable cause) {
        super(cause);
    }

}
