package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;

/**
 * bad request异常
 *
 * @author guqing
 * @date 2019-12-22 14:50
 */
public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
