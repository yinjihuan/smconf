package org.cxytiandi.conf.web.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取登录用户信息工具类
 * @author yinjihuan
 *
 */
public class LoginUserInfoUtils {
	
	/**
	 * 获取登录用户名
	 * @param request
	 * @return
	 */
	public static String getLoginUsername(HttpServletRequest request) {
		if (request.getSession().getAttribute("login_user_name") == null) {
			return null;
		}
		return request.getSession().getAttribute("login_user_name").toString();
	}
	
	/**
	 * 获取登录用户能操作的环境
	 * @author yinjihuan
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getLoginUserEvns(HttpServletRequest request) {
		if (request.getSession().getAttribute("login_user_envs") == null) {
			return null;
		}
		return (List<String>)request.getSession().getAttribute("login_user_envs");
	}
}
