package xyz.guqing.app.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import xyz.guqing.app.model.bo.CurrentUser;
import xyz.guqing.app.model.bo.MyUserDetails;

import java.util.Objects;

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

	/**
	 * 获取当前用户id
	 * @return 返回当前用户id
	 */
	public static Long getCurrentUserId() {
		MyUserDetails userDetails = (MyUserDetails) getCurrentPrincipal();
		return userDetails.getId();
	}

	public static CurrentUser getCurrentUser() {
		CurrentUser currentUser = new CurrentUser();
		MyUserDetails userDetails = (MyUserDetails) getCurrentPrincipal();
		BeanUtils.copyProperties(userDetails, currentUser);
		return currentUser;
	}

	public static boolean isCurrentUser(Long userId) {
		return Objects.equals(getCurrentUserId(), userId);
	}
	/**
	 * 获取当前登录用户名
	 * @return 返回登录用户名
	 */
	public static String getCurrentUsername() {
		MyUserDetails userDetails = (MyUserDetails) getCurrentPrincipal();
		return userDetails.getUsername();
	}
}
