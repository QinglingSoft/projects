package com.qinglingsoft.changshuchannel.web;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.JsonResult;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.StringParamConvertService;

@Component
@Scope("prototype")
public class DeleteDataAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private StringParamConvertService stringParamConvertService;
	private String dataTableName;
	private Map<String, String> primaryKeys = new HashMap<String, String>();
	private JsonResult jsonResult;

	public Map<String, String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public JsonResult getJsonResult() {
		return jsonResult;
	}

	public String delete() {
		try {
			Map<String, Object> params = stringParamConvertService.convert(
					dataTableName, primaryKeys);
			int deleted = propertyJdbcService.delete(dataTableName, params);

			jsonResult = JsonResult.success(deleted);
			return Action.SUCCESS;
		} catch (FieldTypeUnsupportedException e) {
			logger.warning(e.getMessage());
			jsonResult = JsonResult.fail(String.format("%s.%s(%s)字段类型不受支持", e
					.getDataTableName(), e.getFieldName(), e.getFieldType()));
			return Action.ERROR;
		}
	}
}
