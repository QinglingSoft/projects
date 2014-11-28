package com.qinglingsoft.changshuchannel.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import com.qinglingsoft.changshuchannel.model.StatisticResult;
import com.qinglingsoft.changshuchannel.model.User;
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
	private String procedureName;
	private String label;
	private InputStream inputStream;

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

}
