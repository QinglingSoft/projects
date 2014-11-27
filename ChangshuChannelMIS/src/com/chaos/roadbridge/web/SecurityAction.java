package com.chaos.roadbridge.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.service.SecurityService;
import com.chaos.roadbridge.service.UserService;
import com.opensymphony.xwork2.Action;

@Component
@Scope("prototype")
public class SecurityAction implements SessionAware {
	private Map<String, Object> sessionScope;

	@Override
	public void setSession(Map<String, Object> sessionScope) {
		this.sessionScope = sessionScope;
	}

	private String loginName;
	private String password;
	private String oldPassword;
	private String newPassword;
	@Resource
	private SecurityService securityService;
	private String errorMessage;
	@Resource
	private UserService userService;

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String login() {
		if (!securityService.checkPassword(loginName, password)) {
			errorMessage = "登录失败";
			return Action.ERROR;
		}
		User user = userService.findByLoginName(loginName);
		sessionScope.put(SessionAttributeNames.LOGIN_USER, user);
		return Action.SUCCESS;
	}

	public String logout() {
		sessionScope.remove(SessionAttributeNames.LOGIN_USER);
		ServletActionContext.getRequest().getSession()
				.setMaxInactiveInterval(0);
		return Action.SUCCESS;
	}

	public String changePassword() {

		User loginUser = (User) sessionScope
				.get(SessionAttributeNames.LOGIN_USER);
		if (!securityService.checkPassword(loginUser.getLoginName(),
				oldPassword)) {
			errorMessage = "原密码错误";
			return Action.ERROR;
		}
		securityService.updatePassword(loginUser.getLoginName(), newPassword);
		errorMessage = "密码更改成功";
		return Action.SUCCESS;
	}

}
