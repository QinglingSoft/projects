package com.qinglingsoft.changshuchannel.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.model.User;
import com.qinglingsoft.changshuchannel.service.UserService;
import com.qinglingsoft.webframework.helper.PageHelper;
import com.qinglingsoft.webframework.transfer.ResultFragment;

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
