package com.qinglingsoft.changshuchannel.model;

import java.util.List;
import java.util.Map;

public class StatisticResult {
	private String[] columnNames;
	private List<Map<String, Object>> dataRowList;

	public StatisticResult(String[] columnNames,
			List<Map<String, Object>> dataRowList) {
		super();
		this.columnNames = columnNames;
		this.dataRowList = dataRowList;
	}

	public String[] getColumnNames() {
		return columnNames;
	}

	public List<Map<String, Object>> getDataRowList() {
		return dataRowList;
	}

}
