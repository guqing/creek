package xyz.guqing.creek.exception;

import org.springframework.http.HttpStatus;

/**
 * 未授权异常
 * @author guqing
 * @date 2020-09-02
 */
public class UnauthorizedException extends AbstractCreekException {
    private static final long serialVersionUID = 7685019181203066845L;

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
