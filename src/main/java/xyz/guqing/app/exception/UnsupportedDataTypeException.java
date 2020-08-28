package xyz.guqing.app.exception;

/**
 * @author guqing
 * @date 2020-04-15 11:17
 */
public class UnsupportedDataTypeException extends BadRequestException {
    private static final long serialVersionUID = 7932950060225121991L;

    public UnsupportedDataTypeException(String message) {
        super(message);
    }

    public UnsupportedDataTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
