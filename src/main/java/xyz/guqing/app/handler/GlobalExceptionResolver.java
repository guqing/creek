package xyz.guqing.app.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.exception.ServiceException;
import xyz.guqing.app.utils.Result;

/**
 * @author guqing
 * @date 2019-12-22 15:07
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionResolver {
    @ExceptionHandler(Exception.class)

    public Result handleException(Exception e) {
        log.debug("发生未知异常，错误信息：{}， 详细信息：{}", e.getMessage(), e);
        return Result.serious();
    }

    @ExceptionHandler(ServiceException.class)
    public Result handleOpdRuntimeException(ServiceException e) {
        log.debug("业务层异常，错误信息：{}", e.getMessage());
        return Result.businessError(e.getMessage());
    }
}
