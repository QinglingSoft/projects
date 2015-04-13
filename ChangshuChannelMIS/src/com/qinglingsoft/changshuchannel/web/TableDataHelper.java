package com.qinglingsoft.changshuchannel.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;

@Component
@Scope("prototype")
public class TableDataHelper {
	@Resource
	private PropertyJdbcService propertyJdbcService;
	private Map<String, Object> primaryKeys = new HashMap<String, Object>();
	private String dataTableName;

	public Map<String, Object> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setPrimaryKeys(Map<String, Object> parentPrimaryKeys) {
		this.primaryKeys = parentPrimaryKeys;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public Map<String, Object> getData() {
		if (primaryKeys == null || primaryKeys.isEmpty()) {
			return null;
		}
		return propertyJdbcService.findSingleMap(dataTableName, primaryKeys);
	}
	
	/**
	 * primaryKeys主要是定义为其他参数，并非一定为主键
	 * @return
	 */
	public List<Map<String, Object>> getDataList() {
		//primaryKeys 
		if (primaryKeys == null || primaryKeys.isEmpty()) {
			return null;
		}
		return propertyJdbcService.findChildBriefAndPk(dataTableName, primaryKeys);
	}
}
