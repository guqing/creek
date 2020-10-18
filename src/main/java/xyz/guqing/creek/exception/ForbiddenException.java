package xyz.guqing.creek.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by accessing forbidden resources.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class ForbiddenException extends AbstractCreekException {
    private static final long serialVersionUID = 7685019181203066845L;

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
