package com.qinglingsoft.changshuchannel.service;

import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.qinglingsoft.changshuchannel.RecordIndex;
import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.model.User;

@Service
public class AuthorizationService {
	@Resource
	private DataTableService dataTableService;
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public boolean hasPermission(User user, String dataTableName,
			Map<String, Object> recordPk) {
		return hasPermission(user, new RecordIndex(dataTableName, recordPk));
	}

	private boolean hasPermission(User user, RecordIndex recordIndex) {
		if (user.isAdmin()) {
			return true;
		}
		if (user.isGuest()) {
			return false;
		}
		if (user.getDeptCode() == null) {
			return false;
		}
		String rootRecordXzqhCode = getRootRecordXzqhCodeOf(recordIndex);
		return rootRecordXzqhCode != null
				&& rootRecordXzqhCode.startsWith(user.getDeptCodeActivePart());
	}

	private String getRootRecordXzqhCodeOf(RecordIndex recordIndex) {
		RecordIndex rootRecordIndex = getRootRecordIndexOf(recordIndex);
		DataTable rootTable = dataTableService.findByName(rootRecordIndex
				.getDataTableName());

		String whereClause = buildPkWhereClause(rootTable);
		String sql = String.format("select xzqh from %s %s",
				rootTable.getName(), whereClause);
		try {
			String xzqh = jdbcTemplate.queryForObject(sql,
					rootRecordIndex.getPrimaryKeyValues(), String.class);
			return xzqh;
		} catch (Exception e) {
			return null;
		}
	}

	private RecordIndex getRootRecordIndexOf(RecordIndex recordIndex) {
		DataTable dataTable = dataTableService.findByName(recordIndex
				.getDataTableName());
		if (dataTable.getParent() == null) {
			return recordIndex;
		}
		return getParentRecordIndex(recordIndex);
	}

	private RecordIndex getParentRecordIndex(RecordIndex recordIndex) {
		DataTable dataTable = dataTableService.findByName(recordIndex
				.getDataTableName());
		DataTable parentDataTable = dataTable.getParent();
		Set<String> parentPkNames = parentDataTable.getPrimaryKeyNames();

		String fieldsClause = StringUtils.join(parentPkNames, ", ");
		String whereClause = buildPkWhereClause(dataTable);
		String sql = String.format("select %s from %s %s", fieldsClause,
				dataTable.getName(), whereClause);

		Map<String, Object> parentPkValues = jdbcTemplate.queryForMap(sql,
				recordIndex.getPrimaryKeyValues());
		return new RecordIndex(parentDataTable.getName(), parentPkValues);
	}

	private String buildPkWhereClause(DataTable dataTable) {
		StringBuilder sb = new StringBuilder();
		for (String pkName : dataTable.getPrimaryKeyNames()) {
			if (sb.length() > 0) {
				sb.append(" and ");
			}
			sb.append(pkName).append("=:").append(pkName);
		}
		if (sb.length() > 0) {
			sb.insert(0, "where ");
		}
		return sb.toString();
	}

}
