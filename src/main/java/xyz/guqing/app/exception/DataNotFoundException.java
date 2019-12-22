package xyz.guqing.app.exception;

/**
 * 数据未找到异常
 * @author guqing
 * @date 2019-12-22 14:39
 */
public class DataNotFoundException extends BaseException {
    public DataNotFoundException() {
        super();
    }

    public DataNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataNotFoundException(String message) {
        super(message);
    }

    public DataNotFoundException(Throwable cause) {
        super(cause);
    }
}

