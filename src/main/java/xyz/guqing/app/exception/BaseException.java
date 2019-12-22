package xyz.guqing.app.exception;

/**
 * 自定义异常的基类，作为自定义异常的根，往下派生
 * @author guqing
 * @date 2019-12-22 14:37
 */
public class BaseException extends RuntimeException {
    public BaseException() {
        super();
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause) {
        super(cause);
    }
}
