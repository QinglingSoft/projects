package com.qinglingsoft.changshuchannel.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qinglingsoft.changshuchannel.service.MapLayerService;

/**
 * Servlet Filter implementation class DefaultMapLayerSetFilter
 */
public class DefaultMapLayerSetFilter implements Filter {

	private static final String MAP_LAYERS_COOKIE_NAME = "mapLayers";
	private FilterConfig fConfig;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (!hasDefaultMapLayersCookie(request)) {
			String defaultMapLayersStr = buildDefaultMapLayersStr();
			HttpServletResponse hresponse = (HttpServletResponse) response;
			hresponse.addCookie(new Cookie(MAP_LAYERS_COOKIE_NAME,
					defaultMapLayersStr));
		}
		chain.doFilter(request, response);
	}

	private boolean hasDefaultMapLayersCookie(ServletRequest request) {
		HttpServletRequest hrequest = (HttpServletRequest) request;
		Cookie[] cookies = hrequest.getCookies();
		if (cookies == null) {
			return false;
		}
		boolean hasCookie = false;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(MAP_LAYERS_COOKIE_NAME)) {
				hasCookie = true;
			}
		}
		return hasCookie;
	}

	private String buildDefaultMapLayersStr() {
		MapLayerService mapLayerService = getSpringContext().getBean(
				"mapLayerService", MapLayerService.class);
		return StringUtils.join(mapLayerService.findDefaultSelectedIds(), ",");
	}

	private ApplicationContext getSpringContext() {
		ServletContext servletContext = fConfig.getServletContext();
		return WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}

	@Override
	public void destroy() {

	}

}
