package com.chaos.roadbridge.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class ChildTableCondition implements Condition {
	private String childTableName;
	private Set<FieldCondition> fieldConditions = new HashSet<FieldCondition>();

	public ChildTableCondition() {
	}

	public ChildTableCondition(String childTableName) {
		this.childTableName = childTableName;
	}

	public String getChildTableName() {
		return childTableName;
	}

	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}

	public Set<FieldCondition> getFieldConditions() {
		return fieldConditions;
	}

	public void addFieldCondition(FieldCondition fieldCondition) {
		fieldConditions.add(fieldCondition);
	}

	public void addFieldConditions(Collection<FieldCondition> fieldConditions) {
		this.fieldConditions.addAll(fieldConditions);
	}

	@Override
	public String toString() {
		return String.format("针对%s的子表条件", childTableName);
	}

}
