package com.qinglingsoft.changshuchannel;

public class IllegalFieldTypeException extends Exception {
	private static final long serialVersionUID = 1L;

	private String dataTableName;
	private String fieldName;
	private String fieldType;
	private String expectFieldType;

	public IllegalFieldTypeException(String dataTableName, String fieldName,
			String fieldType, String expectFieldType) {
		super();
		this.dataTableName = dataTableName;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
		this.expectFieldType = expectFieldType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public String getExpectFieldType() {
		return expectFieldType;
	}

	@Override
	public String getMessage() {
		return String.format("Illegal field type %s.%s(%s), expected %s.",
				dataTableName, fieldName, fieldType, expectFieldType);
	}
}
