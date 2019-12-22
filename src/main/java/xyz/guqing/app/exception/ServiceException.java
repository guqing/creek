package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;

/**
 * service层异常
 *
 * @author guqing
 * @date 2019-12-22 14:36
 */
public class ServiceException extends BaseException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
