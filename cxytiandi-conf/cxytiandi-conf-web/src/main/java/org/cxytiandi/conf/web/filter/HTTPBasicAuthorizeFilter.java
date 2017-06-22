package org.cxytiandi.conf.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.cxytiandi.conf.client.common.Constant;
import org.cxytiandi.conf.client.util.CommonUtil;
import org.cxytiandi.conf.web.common.LoginUserInfoUtils;

public class HTTPBasicAuthorizeFilter implements Filter {
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
	private List<String> whiteList = Arrays.asList("/static", "/user/login", "/rest/conf");
	
	private boolean containsWhite(String uri) {
		for (String bkUri : whiteList) {
			if (uri.contains(bkUri.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
		req.setAttribute("projectName", System.getProperty(Constant.SMCONF_PROJECT_NAME) == null ? "猿天地" : System.getProperty(Constant.SMCONF_PROJECT_NAME));
		String path = req.getContextPath();
		String uri = req.getRequestURI().toLowerCase();
		if (!containsWhite(uri)) {
			
			if (LoginUserInfoUtils.getLoginUsername(req) == null) {
				String loginUrl = basePath + path + "/user/login";
				resp.sendRedirect(loginUrl);
				return ;
			} else {
				chain.doFilter(request, response);
			}
			
		} else {
			if (uri.contains("/rest/conf/")) {
				String token = req.getHeader("Authorization");
				if (StringUtils.isNotBlank(token) && token.equals(CommonUtil.getRestApiToken())) {
					chain.doFilter(request, response);
				} else {
					resp.getWriter().write("无权限操作");  
					return;
				}
				
			} else {
				chain.doFilter(request, response);
			}
		
		}
	}
	
	@Override
	public void destroy() {
		
	}

}
