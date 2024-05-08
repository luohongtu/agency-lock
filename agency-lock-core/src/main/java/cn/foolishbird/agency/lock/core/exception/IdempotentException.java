package cn.foolishbird.agency.lock.core.exception;

/**
 * 幂等异常
 *
 * @author foolish bird
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
