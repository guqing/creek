package xyz.guqing.app.exception;

/**
 * 不支持的媒体类型异常
 *
 * @author guqing
 * @date 2019-12-22 14:56
 */
public class UnsupportedMediaTypeException extends BadRequestException {

    public UnsupportedMediaTypeException(String message) {
        super(message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
