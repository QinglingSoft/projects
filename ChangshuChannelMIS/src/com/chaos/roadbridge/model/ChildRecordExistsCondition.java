package com.chaos.roadbridge.model;

@SuppressWarnings("serial")
public class ChildRecordExistsCondition implements Condition {
	private String childTableName;

	public ChildRecordExistsCondition(String childTableName) {
		this.childTableName = childTableName;
	}

	public String getChildTableName() {
		return childTableName;
	}

	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}

	@Override
	public String toString() {
		return String.format("子表%s中有对应记录", childTableName);
	}

}
