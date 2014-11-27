package com.chaos.roadbridge.web;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.service.SecurityService;
import com.chaos.roadbridge.service.UserService;

/**
 * Application Lifecycle Listener implementation class SuperAdminCreateListener
 * 
 */
public class SuperAdminCreateListener implements ServletContextListener {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public void contextInitialized(ServletContextEvent sce) {

		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		UserService userService = springContext.getBean("userService",
				UserService.class);
		if (userService.findByLoginName(User.SUPER_ADMIN_LOGIN_NAME) != null) {
			logger.fine("超级管理员用户已经存在不需创建");
			return;
		}
		User admin = new User(User.SUPER_ADMIN_LOGIN_NAME, "超级管理员",
				User.Role.ADMIN);
		userService.save(admin);
		SecurityService securityService = springContext.getBean(
				"securityService", SecurityService.class);
		securityService.initPasswordByLoginName(User.SUPER_ADMIN_LOGIN_NAME);
		logger.info("创建超级管理员用户");
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
