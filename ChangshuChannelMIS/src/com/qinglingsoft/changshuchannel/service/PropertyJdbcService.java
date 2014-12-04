package com.qinglingsoft.changshuchannel.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.qinglingsoft.changshuchannel.IllegalFieldTypeException;
import com.qinglingsoft.changshuchannel.NoRecordFoundException;
import com.qinglingsoft.changshuchannel.RecordNotUniqueException;
import com.qinglingsoft.changshuchannel.model.ChildRecordExistsCondition;
import com.qinglingsoft.changshuchannel.model.ChildTableCondition;
import com.qinglingsoft.changshuchannel.model.Condition;
import com.qinglingsoft.changshuchannel.model.DataField;
import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.model.FieldCondition;
import com.qinglingsoft.changshuchannel.model.FileTypeValue;
import com.qinglingsoft.webframework.transfer.ResultFragment;

@Repository
public class PropertyJdbcService {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private DataTableService dataTableService;
	@Resource
	private Properties extMimeTypes;
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Resource
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	private String buildWhereClause(Map<String, Object> params) {
		StringBuilder clauseSb = new StringBuilder();
		if (params != null && !params.isEmpty()) {
			for (String paramName : params.keySet()) {
				if (clauseSb.length() > 0) {
					clauseSb.append(" and");
				}
				clauseSb.append(" ").append(paramName).append("=:")
						.append(paramName);
			}
		}
		if (clauseSb.length() > 0) {
			clauseSb.insert(0, " where");
		}
		return clauseSb.toString();
	}

	public ResultFragment<Map<String, Object>> findBriefAndPkFragmentByTable(
			String tableName, Map<String, Object> params, int pageStart,
			int pageSize) {
		String sql = "select count(*) from " + tableName
				+ buildWhereClause(params);
		logger.finer(sql);
		long totalCount = jdbcTemplate.queryForObject(sql, params, Long.class);

		String briefFieldsClause = buildBriefAndPkFieldsClause(tableName);
		sql = "select " + briefFieldsClause + " from " + tableName
				+ buildWhereClause(params);
		logger.finer(sql);
		List<Map<String, Object>> fragment = jdbcTemplate.query(sql, params,
				new FragmentResultSetExtractor(pageStart, pageSize));
		ResultFragment<Map<String, Object>> rf = new ResultFragment<>(
				totalCount, fragment);
		return rf;
	}

	private String buildBriefAndPkFieldsClause(String tableName) {
		DataTable dataTable = dataTableService.findByName(tableName);
		Set<String> briefFieldNames = new HashSet<String>(
				dataTable.getBriefFieldNames());
		briefFieldNames.addAll(dataTable.getPrimaryKeyNames());
		return StringUtils.join(briefFieldNames, ", ");
	}

	public Map<String, Object> findBrief(String tableName,
			Map<String, Object> params) {
		String briefFieldsClause = buildBriefAndPkFieldsClause(tableName);
		String sql = "select " + briefFieldsClause + " from " + tableName
				+ buildWhereClause(params);
		logger.finer(sql);
		return jdbcTemplate.queryForMap(sql, params);
	}

	public Map<String, Object> findSingleMap(String tableName,
			Map<String, Object> params) {
		String sql = "select * from " + tableName + buildWhereClause(params);
		logger.finer(sql);
		Map<String, Object> result = jdbcTemplate.queryForMap(sql, params);
		return result;
	}

	public List<Map<String, Object>> findChildMap(String childDataTableName,
			Map<String, Object> parentPrimaryKeyValues) {
		String sql = "select * from " + childDataTableName
				+ buildWhereClause(parentPrimaryKeyValues);
		logger.finer(sql);
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,
				parentPrimaryKeyValues);
		return result;
	}

	public boolean hasRecord(String dataTableName,
			Map<String, Object> parentPrimaryKeyValues) {
		String sql = "select count(*) from " + dataTableName
				+ buildWhereClause(parentPrimaryKeyValues);
		logger.finer(sql);
		long count = jdbcTemplate.queryForObject(sql, parentPrimaryKeyValues,
				Long.class);
		return count > 0;

	}

	public FileTypeValue findFileTypeValue(String dataTableName,
			Map<String, Object> pkParams, String fieldName)
			throws IllegalFieldTypeException {
		// 错误处理
		DataTable dataTable = dataTableService.findByName(dataTableName);
		if (dataTable == null) {
			logger.warning(String.format("表%s没有登记", dataTableName));
			return null;
		}
		DataField field = dataTable.getFieldsMap().get(fieldName);
		if (field == null) {
			logger.warning(String.format("字段%s.%s没有登记", dataTableName,
					fieldName));
			return null;
		}
		if (field.getType() != DataField.Type.FILE) {
			logger.warning(String.format("试图以文件形式获取%s.%s(%s)", dataTableName,
					fieldName, field.getType()));
			throw new IllegalFieldTypeException(dataTableName, fieldName, field
					.getType().toString(), DataField.Type.FILE.toString());
		}

		// 获取字段内容及媒体类型
		String sql = String.format("select %s,%s from %s", fieldName,
				field.getMediaTypeField(), dataTableName)
				+ buildWhereClause(pkParams);
		logger.finer(sql);
		Map<String, ?> results = jdbcTemplate.queryForMap(sql, pkParams);
		byte[] fileContent = (byte[]) results.get(fieldName);
		if (fileContent == null) {
			return null;
		}

		// 构建文件名
		StringBuilder fileName = new StringBuilder(dataTable.getLabel());
		for (Object pkValue : pkParams.values()) {
			fileName.append("_").append(pkValue);
		}

		// mediaType即为扩展名
		String extName = (String) results.get(field.getMediaTypeField());
		fileName.append(".").append(extName);
		return new FileTypeValue(fileName.toString(),
				extMimeTypes.getProperty(extName), fileContent);
	}

	/**
	 * @param dataTableName
	 * @param params
	 * @return primary keys
	 */
	public Map<String, Object> insert(String dataTableName,
			Map<String, Object> params) {
		// 组织SQL与执行
		StringBuilder sqlSb = new StringBuilder("insert into ");
		List<String> paramNameList = new ArrayList<String>(params.keySet());
		sqlSb.append(dataTableName).append("(")
				.append(StringUtils.join(paramNameList, ',')).append(")");
		sqlSb.append(" values(:")
				.append(StringUtils.join(paramNameList, ", :")).append(")");
		String sql = sqlSb.toString();
		logger.finer(sql);
		KeyHolder gkh = new GeneratedKeyHolder();
		if (jdbcTemplate.update(sql, new MapSqlParameterSource(params), gkh) != 1) {
			return null;
		}

		// 返回数据库生成的key值
		Number generatedKey = gkh.getKey();
		DataTable dataTable = dataTableService.findByName(dataTableName);
		Map<String, Object> primaryKeyValues = new HashMap<String, Object>();
		for (DataField pk : dataTable.getPrimaryKeys()) {
			primaryKeyValues.put(pk.getName(), params.get(pk.getName()));
			if (pk.isGeneratedByDatabase()) {
				primaryKeyValues.put(pk.getName(), generatedKey);
			}
		}
		return primaryKeyValues;
	}

	public int delete(String dataTableName, Map<String, Object> params) {
		if (params == null || params.isEmpty()) {
			return 0;
		}
		// 组织SQL与执行
		StringBuilder sqlSb = new StringBuilder("delete ");
		sqlSb.append(dataTableName).append(buildWhereClause(params));
		String sql = sqlSb.toString();
		logger.finer(sql);
		return jdbcTemplate.update(sql, params);
	}

	public Object updateSingleField(String dataTableName, String fieldName,
			Map<String, Object> primaryKeyValues, Object newValue)
			throws NoRecordFoundException, RecordNotUniqueException {
		String sql = String.format("select count(*) from %s", dataTableName);
		String whereClause = buildWhereClause(primaryKeyValues);
		sql += whereClause;
		logger.finer(sql);
		int count = jdbcTemplate.queryForObject(sql, primaryKeyValues,
				Integer.class);
		if (count == 0) {
			throw new NoRecordFoundException();
		}
		if (count > 1) {
			throw new RecordNotUniqueException();
		}
		if (newValue != null) {
			sql = String.format("update %s set %s=:new_%s", dataTableName,
					fieldName, fieldName);
		} else {
			sql = String.format("update %s set %s=null", dataTableName,
					fieldName, fieldName);
		}
		sql += whereClause;
		Map<String, Object> params = new HashMap<String, Object>(
				primaryKeyValues);
		params.put("new_" + fieldName, newValue);
		logger.finer(sql);
		jdbcTemplate.update(sql, params);

		sql = String.format("select %s from %s", fieldName, dataTableName);
		sql += whereClause;
		logger.finer(sql);
		return jdbcTemplate.queryForObject(sql, primaryKeyValues, Object.class);

	}

	public void updateFileTypeField(String dataTableName, String fieldName,
			Map<String, Object> primaryKeyValues, File file, String contentType)
			throws NoRecordFoundException, RecordNotUniqueException,
			IOException {
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataField fileField = dataTable.getField(fieldName);
		DataField mediaTypeField = dataTable.getField(fileField
				.getMediaTypeField());

		String sql = String.format("select count(*) from %s", dataTableName);
		String whereClause = buildWhereClause(primaryKeyValues);
		sql += whereClause;
		logger.finer(sql);
		int count = jdbcTemplate.queryForObject(sql, primaryKeyValues,
				Integer.class);
		if (count == 0) {
			throw new NoRecordFoundException();
		}
		if (count > 1) {
			throw new RecordNotUniqueException();
		}

		if (file != null) {
			sql = String.format("update %s set %s=:new_%s, %s=:new_%s",
					dataTableName, fieldName, fieldName,
					mediaTypeField.getName(), mediaTypeField.getName());
		} else {
			sql = String.format("update %s set %s=null, %s=null",
					dataTableName, fieldName, mediaTypeField.getName());
		}
		sql += whereClause;
		Map<String, Object> params = new HashMap<String, Object>(
				primaryKeyValues);
		if (file != null) {
			params.put("new_" + mediaTypeField.getName(), contentType);
			params.put("new_" + fieldName, FileUtils.readFileToByteArray(file));
		}
		logger.finer(sql);
		jdbcTemplate.update(sql, params);
	}

	public String findMediaType(String dataTableName,
			Map<String, Object> pkValues, String fieldName) {
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataField field = dataTable.getField(fieldName);
		String sql = String.format("select %s from %s",
				field.getMediaTypeField(), dataTableName);
		sql += buildWhereClause(pkValues);
		logger.finer(sql);
		return jdbcTemplate.queryForObject(sql, pkValues, String.class);
	}

	public List<Map<String, Object>> findChildBriefAndPk(
			String childDataTableName, Map<String, Object> parentPrimaryValues) {
		DataTable dataTable = dataTableService.findByName(childDataTableName);
		Set<String> selectFieldNames = new HashSet<String>(
				dataTable.getPrimaryKeyNames());
		if (selectFieldNames.isEmpty()) {
			logger.warning(String.format("从子表%s中获取摘要和主键列表时没有指定字段",
					childDataTableName));
			return null;
		}
		selectFieldNames.addAll(dataTable.getBriefFieldNames());
		String sql = "select " + StringUtils.join(selectFieldNames, ", ")
				+ " from " + childDataTableName
				+ buildWhereClause(parentPrimaryValues);
		logger.finer(sql);
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql,
				parentPrimaryValues);
		return result;
	}

	public ResultFragment<Map<String, Object>> findBriefAndPkFragmentByTableAndConditions(
			String tableName, Collection<Condition> conditions, int pageStart,
			int pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		String whereClause = buildWhereClause(conditions, params);
		String sql = "select count(*) from " + tableName + whereClause;
		logger.finer(sql);
		long totalCount = jdbcTemplate.queryForObject(sql, params, Long.class);

		String briefFieldsClause = buildBriefAndPkFieldsClause(tableName);
		sql = "select " + briefFieldsClause + " from " + tableName
				+ whereClause;
		logger.finer(sql);
		List<Map<String, Object>> fragment = jdbcTemplate.query(sql, params,
				new FragmentResultSetExtractor(pageStart, pageSize));

		ResultFragment<Map<String, Object>> rf = new ResultFragment<Map<String, Object>>(
				totalCount, fragment);
		return rf;
	}

	private String buildWhereClause(Collection<Condition> conditions,
			Map<String, Object> paramMap) {
		if (conditions == null || conditions.isEmpty()) {
			return "";
		}
		StringBuilder clauseSb = new StringBuilder();
		int paramMutex = 0;
		for (Condition condition : conditions) {
			if (clauseSb.length() > 0) {
				clauseSb.append(" and");
			}
			if (condition instanceof FieldCondition) {
				FieldCondition fieldCondition = (FieldCondition) condition;
				String paramName = "c_" + fieldCondition.getFieldName()
						+ (++paramMutex);
				clauseSb.append(buildWhereClauseExpression(fieldCondition,
						paramName, paramMap));
			} else if (condition instanceof ChildRecordExistsCondition) {
				ChildRecordExistsCondition creCondition = (ChildRecordExistsCondition) condition;
				clauseSb.append(buildConditionClause(creCondition));
			} else if (condition instanceof ChildTableCondition) {
				ChildTableCondition ctCondition = (ChildTableCondition) condition;
				clauseSb.append(buildConditionClause(ctCondition, paramMap));
			}
		}
		if (clauseSb.length() > 0) {
			clauseSb.insert(0, " where");
		}
		return clauseSb.toString();
	}

	private String buildConditionClause(ChildTableCondition ctCondition,
			Map<String, Object> paramMap) {
		String clause = String.format(" exists(select * from %s %s)",
				ctCondition.getChildTableName(),
				buildWhereClause(ctCondition, paramMap));
		return clause;
	}

	private String buildWhereClause(ChildTableCondition ctCondition,
			Map<String, Object> paramMap) {
		StringBuilder clauseSb = new StringBuilder();
		DataTable childTable = dataTableService.findByName(ctCondition
				.getChildTableName());
		DataTable parentTable = childTable.getParent();
		for (DataField parentPk : parentTable.getPrimaryKeys()) {
			if (clauseSb.length() > 0) {
				clauseSb.append(" and");
			}
			clauseSb.append(String.format(" %s.%s=%s.%s", childTable.getName(),
					parentPk.getName(), parentTable.getName(),
					parentPk.getName()));
		}
		int paramMutex = 0;
		for (FieldCondition fieldCondition : ctCondition.getFieldConditions()) {
			if (clauseSb.length() > 0) {
				clauseSb.append(" and");
			}
			String paramName = "c_" + ctCondition.getChildTableName() + "_"
					+ fieldCondition.getFieldName() + (++paramMutex);
			clauseSb.append(buildWhereClauseExpression(fieldCondition,
					paramName, paramMap));

		}
		if (clauseSb.length() > 0) {
			clauseSb.insert(0, "where");
		}
		return clauseSb.toString();
	}

	private String buildWhereClauseExpression(FieldCondition fieldCondition,
			String paramName, Map<String, Object> paramMap) {
		StringBuilder expressionSb = new StringBuilder();
		switch (fieldCondition.getOperator()) {
		case EQ:
			expressionSb.append(String.format(" %s=:%s",
					fieldCondition.getFieldName(), paramName));
			paramMap.put(paramName, fieldCondition.getValue());
			break;
		case GE:
			expressionSb.append(String.format(" %s>=:%s",
					fieldCondition.getFieldName(), paramName));
			paramMap.put(paramName, fieldCondition.getValue());
			break;
		case IN:
			expressionSb.append(String.format(" %s in(:%s)",
					fieldCondition.getFieldName(), paramName));
			paramMap.put(paramName, fieldCondition.getValue());
			break;
		case LE:
			expressionSb.append(String.format(" %s<=:%s",
					fieldCondition.getFieldName(), paramName));
			paramMap.put(paramName, fieldCondition.getValue());
			break;
		case LIKE:
			expressionSb.append(String.format(" %s like :%s",
					fieldCondition.getFieldName(), paramName));
			paramMap.put(paramName, "%" + fieldCondition.getValue() + "%");
			break;
		}
		return expressionSb.toString();
	}

	private Object buildConditionClause(ChildRecordExistsCondition creCondition) {
		String clause = String.format(" exists(select * from %s %s)",
				creCondition.getChildTableName(),
				buildWhereClause(creCondition));
		return clause;
	}

	private String buildWhereClause(ChildRecordExistsCondition creCondition) {
		StringBuilder clauseSb = new StringBuilder();
		DataTable childTable = dataTableService.findByName(creCondition
				.getChildTableName());
		DataTable parentTable = childTable.getParent();
		for (DataField parentPk : parentTable.getPrimaryKeys()) {
			if (clauseSb.length() > 0) {
				clauseSb.append(" and");
			}
			clauseSb.append(String.format(" %s.%s=%s.%s", childTable.getName(),
					parentPk.getName(), parentTable.getName(),
					parentPk.getName()));
		}
		if (clauseSb.length() > 0) {
			clauseSb.insert(0, "where");
		}
		return clauseSb.toString();
	}
}
