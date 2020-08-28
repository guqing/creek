package xyz.guqing.app.exception;

/**
 * Frequent access exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class FrequentAccessException extends BadRequestException {
    private static final long serialVersionUID = 1947535783596017366L;

    public FrequentAccessException(String message) {
        super(message);
    }

    public FrequentAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
