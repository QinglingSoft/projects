package com.qinglingsoft.changshuchannel;

import java.util.Map;

public class FieldIndex {
	private String dataTableName;
	private Map<String, Object> primaryKeyValues;
	private String fieldName;
	private String fileContentType;

	public FieldIndex(String dataTableName,
			Map<String, Object> primaryKeyValues, String fieldName,
			String fileContentType) {
		super();
		this.dataTableName = dataTableName;
		this.primaryKeyValues = primaryKeyValues;
		this.fieldName = fieldName;
		this.fileContentType = fileContentType;
	}

	public FieldIndex(String dataTableName,
			Map<String, Object> primaryKeyValues, String fieldName) {
		super();
		this.dataTableName = dataTableName;
		this.primaryKeyValues = primaryKeyValues;
		this.fieldName = fieldName;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public Map<String, Object> getPrimaryKeyValues() {
		return primaryKeyValues;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

}
