package com.qinglingsoft.changshuchannel.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.qinglingsoft.changshuchannel.model.StatisticResult;

@Repository
public class StatisticService {
	private JdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public StatisticResult statistic(String procedureName, String xzqhCodePrefix) {
		String sql = String.format("EXECUTE %s '%s'", procedureName,
				xzqhCodePrefix);
		SqlRowSet rowset = jdbcTemplate.queryForRowSet(sql);
		String[] columnNames = rowset.getMetaData().getColumnNames();
		List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
		return new StatisticResult(columnNames, data);
	}
}
