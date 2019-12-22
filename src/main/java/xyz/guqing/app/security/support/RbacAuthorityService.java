package xyz.guqing.app.security.support;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;

/**
 * @author guqing
 * @date 2019年10月19日 21:59
 */
@Component("rbacauthorityservice")
public class RbacAuthorityService {
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {

        Object principal = authentication.getPrincipal();
        boolean hasPermission = false;
        if (principal instanceof MyUserDetails) {
            MyUserDetails myUserDetails = (MyUserDetails)principal;

            //获取资源
            //这些 url 都是要登录后才能访问，且其他的 url 都不能访问
            Set<String> permissionUrls = myUserDetails.getPermissionUrl();

            hasPermission = matcherPrincipalUrls(request, permissionUrls);
        } else {
            hasPermission = matcherCommonUrls(request);
        }

        return hasPermission;
    }
    
    /**
     * 匹配url规则
     * @param request 请求对象
     * @param urls 允许访问的url地址
     */
    private boolean matcherPrincipalUrls(HttpServletRequest request, Set<String> urls) {
        if(matcherUrls(request, urls)) {
            return true;
        } else {
            return matcherCommonUrls(request);
        }
    }

    private boolean matcherCommonUrls(HttpServletRequest request) {
        Set<String> commonUrls = new HashSet<>();
        // 公共地址，不需要授权即可访问
        commonUrls.add("/user/activate");
        return matcherUrls(request, commonUrls);
    }

    private boolean matcherUrls(HttpServletRequest request, Set<String> urls) {
        boolean hasPermission = false;
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for(String url : urls) {
            if (antPathMatcher.match(url, request.getRequestURI())) {
                hasPermission = true;
                break;
            }
        }

        return hasPermission;
    }
}
