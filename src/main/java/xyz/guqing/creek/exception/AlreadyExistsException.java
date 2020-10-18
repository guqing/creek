package xyz.guqing.creek.exception;

/**
 * Exception caused by model existence already.
 *
 * @author guqing
 * @date 2020-4-4 16:29
 */
public class AlreadyExistsException extends BadRequestException {

    private static final long serialVersionUID = -1379001199432321678L;

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
