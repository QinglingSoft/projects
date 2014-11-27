package com.chaos.roadbridge;

import java.util.Collections;
import java.util.Map;

public class RecordIndex {
	private String dataTableName;
	private Map<String, Object> primaryKeyValues;

	public RecordIndex(String dataTableName,
			Map<String, Object> primaryKeyValues) {
		this.dataTableName = dataTableName;
		this.primaryKeyValues = primaryKeyValues;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public Map<String, Object> getPrimaryKeyValues() {
		return Collections.unmodifiableMap(primaryKeyValues);
	}
}
