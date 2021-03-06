package com.qinglingsoft.changshuchannel.service;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.IncorrectFieldTypeRegistryException;
import com.qinglingsoft.changshuchannel.model.DataField;
import com.qinglingsoft.changshuchannel.model.DataTable;

/**
 * 数据格式化服务，用于将表数据格式化为字符串
 * 
 * @author 郭强
 */
@Service
public class DataFormatService {
	@Resource
	private DataTableService dataTableService;

	public String format(String dataTableName, String fieldName,
			Object dataValue) throws IncorrectFieldTypeRegistryException,
			FieldTypeUnsupportedException {
		DataTable dataTable = dataTableService.findByName(dataTableName);
		DataField field = dataTable.getField(fieldName);

		return format(field, dataValue);
	}

	private static final DateFormat DateFormatter = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final DateFormat DateTimeFormatter = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	private String format(DataField field, Object dataValue)
			throws IncorrectFieldTypeRegistryException,
			FieldTypeUnsupportedException {
		if (dataValue == null) {
			return null;
		}
		if (!field.getType().getJavaType().isInstance(dataValue)) {
			throw new IncorrectFieldTypeRegistryException(field.getDataTable()
					.getName(), field.getName(), field.getType(),
					dataValue.getClass());
		}
		switch (field.getType()) {
		case CODE:
			return ((String) dataValue).trim();
		case DATE:
			return DateFormatter.format((Date) dataValue);
		case DATETIME:
			return DateTimeFormatter.format((Date) dataValue);
		case FILE:
			throw new FieldTypeUnsupportedException(field.getDataTable()
					.getName(), field.getName(), field.getType().toString());
		case NUMBER:
			NumberFormat numberFormat = NumberFormat.getNumberInstance();
			numberFormat.setGroupingUsed(false);
			Integer fd = field.getFractionDigits();
			numberFormat.setMaximumFractionDigits(fd != null ? fd : 0);
			return numberFormat.format(dataValue);
		case STRING:
		case DATEMONTH:
		case DATEYEAR:
		case DATEQUARTER:
		case TEXT:
			return (String) dataValue;
		}
		return null;

	}

}
