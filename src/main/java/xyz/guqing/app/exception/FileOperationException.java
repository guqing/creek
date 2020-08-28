package xyz.guqing.app.exception;

/**
 * File operation exception.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class FileOperationException extends ServiceException {
    private static final long serialVersionUID = 3171412351360260518L;

    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
