package com.chaos.roadbridge.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;

public class FragmentResultSetExtractor implements
		ResultSetExtractor<List<Map<String, Object>>> {
	private ColumnMapRowMapper rowMapper = new ColumnMapRowMapper();
	private int firstResult;
	private int maxResults;

	public FragmentResultSetExtractor(int firstResult, int maxResults) {
		super();
		this.firstResult = firstResult;
		this.maxResults = maxResults;
	}

	@Override
	public List<Map<String, Object>> extractData(ResultSet rs)
			throws SQLException, DataAccessException {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < firstResult; i++) {
			if (!rs.next()) {
				break;
			}
		}
		for (int i = 0; i < maxResults; i++) {
			if (!rs.next()) {
				break;
			}
			resultList.add(rowMapper.mapRow(rs, firstResult + i));
		}
		return resultList;
	}

}
