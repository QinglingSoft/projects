package com.qinglingsoft.webframework;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.qinglingsoft.webframework.helper.PageHelper;

/**
 * 缺省页容量配置监听器，负责在web应用启动时将名为
 * {@linkplain com.qinglingsoft.webframework.DefaultPageSizeConfigListener#DEFAULT_PAGE_SIZE_PARAM_NAME}
 * 的context-param读入作为翻翻页策略的缺省页容量。
 * 
 * @author guoqiang
 * 
 */
public class DefaultPageSizeConfigListener implements ServletContextListener {
	private static final String DEFAULT_PAGE_SIZE_PARAM_NAME = "com.qinglingsoft.webframework.helper.DEFAULT_PAGE_SIZE";
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		String defaultPageSizeStr = sce.getServletContext().getInitParameter(
				DEFAULT_PAGE_SIZE_PARAM_NAME);
		if (defaultPageSizeStr == null) {
			return;
		}
		try {
			int defaultPageSize = Integer.parseInt(defaultPageSizeStr);
			PageHelper.setDefaultPageSize(defaultPageSize);
		} catch (Exception e) {
			logger.log(Level.WARNING, String.format(
					"context param %s format error.",
					DEFAULT_PAGE_SIZE_PARAM_NAME), e);
		}
	}

}
