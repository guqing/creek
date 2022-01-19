package xyz.guqing.creek.utils;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 用户信息工具类
 *
 * @author guqin
 * @date 2019-08-09 16:35
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUserHelper {

    /**
     * 获取当前用户
     *
     * @return 返回认证对象
     */
    public static Authentication getCurrentUserAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static boolean isCurrentUser(String username) {
        return Objects.equals(getCurrentUsername(), username);
    }

    /**
     * 获取当前登录用户名
     *
     * @return 返回登录用户名
     */
    public static String getCurrentUsername() {
        Authentication authentication = getCurrentUserAuthentication();
        return authentication.getName();
    }
}
