package com.qinglingsoft.changshuchannel.model;

import com.qinglingsoft.changshuchannel.model.DataField.Operator;

@SuppressWarnings("serial")
public class NumberCondition extends FieldCondition {
	private Number value;

	public NumberCondition(String fieldName, String fieldLabel,
			Operator operator, Number value) {
		super(fieldName, fieldLabel, operator);
		this.value = value;
	}

	@Override
	public Number getValue() {
		return value;
	}

}
