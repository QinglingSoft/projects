package com.qinglingsoft.changshuchannel.web;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.service.DataTableService;

@Component
@Scope("prototype")
public class DataTableHelper {
	@Resource
	private DataTableService dataTableService;
	private String dataTableName;

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public DataTable getDataTable() {
		return dataTableService.findByName(dataTableName);
	}

	public List<DataTable> getAll() {
		return dataTableService.findAll();
	}
}