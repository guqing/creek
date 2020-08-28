package xyz.guqing.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * 基类异常
 *
 * @author guqing
 * @date 2020-04-04 16:03
 */
public abstract class AbstractCreekException extends RuntimeException {
    /**
     * version id
     */
    private static final long serialVersionUID = -8889564685896439438L;

    /**
     * Error errorData.
     */
    private Object errorData;

    public AbstractCreekException(String message) {
        super(message);
    }

    public AbstractCreekException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public AbstractCreekException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }

}
