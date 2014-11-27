package com.chaos.roadbridge.web;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.model.CodeTable;
import com.chaos.roadbridge.service.CodeTableService;

@Component
@Scope("prototype")
public class CodeTableHelper {
	@Resource
	private CodeTableService codeTableService;
	private String codeTableName;

	public void setCodeTableName(String codeTableName) {
		this.codeTableName = codeTableName;
	}

	public CodeTable getCodeTable() {
		return codeTableService.findByName(codeTableName);
	}
}
