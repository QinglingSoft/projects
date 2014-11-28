package com.qinglingsoft.changshuchannel.model;

import java.sql.Date;

import com.qinglingsoft.changshuchannel.model.DataField.Operator;

@SuppressWarnings("serial")
public class DateCondition extends FieldCondition {
	private Date value;

	public DateCondition(String fieldName, String fieldLabel,
			Operator operator, Date value) {
		super(fieldName, fieldLabel, operator);
		this.value = value;
	}

	@Override
	public Date getValue() {
		return value;
	}

}
