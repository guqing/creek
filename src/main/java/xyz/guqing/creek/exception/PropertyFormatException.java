package xyz.guqing.creek.exception;

/**
 * Property format exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class PropertyFormatException extends BadRequestException {
    private static final long serialVersionUID = 5427803241943272018L;

    public PropertyFormatException(String message) {
        super(message);
    }

    public PropertyFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}
