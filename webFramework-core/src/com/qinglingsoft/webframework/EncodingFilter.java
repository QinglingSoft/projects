/*
 * Copyright 2006 Sun Microsystems, Inc. All rights reserved.
 * Use is subject to license terms.
 */

package com.qinglingsoft.webframework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter {

	private FilterConfig config = null;
	// default to ASCII
	private String targetEncoding = "ASCII";

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
		targetEncoding = config.getInitParameter("encoding");
	}

	public void destroy() {
		config = null;
		targetEncoding = null;
	}

	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) srequest;
		request.setCharacterEncoding(targetEncoding);
		chain.doFilter(srequest, sresponse);
	}
}
