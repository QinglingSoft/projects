package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.service.UserService;

@Component
@Scope("prototype")
public class UserHelper {
	@Resource
	private UserService userService;
	private String loginName;

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public User getUser() {
		return userService.findByLoginName(loginName);
	}
}
