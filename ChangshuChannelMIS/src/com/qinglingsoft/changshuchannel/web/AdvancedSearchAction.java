package com.qinglingsoft.changshuchannel.web;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.model.Condition;
import com.qinglingsoft.changshuchannel.service.StringConditionConvertService;

@Component
@Scope("prototype")
public class AdvancedSearchAction implements SessionAware {
	@Resource
	private StringConditionConvertService stringConditionConvertService;
	private Map<String, Object> sessionScope;

	@Override
	public void setSession(Map<String, Object> sessionScope) {
		this.sessionScope = sessionScope;
	}

	private String searchName;
	private String dataTableName;
	private String fieldName;
	private String operator;
	private String conditionValue;
	private Integer conditionIndex;
	private String page;

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public void setConditionValue(String conditionValue) {
		this.conditionValue = conditionValue;
	}

	public void setConditionIndex(Integer conditionIndex) {
		this.conditionIndex = conditionIndex;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String addCondition() {
		Condition condition = stringConditionConvertService.buildCondition(
				dataTableName, fieldName, operator, conditionValue);
		@SuppressWarnings("unchecked")
		List<Condition> conditionList = (List<Condition>) sessionScope
				.get("search." + searchName);
		if (conditionList == null) {
			conditionList = new LinkedList<Condition>();
			sessionScope.put("search." + searchName, conditionList);
		}
		conditionList.add(condition);
		return Action.SUCCESS;
	}

	public String deleteCondition() {
		if (conditionIndex == null) {
			return Action.ERROR;
		}
		@SuppressWarnings("unchecked")
		List<Condition> conditionList = (List<Condition>) sessionScope
				.get("search." + searchName);
		if (conditionList == null) {
			return Action.ERROR;
		}
		conditionList.remove(conditionIndex.intValue());
		return Action.SUCCESS;
	}

	public String acceptConditionList() {
		@SuppressWarnings("unchecked")
		List<Condition> conditionList = (List<Condition>) sessionScope
				.get("search." + searchName);
		sessionScope.put(SessionAttributeNames.CONDITIONS, conditionList);
		return Action.SUCCESS;
	}
}
