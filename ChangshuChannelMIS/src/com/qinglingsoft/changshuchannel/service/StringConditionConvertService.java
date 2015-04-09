package com.qinglingsoft.changshuchannel.service;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.qinglingsoft.changshuchannel.model.CodeCondition;
import com.qinglingsoft.changshuchannel.model.CodeTable;
import com.qinglingsoft.changshuchannel.model.Condition;
import com.qinglingsoft.changshuchannel.model.DataField;
import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.model.DateCondition;
import com.qinglingsoft.changshuchannel.model.FieldCondition;
import com.qinglingsoft.changshuchannel.model.NumberCondition;
import com.qinglingsoft.changshuchannel.model.StringCondition;
import com.qinglingsoft.changshuchannel.model.DataField.Operator;

@Service
public class StringConditionConvertService {
	private static final String CONDITION_KEY_SEPARATOR = "_";
	private Logger logger = Logger.getLogger(this.getClass().getName());
	@Resource
	private DataTableService dataTableService;

	/**
	 * @param dataTableName
	 * @param conditionStrMap
	 *            Map<fieldName$operatorStr, conditionValueStr>
	 * @return List of condition
	 */
	public List<FieldCondition> convert(String dataTableName,
			Map<String, String> conditionStrMap) {
		List<FieldCondition> conditionList = new ArrayList<FieldCondition>();
		if (conditionStrMap == null) {
			return conditionList;
		}
		DataTable dataTable = dataTableService.findByName(dataTableName);
		for (String conditionKeyStr : conditionStrMap.keySet()) {

			// 忽略没有内容的条件
			String conditionValueStr = conditionStrMap.get(conditionKeyStr);
			if (StringUtils.isEmpty(conditionValueStr)) {
				continue;
			}

			// 将条件键解析为字段和操作符
			String[] conditionKeys;
			try {
				conditionKeys = parseConditionKey(conditionKeyStr);
			} catch (IllegleConditionKeyExpression e) {
				logger.warning("查询键表达式错误：" + e.getLocalizedMessage());
				continue;
			}
			String fieldName = conditionKeys[0];
			String operatorStr = conditionKeys[1];
			DataField field = dataTable.getField(fieldName);
			if (field == null) {
				logger.warning(String.format("查询字段%s不存在，忽略条件", fieldName));
				continue;
			}
			DataField.Operator operator;
			try {
				operator = DataField.Operator.valueOf(operatorStr);
			} catch (IllegalArgumentException e) {
				logger.warning("非法查询操作符：" + operatorStr);
				continue;
			}

			// 构建条件对象，加入条件集
			conditionList
					.add(buildCondition(field, operator, conditionValueStr));
		}
		return conditionList;
	}

	private String[] parseConditionKey(String conditionKeyExpression)
			throws IllegleConditionKeyExpression {
		String[] conditionKeys = new String[2];
		int separatorPosition = conditionKeyExpression
				.lastIndexOf(CONDITION_KEY_SEPARATOR);
		if (separatorPosition == -1) {
			throw new IllegleConditionKeyExpression(conditionKeyExpression);
		}
		conditionKeys[0] = conditionKeyExpression.substring(0,
				separatorPosition);
		conditionKeys[1] = conditionKeyExpression
				.substring(separatorPosition + 1);
		return conditionKeys;
	}

	public Condition buildCondition(String dataTableName, String fieldName,
			String operatorStr, String conditionValueStr) {
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataField field = dataTable.getField(fieldName);
		return buildCondition(field, Operator.valueOf(operatorStr),
				conditionValueStr);
	}

	private FieldCondition buildCondition(DataField field, Operator operator,
			String conditionValueStr) {
		switch (field.getType()) {
		case CODE:
			CodeTable codeTable = field.getCodeTable();
			String[] value = conditionValueStr.split(", ");
			List<String> texts = new ArrayList<String>();
			for (String valueItem : value) {
				try {
					String text = valueItem
							+ "."
							+ codeTable.getCodes().get(valueItem.trim())
									.getMeaning();
					texts.add(text);
				} catch (Exception e) {
					logger.warning(String.format("字段%s代码%s没有注册",
							field.getName(), valueItem));
				}
			}
			return new CodeCondition(field.getName(), field.getLabel(),
					operator, Arrays.asList(value), texts);
		case DATE:
		case DATETIME:
			return new DateCondition(field.getName(), field.getLabel(),
					operator, Date.valueOf(conditionValueStr));
		case NUMBER:
			return new NumberCondition(field.getName(), field.getLabel(),
					operator, new BigDecimal(conditionValueStr));
		case STRING:
		case DATEMONTH:
		case DATEYEAR:
		case DATEQUARTER:
		case TEXT:
			return new StringCondition(field.getName(), field.getLabel(),
					operator, conditionValueStr);
		default:
			logger.warning(String.format("%s.%s字段为%s型，不可作为条件字段", field
					.getDataTable().getName(), field.getName(), field.getType()));
		}
		return null;
	}
}
