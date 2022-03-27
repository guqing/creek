package xyz.guqing.creek.security.handler;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import xyz.guqing.creek.model.support.ResultEntity;

import java.io.IOException;


/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException,
        ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        //Need Authorities!
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultEntity.unauthorized()));
    }
}
