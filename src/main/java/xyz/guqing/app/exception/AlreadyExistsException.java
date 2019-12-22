package xyz.guqing.app.exception;

/**
 * 数据已经存在异常
 *
 * @author guqing
 * @date 2019-12-22 14:49
 */
public class AlreadyExistsException extends BadRequestException {

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
