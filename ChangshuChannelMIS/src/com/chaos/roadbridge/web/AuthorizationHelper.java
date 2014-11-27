package com.chaos.roadbridge.web;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.User;
import com.chaos.roadbridge.service.AuthorizationService;

@Component
@Scope("prototype")
public class AuthorizationHelper {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private AuthorizationService authorizationService;
	private User user;
	private String dataTableName;
	private Map<String, Object> primaryKeyValues = new HashMap<String, Object>();

	public void setUser(User user) {
		this.user = user;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public Map<String, Object> getPrimaryKeyValues() {
		return primaryKeyValues;
	}

	public void setPrimaryKeyValues(Map<String, Object> primaryKeyValues) {
		this.primaryKeyValues = primaryKeyValues;
	}

	public boolean isHasPermission() {
		if (user == null) {
			logger.fine("授权时没有提供用户对象，拒绝授权");
			return false;
		}
		if (dataTableName == null) {
			logger.fine("授权时没有提供目标表名，拒绝授权");
			return false;
		}
		if (primaryKeyValues.isEmpty()) {
			logger.fine("授权时没有提供目标记录主键，拒绝授权");
			return false;
		}
		return authorizationService.hasPermission(user, dataTableName,
				primaryKeyValues);
	}
}
