package xyz.guqing.app.security.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.guqing.app.security.support.MyUserDetails;

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
	 * @return 返回认证对象
	 */
	public static Authentication getCurrentUserAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取当前用户
	 * @return 返回当前用户
	 */
	public static Object getCurrentPrincipal(){
		return getCurrentUserAuthentication().getPrincipal();
	}

	/**
	 * 获取当前用户id
	 * @return 返回当前用户id
	 */
	public static Integer getUserId() {
		MyUserDetails userDetails = (MyUserDetails) getCurrentPrincipal();
		return userDetails.getId();
	}
}
