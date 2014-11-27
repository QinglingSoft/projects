package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.service.SecurityService;
import com.chaos.roadbridge.service.UserService;
import com.opensymphony.xwork2.Action;

@Component
@Scope("prototype")
public class UserAction {
	@Resource
	private UserService userService;
	private User user;
	private String loginName;
	private String errorMessage;
	@Resource
	private SecurityService securityService;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String add() {
		try {
			userService.save(user);
			securityService.initPasswordByLoginName(user.getLoginName());
			return Action.SUCCESS;
		} catch (Exception e) {
			errorMessage = "添加失败";
			return Action.ERROR;
		}
	}

	public String update() {
		try {
			userService.update(user);
			return Action.SUCCESS;
		} catch (Exception e) {
			errorMessage = "修改失败";
			return Action.ERROR;
		}
	}

	public String delete() {
		try {
			userService.delete(loginName);
			return Action.SUCCESS;
		} catch (Exception e) {
			errorMessage = "删除失败";
			return Action.ERROR;
		}
	}

	public String initPassword() {
		try {
			securityService.initPasswordByLoginName(loginName);
			return Action.SUCCESS;
		} catch (Exception e) {
			errorMessage = "初始化密码失败";
			return Action.ERROR;
		}
	}
}
