package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.service.SystemMaintainService;
import com.opensymphony.xwork2.Action;

@Component
@Scope("prototype")
public class SystemMaintainAction {
	@Resource
	private SystemMaintainService systemMaintainService;

	private String backupFileName;

	public String getBackupFileName() {
		return backupFileName;
	}

	public void setBackupFileName(String backupFileName) {
		this.backupFileName = backupFileName;
	}

	public String backup() {
		backupFileName = systemMaintainService.backup();
		return Action.SUCCESS;
	}
}
