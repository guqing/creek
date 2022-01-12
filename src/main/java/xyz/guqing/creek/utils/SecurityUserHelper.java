package xyz.guqing.creek.utils;

import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import xyz.guqing.creek.exception.UnauthorizedException;
import xyz.guqing.creek.model.bo.CurrentUser;

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
		return Objects.requireNonNull(getCurrentUserAuthentication().getPrincipal());
	}

	public static CurrentUser getCurrentUser() {
		CurrentUser currentUser = new CurrentUser();
		Object principal = getCurrentPrincipal();
		if(principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			BeanUtils.copyProperties(userDetails, currentUser);
			return currentUser;
		}
		throw new UnauthorizedException("未登录，请先登录后访问");
	}

	public static boolean isCurrentUser(String username) {
		return Objects.equals(getCurrentUsername(), username);
	}

	/**
	 * 获取当前登录用户名
	 * @return 返回登录用户名
	 */
	public static String getCurrentUsername() {
		Object principal = getCurrentPrincipal();
		if (principal instanceof UserDetails) {
			UserDetails userDetails = (UserDetails) principal;
			return userDetails.getUsername();
		}
		if (principal instanceof String) {
			return (String) principal;
		}
		throw new UnauthorizedException("未登录，请先登录后访问");
	}
}
