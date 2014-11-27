package com.chaos.roadbridge.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.FieldTypeUnsupportedException;
import com.chaos.roadbridge.model.DataTable;
import com.chaos.roadbridge.service.DataTableService;
import com.chaos.roadbridge.service.PropertyJdbcService;
import com.chaos.roadbridge.service.StringParamConvertService;

@Component
@Scope("prototype")
public class ChildTableDataHelper {
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private DataTableService dataTableService;
	@Resource
	private StringParamConvertService stringParamConvertService;
	private Map<String, String> parentPrimaryKeys = new HashMap<String, String>();
	private String dataTableName;

	public Map<String, String> getParentPrimaryKeys() {
		return parentPrimaryKeys;
	}

	public void setParentPrimaryKeys(Map<String, String> parentPrimaryKeys) {
		this.parentPrimaryKeys = parentPrimaryKeys;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public Map<String, Object> getParentBrief()
			throws FieldTypeUnsupportedException {
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataTable parentTable = dataTable.getParent();
		Map<String, Object> parentPrimaryKeyValues = stringParamConvertService
				.convert(parentTable.getName(), parentPrimaryKeys);
		return propertyJdbcService.findBrief(parentTable.getName(),
				parentPrimaryKeyValues);
	}

	public List<Map<String, Object>> getDataList()
			throws FieldTypeUnsupportedException {
		if (parentPrimaryKeys == null || parentPrimaryKeys.isEmpty()) {
			return null;
		}
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataTable parentTable = dataTable.getParent();
		Map<String, Object> parentPrimaryKeyValues = stringParamConvertService
				.convert(parentTable.getName(), parentPrimaryKeys);
		return propertyJdbcService.findChildMap(dataTableName,
				parentPrimaryKeyValues);
	}

	public List<Map<String, Object>> getBriefList()
			throws FieldTypeUnsupportedException {
		if (parentPrimaryKeys == null || parentPrimaryKeys.isEmpty()) {
			return null;
		}
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataTable parentTable = dataTable.getParent();
		Map<String, Object> parentPrimaryKeyValues = stringParamConvertService
				.convert(parentTable.getName(), parentPrimaryKeys);
		return propertyJdbcService.findChildBriefAndPk(dataTableName,
				parentPrimaryKeyValues);
	}

	public boolean isHasRecord() throws FieldTypeUnsupportedException {
		if (parentPrimaryKeys == null || parentPrimaryKeys.isEmpty()) {
			return false;
		}
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataTable parentTable = dataTable.getParent();
		Map<String, Object> parentPrimaryKeyValues = stringParamConvertService
				.convert(parentTable.getName(), parentPrimaryKeys);
		return propertyJdbcService.hasRecord(dataTableName,
				parentPrimaryKeyValues);

	}
}
