package com.qinglingsoft.changshuchannel.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.IllegalFieldTypeException;
import com.qinglingsoft.changshuchannel.model.FileTypeValue;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.StringParamConvertService;

@Component
@Scope("prototype")
public class DownloadFileTypeDataAction {
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private StringParamConvertService stringParamConvertService;
	private String dataTableName;
	private Map<String, String> primaryKeys = new HashMap<String, String>();
	private String fieldName;
	private FileTypeValue fileToDownload;
	private String errorMessage;

	public Map<String, String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public InputStream getFileStream() {
		return new ByteArrayInputStream(fileToDownload.getContent());
	}

	public FileTypeValue getFileToDownload() {
		return fileToDownload;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String download() {
		try {
			Map<String, Object> pkValuess = stringParamConvertService.convert(
					dataTableName, primaryKeys);
			fileToDownload = propertyJdbcService.findFileTypeValue(
					dataTableName, pkValuess, fieldName);
			if (fileToDownload == null) {
				errorMessage = "没有找到指定字段的值";
				return "fieldNull";
			}
			return Action.SUCCESS;
		} catch (FieldTypeUnsupportedException e) {
			errorMessage = String.format("%s.%s(%s)字段类型不受支持", e
					.getDataTableName(), e.getFieldName(), e.getFieldType());
			return Action.ERROR;
		} catch (IllegalFieldTypeException e) {
			errorMessage = String.format("%s.%s(%s)字段不是需要的%型", e
					.getDataTableName(), e.getFieldName(), e.getFieldType(), e
					.getExpectFieldType());
			return Action.ERROR;
		}
	}
}
