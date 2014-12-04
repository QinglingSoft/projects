package com.qinglingsoft.changshuchannel.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.model.DataField;
import com.qinglingsoft.changshuchannel.model.DataTable;

@Service
public class StringParamConvertService {
	@Resource
	private DataTableService dataTableService;

	public Map<String, Object> convert(String dataTableName,
			Map<String, String> strParamMap)
			throws FieldTypeUnsupportedException {
		Map<String, Object> params = new HashMap<String, Object>();
		DataTable dataTable = dataTableService.findByName(dataTableName);
		for (String paramName : strParamMap.keySet()) {
			String paramStr = strParamMap.get(paramName);
			DataField field = dataTable.getField(paramName);
			Object paramValue = convert(field, paramStr);
			params.put(paramName, paramValue);
		}
		return params;
	}

	public Object convert(DataField field, String str)
			throws FieldTypeUnsupportedException {
		Object obj;
		if (str == null || str.isEmpty()) {
			return null;
		}
		switch (field.getType()) {
		case CODE:
		case STRING:
		case TEXT:
			obj = str;
			break;
		case NUMBER:
			obj = new BigDecimal(str);
			break;
		case DATE:
			obj = java.sql.Date.valueOf(str);
			break;
		case DATETIME:
			obj = java.sql.Timestamp.valueOf(str);
			break;
		default:
			throw new FieldTypeUnsupportedException(field.getDataTable()
					.getName(), field.getName(), field.getType().toString());
		}

		return obj;
	}

	public Object convert(String dataTableName, String fieldName, String str)
			throws FieldTypeUnsupportedException {
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataField field = dataTable.getField(fieldName);
		return convert(field, str);
	}
}
