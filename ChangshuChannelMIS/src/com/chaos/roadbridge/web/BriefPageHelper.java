package com.chaos.roadbridge.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.webframework.helper.PageHelper;
import com.qinglingsoft.webframework.transfer.ResultFragment;

import com.chaos.roadbridge.model.Condition;
import com.chaos.roadbridge.service.PropertyJdbcService;

@Component
@Scope("prototype")
public class BriefPageHelper extends PageHelper<Map<String, Object>> {

	@Resource
	private PropertyJdbcService propertyJdbcService;

	private String dataTableName;
	private List<Condition> conditions;

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setConditions(List<Condition> conditions) {
		this.conditions = conditions;
	}

	@Override
	protected ResultFragment<Map<String, Object>> getResultFragment() {
		return propertyJdbcService.findBriefAndPkFragmentByTableAndConditions(
				dataTableName, conditions, this.getPageStart(),
				this.getPageSize());
	}

}
