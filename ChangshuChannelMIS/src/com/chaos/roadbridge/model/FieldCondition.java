package com.chaos.roadbridge.model;

import com.chaos.roadbridge.model.DataField.Operator;

@SuppressWarnings("serial")
public abstract class FieldCondition implements Condition {
	protected String fieldName;
	protected String fieldLabel;
	protected DataField.Operator operator;

	public FieldCondition(String fieldName, String fieldLabel, Operator operator) {
		this.fieldName = fieldName;
		this.fieldLabel = fieldLabel;
		this.operator = operator;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldLabel() {
		return fieldLabel;
	}

	public DataField.Operator getOperator() {
		return operator;
	}

	public abstract Object getValue();

	@Override
	public String toString() {
		return String.format("%s %s %s", fieldLabel, operator.getText(),
				getValue());
	}

}
