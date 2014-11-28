package com.qinglingsoft.changshuchannel.model;

import java.util.List;

import com.qinglingsoft.changshuchannel.model.DataField.Operator;

@SuppressWarnings("serial")
public class CodeCondition extends FieldCondition {
	private List<String> value;
	private List<String> texts;

	public CodeCondition(String fieldName, String fieldLabel,
			Operator operator, List<String> value, List<String> texts) {
		super(fieldName, fieldLabel, operator);
		this.value = value;
		this.texts = texts;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.format("%s%s%s", fieldLabel, operator.getText(), texts);
	}

}
