package com.qinglingsoft.changshuchannel.model;

import com.qinglingsoft.changshuchannel.model.DataField.Operator;

@SuppressWarnings("serial")
public class StringCondition extends FieldCondition {
	private String value;

	public StringCondition(String fieldName, String fieldLabel,
			Operator operator, String value) {
		super(fieldName, fieldLabel, operator);
		this.value = value;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s %s “%s”", fieldLabel, operator.getText(),
				getValue());
	}

}
