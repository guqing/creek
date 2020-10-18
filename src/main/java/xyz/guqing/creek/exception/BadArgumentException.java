package xyz.guqing.creek.exception;

/**
 * Exception caused by bad argument.
 *
 * @author guqing
 * @date 2020-07-15
 */
public class BadArgumentException extends BadRequestException {
    public BadArgumentException(String message) {
        super(message);
    }

    public BadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }
}
