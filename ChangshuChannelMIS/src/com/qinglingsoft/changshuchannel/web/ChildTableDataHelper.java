package com.qinglingsoft.changshuchannel.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.service.DataTableService;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.StringParamConvertService;

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
	private Map<String, Object> params = new HashMap<String, Object>();
	private String dataTableName;

	
	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

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
	
	/**
	 * 根据子节点递归查询父节点,用于树查询展开节点
	 * @return fieldName_value
	 * @throws FieldTypeUnsupportedException
	 */
	public String getParentPrimaryKeyStr() throws FieldTypeUnsupportedException {
		List<String> parentList = new ArrayList<String>();
		getParentList(parentList,params,dataTableName);
		Collections.reverse(parentList);
		StringBuffer valueBuf = new StringBuffer();
		String value = "";
		for (String pl : parentList) {
			valueBuf.append(pl).append(",");
		}
		if(valueBuf.length()>0){
			value = valueBuf.substring(0,valueBuf.length()-1).toString();
		}
		return value;
	}
	
	
	private void getParentList(List<String> parentList,Map<String, Object> params,String dataTableName) throws FieldTypeUnsupportedException {
		
		if (params == null || params.isEmpty()) {
			return;
		}
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataTable parentTable = dataTable.getParent();
		StringBuffer parentKeyBuf = new StringBuffer();
		if(parentTable!=null){
			Map<String, Object> parentParams = new HashMap<String, Object>();
			Map<String, Object> resultMap = propertyJdbcService.findSingleMap(dataTableName,params);
			for (String resultName : resultMap.keySet()) {
				if(parentTable.getPrimaryKeyNames().contains(resultName)){
					String value = String.valueOf(resultMap.get(resultName));
					parentKeyBuf.append(resultName).append("_").append(value).append("_");
					parentParams.put(resultName, value);
				}
			}
			if(parentKeyBuf.length()>0){
				parentList.add(parentKeyBuf.substring(0,parentKeyBuf.length()-1).toString());
			}
			
			getParentList(parentList,parentParams,parentTable.getName());
		}
		
		
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
