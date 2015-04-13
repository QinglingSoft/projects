package com.qinglingsoft.changshuchannel.web;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldIndex;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.JsonResult;
import com.qinglingsoft.changshuchannel.NoRecordFoundException;
import com.qinglingsoft.changshuchannel.RecordNotUniqueException;
import com.qinglingsoft.changshuchannel.model.Code;
import com.qinglingsoft.changshuchannel.model.CodeTable;
import com.qinglingsoft.changshuchannel.service.CodeTableService;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.StringParamConvertService;

@Component
@Scope("prototype")
public class UpdateFileTypeFieldAction {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private StringParamConvertService stringParamConvertService;
	@Resource
	private CodeTableService codeTableService;
	private String dataTableName;
	private String fieldName;
	private Map<String, String> primaryKeys = new HashMap<String, String>();
	private File upload;
	private String uploadFileName;
	private String uploadContentType;
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

	public void setUpload(File upload) {
		this.upload = upload;
	}

	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}

	public JsonResult getJsonResult() {
		return jsonResult;
	}

	public String update() {
		try {
			Map<String, Object> primaryKeyValues = stringParamConvertService
					.convert(dataTableName, primaryKeys);
			String extName = uploadFileName.substring(uploadFileName
					.lastIndexOf('.') + 1);
			propertyJdbcService.updateFileTypeField(dataTableName, fieldName,
					primaryKeyValues, upload, extName);
			jsonResult = JsonResult.success(new FieldIndex(dataTableName,
					primaryKeyValues, fieldName, uploadContentType));
		} catch (FieldTypeUnsupportedException e) {
			jsonResult = JsonResult.fail("文件型字段不可作为主键");
		} catch (NoRecordFoundException e) {
			jsonResult = JsonResult.fail("指定记录不存在");
		} catch (RecordNotUniqueException e) {
			jsonResult = JsonResult.fail("指定记录不唯一");
		} catch (IOException e) {
			jsonResult = JsonResult.fail("文件读取错误");
		} catch (DataAccessException e) {
			logger.log(Level.WARNING, "插入文件错误", e);
			jsonResult = JsonResult.fail("失败：" + e.getLocalizedMessage());
		}

		return Action.SUCCESS;
	}
	
	public String add() {
		try {
			
			Map<String, Object> keyValues = new HashMap<String, Object>();
			Map<String, Object> pkeyValues = stringParamConvertService
					.convert(dataTableName, primaryKeys);
			
			// 组织参数表
			Map<String, Object> params = new HashMap<String, Object>();
			keyValues.put("OBJECTTABLENAME", dataTableName);
			params.put("OBJECTTABLENAME", dataTableName);
			for (Object obj : pkeyValues.values()) {
				if(obj  instanceof BigDecimal){
					params.put("OBJECTID", obj);
					keyValues.put("OBJECTID", obj);
				}
			}
			String extName = uploadFileName.substring(uploadFileName
					.lastIndexOf('.') + 1);
			CodeTable codeTable = codeTableService.findByName("TC_MDDM");
			for (Code c : codeTable.getCodes().values()) {
				if(c.getMeaning().equalsIgnoreCase(extName)){
					params.put("MEDIATYPE", c.getValue());
				}
			}
			params.put("MEDIACONTEXT", FileUtils.readFileToByteArray(upload));
			
			propertyJdbcService.insert("T_MEDIA", params);
			jsonResult = JsonResult.success(new FieldIndex(dataTableName,
					keyValues, "MEDIACONTEXT", uploadContentType));
		} catch (FieldTypeUnsupportedException e) {
			jsonResult = JsonResult.fail("文件型字段不可作为主键");
		} catch (DataAccessException e) {
			logger.log(Level.WARNING, "插入文件错误", e);
			jsonResult = JsonResult.fail("失败：" + e.getLocalizedMessage());
		} catch (IOException e) {
			logger.log(Level.WARNING, "读取文件错误", e);
			jsonResult = JsonResult.fail("失败：" + e.getLocalizedMessage());
		}

		return Action.SUCCESS;
	}
}
