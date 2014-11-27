package com.chaos.roadbridge;

public class FieldTypeUnsupportedException extends Exception {

	private static final long serialVersionUID = 3L;
	private String dataTableName;
	private String fieldName;
	private String fieldType;

	public FieldTypeUnsupportedException(String dataTableName,
			String fieldName, String fieldType) {
		super();
		this.dataTableName = dataTableName;
		this.fieldName = fieldName;
		this.fieldType = fieldType;
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

	@Override
	public String getMessage() {
		return "unsupported field type: " + dataTableName + "." + fieldName
				+ "(" + fieldType + ")";
	}

}
