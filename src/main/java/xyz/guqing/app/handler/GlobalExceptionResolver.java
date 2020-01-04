package xyz.guqing.app.handler;

import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.exception.AuthException;
import org.springframework.web.bind.annotation.*;
import xyz.guqing.app.exception.AuthFailException;
import xyz.guqing.app.exception.ServiceException;
import xyz.guqing.app.exception.UnsupportedOauthTypeException;
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

    @ExceptionHandler(UnsupportedOauthTypeException.class)
    public Result handleOauthException(UnsupportedOauthTypeException e) {
        log.debug("第三方登录异常，错误信息：{}", e.getMessage());
        return Result.loginFail("第三方登录出错:" + e.getMessage());
    }

    @ExceptionHandler(AuthFailException.class)
    public Result handleAuthFailException(AuthFailException e) {
        log.debug("登录失败，错误信息：{}", e.getMessage());
        return Result.loginFail("登录失败:" + e.getMessage());
    }

    @ExceptionHandler(ServiceException.class)
    public Result handleOpdRuntimeException(ServiceException e) {
        log.debug("业务层异常，错误信息：{}", e.getMessage());
        return Result.businessError(e.getMessage());
    }
}
