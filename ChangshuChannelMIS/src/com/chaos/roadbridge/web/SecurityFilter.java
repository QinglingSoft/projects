package com.chaos.roadbridge.web;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.model.User.Role;

/**
 * Servlet Filter implementation class SecurityFilter
 */
public class SecurityFilter implements Filter {

	public void destroy() {

	}

	private static final Set<String> NO_LOGIN_PATHS;
	static {
		Set<String> noLoginPaths = new HashSet<String>();
		noLoginPaths.add("/login.jsp");
		noLoginPaths.add("/login.action");
		noLoginPaths.add("/nologin.jsp");
		noLoginPaths.add("/systemMaintaining.jsp");
		NO_LOGIN_PATHS = Collections.unmodifiableSet(noLoginPaths);
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		String requestPath = getRequestPath(hrequest);
		if (!NO_LOGIN_PATHS.contains(requestPath)) {
			User user = (User) hrequest.getSession().getAttribute(
					SessionAttributeNames.LOGIN_USER);
			if (user == null) {
				hrequest.getRequestDispatcher("/nologin.jsp").forward(request,
						response);
				return;
			} else if (needAdmin(requestPath) && user.getRole() != Role.ADMIN) {
				hrequest.getRequestDispatcher("/noPermission.jsp").forward(
						request, response);
				return;
			}
		}
		try {
			chain.doFilter(request, response);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}

	private boolean needAdmin(String requestPath) {
		if (requestPath.startsWith("/admin/")) {
			return true;
		}
		if (requestPath.startsWith("/article/")) {
			return true;
		}
		return false;
	}

	private String getRequestPath(HttpServletRequest hrequest) {
		return hrequest.getServletPath();
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
