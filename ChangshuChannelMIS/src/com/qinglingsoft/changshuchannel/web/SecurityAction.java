package com.qinglingsoft.changshuchannel.web;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.model.T_GL_HDZZJG;
import com.qinglingsoft.changshuchannel.model.User;
import com.qinglingsoft.changshuchannel.service.DepartmentService;
import com.qinglingsoft.changshuchannel.service.SecurityService;
import com.qinglingsoft.changshuchannel.service.UserService;

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
	@Resource
	private DepartmentService departmentService;

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
		int permission = 0;
		if(user.getDeptCode()!=null){
			T_GL_HDZZJG jg = departmentService.get(Long.valueOf(user.getDeptCode()));
			if(jg!=null){
				if("1".equals(jg.getSfxg_hdjcxx())) permission|= 1L << 1;
				if("1".equals(jg.getSfbj_hdgcgh())) permission|= 1L << 2;
				if("1".equals(jg.getSfbj_hdyhgh())) permission|= 1L << 3;
				if("1".equals(jg.getSfbj_xhjh())) permission|= 1L << 4;
			}
		}
		if(user.isSuperAdmin()){
			permission =99;
		}
		user.setPermission(permission);
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

	public static void main(String[] args) {
		System.out.println((1L<<2)+(0<<0));
	}
}
