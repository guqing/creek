package xyz.guqing.app.exception;

/**
 * Missing property value exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class MissingPropertyException extends BadRequestException {
    private static final long serialVersionUID = 1932212946514588758L;

    public MissingPropertyException(String message) {
        super(message);
    }

    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }
}
