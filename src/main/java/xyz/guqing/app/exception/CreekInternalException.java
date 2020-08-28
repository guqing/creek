package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;

/**
 * @author guqing
 * @date 2020-06-01
 */
public class CreekInternalException extends AbstractCreekException {
    public CreekInternalException(String message) {
        super(message);
    }

    public CreekInternalException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    @NonNull
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
