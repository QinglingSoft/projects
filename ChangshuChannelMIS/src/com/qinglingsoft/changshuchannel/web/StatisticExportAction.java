package com.qinglingsoft.changshuchannel.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.qinglingsoft.changshuchannel.FieldTypeUnsupportedException;
import com.qinglingsoft.changshuchannel.IncorrectFieldTypeRegistryException;
import com.qinglingsoft.changshuchannel.model.Condition;
import com.qinglingsoft.changshuchannel.model.DataField;
import com.qinglingsoft.changshuchannel.model.DataTable;
import com.qinglingsoft.changshuchannel.model.StatisticResult;
import com.qinglingsoft.changshuchannel.model.User;
import com.qinglingsoft.changshuchannel.service.DataFormatService;
import com.qinglingsoft.changshuchannel.service.DataTableService;
import com.qinglingsoft.changshuchannel.service.PropertyJdbcService;
import com.qinglingsoft.changshuchannel.service.StatisticService;

@Component
@Scope("prototype")
public class StatisticExportAction implements SessionAware {
	private Logger logger = Logger.getLogger(this.getClass().getName());
	private Map<String, Object> sessionScope;

	@Override
	public void setSession(Map<String, Object> sessionScope) {
		this.sessionScope = sessionScope;
	}

	@Resource
	private StatisticService statisticService;
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private DataTableService dataTableService;
	@Resource
	private DataFormatService dataFormatService;
	
	private String dataTableName;
	private String procedureName;
	private String label;
	private InputStream inputStream;

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}
	
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getFileName() {
		try {
			return URLEncoder.encode(label, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return label;
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String exportExcel() {
		User loginUser = (User) sessionScope
				.get(SessionAttributeNames.LOGIN_USER);
		StatisticResult result = statisticService.statistic(procedureName,
				loginUser.getDeptCodeActivePart());

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(label);

		// 表头
		Row headerRow = sheet.createRow(0);
		String[] columnNames = result.getColumnNames();
		// 第一列不显示
		for (int i = 1; i < columnNames.length; i++) {
			headerRow.createCell(i - 1).setCellValue(columnNames[i]);
		}

		// 表内容
		int rownum = 1;
		for (Map<String, Object> dataRow : result.getDataRowList()) {
			Row row = sheet.createRow(rownum++);
			// 第一列不显示
			for (int i = 1; i < columnNames.length; i++) {
				String columnName = columnNames[i];
				Object data = dataRow.get(columnName);
				if (data == null) {
					continue;
				}
				Cell cell = row.createCell(i - 1);
				if (data instanceof Number) {
					cell.setCellValue(((Number) data).doubleValue());
					continue;
				}
				cell.setCellValue(data.toString());
			}
		}

		try {
			File tempFile = File.createTempFile("roadBridge.export", ".tmp");
			tempFile.deleteOnExit();
			OutputStream out = new FileOutputStream(tempFile);
			wb.write(out);
			out.close();
			inputStream = new FileInputStream(tempFile);
		} catch (IOException e) {
			logger.log(Level.WARNING, "生成临时文件错误", e);
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}
	
	
	public String exportDatas() {
		List<Condition> conditionList = new ArrayList<Condition>();
		conditionList = (List<Condition>) sessionScope.get(SessionAttributeNames.CONDITIONS);
		StatisticResult result = propertyJdbcService.findByConditions(dataTableName, conditionList);

		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet(label);

		// 表头
		Row headerRow = sheet.createRow(0);
		String[] columnNames = result.getColumnNames();
		DataTable dataTable = dataTableService.findByName(dataTableName);
		if (dataTable == null) {
			logger.warning(String.format("表%s没有登记", dataTableName));
			return null;
		}
		// 
		
		for (int i = 0; i < columnNames.length; i++) {
			DataField field = dataTable.getFieldsMap().get(columnNames[i]);
			if (field == null) {
				logger.warning(String.format("字段%s.%s没有登记", dataTableName,columnNames[i]));
				return null;
			}
			headerRow.createCell(i).setCellValue(field.getLabel());
		}

		// 表内容
		int rownum = 1;
		for (Map<String, Object> dataRow : result.getDataRowList()) {
			Row row = sheet.createRow(rownum++);
			//
			for (int i = 0; i < columnNames.length; i++) {
				String columnName = columnNames[i];
				String value = "";
				Object data = dataRow.get(columnName);
				if (data == null) {
					continue;
				}
				try {
					value = dataFormatService.format(dataTableName, columnName, data);
				} catch (IncorrectFieldTypeRegistryException e) {
					e.printStackTrace();
				} catch (FieldTypeUnsupportedException e) {
					e.printStackTrace();
				}
				DataField field = dataTable.getFieldsMap().get(columnName);
				if(field.getType().equals(DataField.Type.CODE)){
					value = field.getCodeTable().getCodes().get(data).getMeaning();
				}
				Cell cell = row.createCell(i);
				cell.setCellValue(value);
			}
		}

		try {
			File tempFile = File.createTempFile("changshu.export", ".tmp");
			tempFile.deleteOnExit();
			OutputStream out = new FileOutputStream(tempFile);
			wb.write(out);
			out.close();
			inputStream = new FileInputStream(tempFile);
		} catch (IOException e) {
			logger.log(Level.WARNING, "生成临时文件错误", e);
			return Action.ERROR;
		}
		return Action.SUCCESS;
	}

}
