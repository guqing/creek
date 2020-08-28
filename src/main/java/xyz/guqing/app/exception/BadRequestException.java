package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by bad request.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class BadRequestException extends AbstractCreekException {
    private static final long serialVersionUID = 2888541634229909695L;

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
