package xyz.guqing.creek.security.handler;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import xyz.guqing.creek.model.support.ResultEntity;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Slf4j
public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse, AuthenticationException e)
        throws IOException {
        log.error(e.getMessage(), e);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 登陆失败
        httpServletResponse.getWriter()
            .write(JSON.toJSONString(ResultEntity.authorizedFailed("认证失败")));
    }
}
