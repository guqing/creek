package xyz.guqing.creek.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.Assert;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import xyz.guqing.creek.exception.AuthenticationException;
import xyz.guqing.creek.exception.BadRequestException;
import xyz.guqing.creek.exception.BindSocialAccountException;
import xyz.guqing.creek.exception.ForbiddenException;
import xyz.guqing.creek.model.support.ResultEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author guqing
 * @date 2020-8-19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultEntity<String> handleForbiddenException(ForbiddenException e) {
        log.error("无权访问,{}", e);
        return ResultEntity.accessDenied("没有权限访问该资源");
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResultEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error("无权访问,{}", e);
        return ResultEntity.accessDenied("操作被拒绝，无权访问该资源");
    }

    @ExceptionHandler(value = BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        log.error("登录失败,{}", e);
        return ResultEntity.authorizedFailed("用户名或密码错误");
    }

    @ExceptionHandler(value = HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
        log.error("不支持的媒体类型异常,{}", e);
        return ResultEntity.unSupportedMediaType();
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String message = "该方法不支持" + StringUtils.substringBetween(e.getMessage(), "'", "'") + "请求方法";
        log.error(message);
        return ResultEntity.accessDenied(message);
    }

    @ExceptionHandler(BindSocialAccountException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResultEntity<String> handleBindSocialAccountException(BindSocialAccountException e) {
        log.error("绑定社交帐号出错:{}", e);
        return ResultEntity.badArgument(e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<String> handleBadRequestException(BadRequestException e) {
        log.error("Captured an exception：{0}", e);
        return ResultEntity.badArgument(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResultEntity<String> handleAuthFailException(AuthenticationException e) {
        log.error("认证失败，错误信息：{0}", e);
        return ResultEntity.authorizedFailed("认证失败:" + e.getMessage());
    }

    /**
     * 当使用@Valid不带@RequestBody request参数时:
     * 对象验证失败，验证将引发BindException而不是MethodArgumentNotValidException
     * @param e 参数绑定异常
     * @return 返回参数校验失败的错误信息
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<Map<String, String>> validExceptionHandler(BindException e){
        // 将错误的参数的详细信息封装到统一的返回实体
        return validParam(e.getBindingResult());
    }

    /**
     * 使用@Valid并且带有@RequestBody request参数时
     * 参数教研失败将抛出MethodArgumentNotValidException异常，由此方法捕获处理
     * @param e 方法参数校验失败的异常
     * @return 返回校验失败错误信息
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultEntity<Map<String, String>> validExceptionHandler(MethodArgumentNotValidException e){
        return validParam(e.getBindingResult());
    }

    private ResultEntity<Map<String, String>> validParam(BindingResult bindResult) {
        Assert.notNull(bindResult, "参数校验对象BindingResult不能为空");

        List<FieldError> fieldErrors = bindResult.getFieldErrors();
        Map<String, String> map = new HashMap<>(16);
        fieldErrors.forEach(fieldError -> {
            map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        log.error("Parameter verification failed：{}", bindResult.getAllErrors());
        return ResultEntity.badArgumentValue(map);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultEntity<String> handleException(Exception e) {
        log.error("系统内部异常，{}",e);
        return ResultEntity.serverError(e.getMessage());
    }
}
