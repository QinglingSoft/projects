package com.chaos.roadbridge;

import com.chaos.roadbridge.model.DataField;
import com.chaos.roadbridge.model.DataField.Type;

public class IncorrectFieldTypeRegistryException extends Exception {

	private static final long serialVersionUID = 1L;
	private String dataTableName;
	private String fieldName;
	private DataField.Type registerType;
	private Class<?> valueType;

	public IncorrectFieldTypeRegistryException(String dataTableName,
			String fieldName, Type registerType, Class<?> valueType) {
		super();
		this.dataTableName = dataTableName;
		this.fieldName = fieldName;
		this.registerType = registerType;
		this.valueType = valueType;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public DataField.Type getRegisterType() {
		return registerType;
	}

	public Class<?> getValueType() {
		return valueType;
	}

}
