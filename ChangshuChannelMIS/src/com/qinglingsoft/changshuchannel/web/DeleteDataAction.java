package com.qinglingsoft.changshuchannel.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.JsonResult;
import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.service.DataTableService;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.StringParamConvertService;

@Component
@Scope("prototype")
public class DeleteDataAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private DataTableService dataTableService;
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
			
			DataTable dataTable = dataTableService.findByName(dataTableName);
			for (DataTable children : dataTable.getChildren()) {
				List<Map<String, Object>> list = propertyJdbcService.findChildBriefAndPk(children.getName(),params);
				if(list!=null&&list.size()>0){
					jsonResult = JsonResult.fail("删除失败，目录下有子节点");
					return Action.ERROR;
				}
			}
			
			int deleted = propertyJdbcService.delete(dataTableName, params);
			
			
			//删除相关联媒体信息
			try{
				String dataTableStr = "T_MEDIA";
				if(!dataTableName.equals(dataTableStr)){
					Map<String, Object> paramMap = new HashMap<String, Object>();
					for (String paramName : primaryKeys.keySet()) {
						String paramStr = primaryKeys.get(paramName);
						paramMap.put("OBJECTID", paramStr);
					}
					paramMap.put("OBJECTTABLENAME", dataTableName);
					propertyJdbcService.delete(dataTableStr, paramMap);
				}
			}catch (Exception e) {
				logger.warning(e.getMessage());
			}
			
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
