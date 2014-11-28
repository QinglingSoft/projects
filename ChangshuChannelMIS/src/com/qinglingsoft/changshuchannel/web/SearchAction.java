package com.qinglingsoft.changshuchannel.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.model.ChildRecordExistsCondition;
import com.qinglingsoft.changshuchannel.model.ChildTableCondition;
import com.qinglingsoft.changshuchannel.model.Condition;
import com.qinglingsoft.changshuchannel.model.FieldCondition;
import com.qinglingsoft.changshuchannel.service.StringConditionConvertService;

/**
 * @author 郭强
 * 
 */
@Component
@Scope("prototype")
public class SearchAction implements SessionAware {
	@Resource
	private StringConditionConvertService stringConditionConvertService;

	private Map<String, Object> sessionScope;

	@Override
	public void setSession(Map<String, Object> sessionScope) {
		this.sessionScope = sessionScope;
	}

	private String dataTableName;
	private String childTableName;
	private String catalog;
	private String page;
	private Map<String, String> conditions;
	private List<String> childRecordExistsConditions;
	private Map<String, String> childTableConditions;

	public List<String> getChildRecordExistsConditions() {
		return childRecordExistsConditions;
	}

	public void setChildRecordExistsConditions(
			List<String> childRecordExistsConditions) {
		this.childRecordExistsConditions = childRecordExistsConditions;
	}

	public Map<String, String> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, String> conditions) {
		this.conditions = conditions;
	}

	public Map<String, String> getChildTableConditions() {
		return childTableConditions;
	}

	public void setChildTableConditions(Map<String, String> childTableConditions) {
		this.childTableConditions = childTableConditions;
	}

	public String getDataTableName() {
		return dataTableName;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public String getChildTableName() {
		return childTableName;
	}

	public void setChildTableName(String childTableName) {
		this.childTableName = childTableName;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public String buildConditionList() {
		List<Condition> conditionList = new ArrayList<Condition>();
		if (conditions != null && !conditions.isEmpty()) {
			conditionList.addAll(stringConditionConvertService.convert(
					dataTableName, conditions));
		}
		if (childRecordExistsConditions != null
				&& !childRecordExistsConditions.isEmpty()) {
			conditionList
					.addAll(buildChildRecordExistsConditions(childRecordExistsConditions));
		}
		if (childTableConditions != null && !childTableConditions.isEmpty()
				&& childTableName != null) {
			conditionList.add(buildChildTableCondition());
		}
		sessionScope.put(SessionAttributeNames.CONDITIONS, conditionList);
		return Action.SUCCESS;
	}

	private Condition buildChildTableCondition() {
		ChildTableCondition ctCondition = new ChildTableCondition(
				childTableName);
		List<FieldCondition> fieldConditions = stringConditionConvertService
				.convert(childTableName, childTableConditions);
		ctCondition.addFieldConditions(fieldConditions);
		return ctCondition;
	}

	private List<? extends Condition> buildChildRecordExistsConditions(
			List<String> childTableNames) {
		List<ChildRecordExistsCondition> conditions = new ArrayList<ChildRecordExistsCondition>();
		if (childTableNames == null) {
			return conditions;
		}
		for (String childTableName : childTableNames) {
			conditions.add(new ChildRecordExistsCondition(childTableName));
		}
		return conditions;
	}

}
