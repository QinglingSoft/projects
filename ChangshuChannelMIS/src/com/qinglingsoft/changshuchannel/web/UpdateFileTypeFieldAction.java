package com.qinglingsoft.changshuchannel.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldIndex;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.JsonResult;
import com.qinglingsoft.changshuchannel.NoRecordFoundException;
import com.qinglingsoft.changshuchannel.RecordNotUniqueException;
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
}
