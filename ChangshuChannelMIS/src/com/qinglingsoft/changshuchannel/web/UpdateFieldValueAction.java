package com.qinglingsoft.changshuchannel.web;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.IncorrectFieldTypeRegistryException;
import com.qinglingsoft.changshuchannel.JsonResult;
import com.qinglingsoft.changshuchannel.NoRecordFoundException;
import com.qinglingsoft.changshuchannel.RecordNotUniqueException;
import com.qinglingsoft.changshuchannel.service.DataFormatService;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.ReportService;
import com.qinglingsoft.changshuchannel.service.StringParamConvertService;

@Component
@Scope("prototype")
public class UpdateFieldValueAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private StringParamConvertService stringParamConvertService;
	@Resource
	private DataFormatService dataFormatService;
	@Resource
	private ReportService reportService;
	
	private String dataTableName;
	private Map<String, String> primaryKeys = new HashMap<String, String>();
	private String fieldName;
	private String fieldValue;
	private JsonResult jsonResult;

	public Map<String, String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public JsonResult getJsonResult() {
		return jsonResult;
	}

	public String update() {
		try {
			Object newValue = stringParamConvertService.convert(dataTableName,
					fieldName, fieldValue);
			Map<String, Object> primaryKeyValues = stringParamConvertService
					.convert(dataTableName, primaryKeys);
			Object updateResult = propertyJdbcService.updateSingleField(
					dataTableName, fieldName, primaryKeyValues, newValue);
			jsonResult = JsonResult.success(updateResult == null ? null
					: dataFormatService.format(dataTableName, fieldName,
							updateResult));
			//
			reportService.setTableValueByParam(dataTableName, fieldName, fieldValue, primaryKeys);
			
		} catch (FieldTypeUnsupportedException e) {
			jsonResult = JsonResult.fail("字段类型" + e.getFieldType() + "不受支持");
		} catch (NumberFormatException e) {
			jsonResult = JsonResult.fail("数字格式错误");
		} catch (IllegalArgumentException e) {
			jsonResult = JsonResult.fail("格式错误");
		} catch (NoRecordFoundException e) {
			jsonResult = JsonResult.fail("没有符合条件的记录");
		} catch (RecordNotUniqueException e) {
			jsonResult = JsonResult.fail("符合条件的记录不唯一");
		} catch (DataAccessException e) {
			logger.warning("更新失败：" + e.getLocalizedMessage());
			String message = e.getLocalizedMessage();
			if (e.getMostSpecificCause() instanceof java.sql.DataTruncation) {
				message = "内容过长";
			}
			jsonResult = JsonResult.fail("更新失败：" + message);
		} catch (IncorrectFieldTypeRegistryException e) {
			jsonResult = JsonResult.fail(String.format("字段%s注册类型为%s，取得数据为%s型",
					e.getFieldName(), e.getRegisterType(), e.getValueType()));
		} catch (Exception e) {
			logger.warning("更新失败：" + e.getLocalizedMessage());
			jsonResult = JsonResult.fail("更新失败：" + e.getLocalizedMessage());
		}
		return Action.SUCCESS;
	}
}
