package xyz.guqing.creek.security.handler;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import xyz.guqing.creek.model.support.ResultEntity;

import java.io.IOException;

/**
 * @author guqing
 * @date 2019-12-22 24:53
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException,
        ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        // 访问被拒绝
        httpServletResponse.getWriter().write(JSON.toJSONString(ResultEntity.unauthorized()));
    }
}
