package com.qinglingsoft.changshuchannel;

import java.util.Collections;
import java.util.Map;

/**
 * 记录索引，用表名和主键对来唯一确定一条记录
 * 
 * @author 郭强
 *
 */
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
