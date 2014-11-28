package com.qinglingsoft.changshuchannel.web;

import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.qinglingsoft.changshuchannel.model.User;
import com.qinglingsoft.changshuchannel.service.SecurityService;
import com.qinglingsoft.changshuchannel.service.UserService;

/**
 * Application Lifecycle Listener implementation class SuperAdminCreateListener
 * 
 */
public class GuestUserCreateListener implements ServletContextListener {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	public void contextInitialized(ServletContextEvent sce) {

		WebApplicationContext springContext = WebApplicationContextUtils
				.getWebApplicationContext(sce.getServletContext());
		UserService userService = springContext.getBean("userService",
				UserService.class);
		if (userService.findByLoginName(User.GUEST_LOGIN_NAME) != null) {
			logger.fine("访客用户已经存在不需创建");
			return;
		}
		User guest = new User(User.GUEST_LOGIN_NAME, "访客", User.Role.USER);
		userService.save(guest);
		SecurityService securityService = springContext.getBean(
				"securityService", SecurityService.class);
		securityService.initPasswordByLoginName(User.GUEST_LOGIN_NAME);
		logger.info("创建访客用户");
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}
}
