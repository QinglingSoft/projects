package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.webframework.helper.PageHelper;
import com.qinglingsoft.webframework.transfer.ResultFragment;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.service.UserService;

@Component
@Scope("prototype")
public class UserPageHelper extends PageHelper<User> {
	@Resource
	private UserService userService;

	@Override
	protected ResultFragment<User> getResultFragment() {
		return userService.fragment(this.getPageStart(), this.getPageSize());
	}

}
