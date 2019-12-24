package xyz.guqing.app.exception;

/**
 * 认证失败异常
 * @author guqing
 * @date 2019-12-25 00:00
 */
public class AuthFailException extends BadRequestException {
    public AuthFailException(String message) {
        super(message);
    }

    public AuthFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
