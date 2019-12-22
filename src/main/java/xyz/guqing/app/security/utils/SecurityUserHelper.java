package xyz.guqing.app.security.utils;

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
	 * @return
	 */
	public static Authentication getCurrentUserAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取当前用户
	 * @return
	 */
	public static Object getCurrentPrincipal(){
		return getCurrentUserAuthentication().getPrincipal();
	}
}
