package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by service.
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public class ServiceException extends AbstractCreekException {
    private static final long serialVersionUID = 4732689741650548040L;

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
