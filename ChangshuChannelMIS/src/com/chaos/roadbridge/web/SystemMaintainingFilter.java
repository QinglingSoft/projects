package com.chaos.roadbridge.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chaos.roadbridge.service.SystemMaintainService;

/**
 * 系统维护时阻止用户访问功能
 */
public class SystemMaintainingFilter implements Filter {
	private WebApplicationContext springContext;

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		SystemMaintainService systemMaintainService = springContext.getBean(
				"systemMaintainService", SystemMaintainService.class);
		if (systemMaintainService.isMaintaining()) {
			HttpServletRequest hrequest = (HttpServletRequest) request;
			hrequest.getRequestDispatcher("/systemMaintaining.jsp").forward(
					request, response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		springContext = WebApplicationContextUtils
				.getWebApplicationContext(fConfig.getServletContext());

	}

}
