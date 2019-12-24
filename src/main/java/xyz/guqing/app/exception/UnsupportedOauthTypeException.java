package xyz.guqing.app.exception;

/**
 * 不支持的社交登录类型异常
 * @author guqing
 * @date 2019-12-24 16:31
 */
public class UnsupportedOauthTypeException extends BadRequestException {
    public UnsupportedOauthTypeException(String message) {
        super(message);
    }

    public UnsupportedOauthTypeException(String message, Throwable cause) {
        super(message, cause);
    }
}
