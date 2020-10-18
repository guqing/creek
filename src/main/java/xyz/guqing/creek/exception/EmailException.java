package xyz.guqing.creek.exception;

/**
 * Email exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class EmailException extends ServiceException {
    private static final long serialVersionUID = 8054053086207304307L;

    public EmailException(String message) {
        super(message);
    }

    public EmailException(String message, Throwable cause) {
        super(message, cause);
    }
}
