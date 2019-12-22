package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;

/**
 * BeanUtils exception.
 *
 * @author johnniang
 */
public class BeanUtilsException extends BaseException {

    public BeanUtilsException(String message) {
        super(message);
    }

    public BeanUtilsException(String message, Throwable cause) {
        super(message, cause);
    }
}
