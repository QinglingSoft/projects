package com.chaos.roadbridge.web;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.chaos.roadbridge.FieldTypeUnsupportedException;
import com.chaos.roadbridge.IllegalFieldTypeException;
import com.chaos.roadbridge.model.FileTypeValue;
import com.chaos.roadbridge.service.PropertyJdbcService;
import com.chaos.roadbridge.service.StringParamConvertService;
import com.opensymphony.xwork2.Action;

@Component
@Scope("prototype")
public class PreviewFileTypeDataAction implements ServletContextAware {
	private static final String FILE_TYPE_ICON_PATH = "/images/fileType/";
	private static final String FILE_TYPE_ICON_EXT = ".png";
	@Resource
	private PropertyJdbcService propertyJdbcService;
	@Resource
	private StringParamConvertService stringParamConvertService;
	@Resource
	private Properties extMimeTypes;
	private String dataTableName;
	private Map<String, String> primaryKeys = new HashMap<String, String>();
	private String fieldName;
	private InputStream inputStream;
	private String errorMessage;
	private String iconPath;
	private ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public Map<String, String> getPrimaryKeys() {
		return primaryKeys;
	}

	public void setDataTableName(String dataTableName) {
		this.dataTableName = dataTableName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getIconPath() {
		return iconPath;
	}

	public String preview() {
		try {
			Map<String, Object> pkValues = stringParamConvertService.convert(
					dataTableName, primaryKeys);
			String mediaType = propertyJdbcService.findMediaType(dataTableName,
					pkValues, fieldName);
			if (mediaType == null) {
				return "mediaTypeNull";
			}
			String mimeType = extMimeTypes.getProperty(mediaType.toLowerCase());
			if (mimeType == null) {
				return "mimeTypeNull";
			}
			if (mimeType.startsWith("image/")) {
				FileTypeValue fileToDownload = propertyJdbcService
						.findFileTypeValue(dataTableName, pkValues, fieldName);
				if (fileToDownload == null) {
					errorMessage = "没有找到指定字段的值";
					return "imageNull";
				}
				inputStream = new ByteArrayInputStream(
						fileToDownload.getContent());
				return "image";
			}
			iconPath = FILE_TYPE_ICON_PATH + mimeType + FILE_TYPE_ICON_EXT;
			if (servletContext.getResource(iconPath) == null) {
				iconPath = FILE_TYPE_ICON_PATH + "unknown" + FILE_TYPE_ICON_EXT;
			}
			return Action.SUCCESS;
		} catch (FieldTypeUnsupportedException e) {
			errorMessage = String.format("%s.%s(%s)字段类型不受支持",
					e.getDataTableName(), e.getFieldName(), e.getFieldType());
			return Action.ERROR;
		} catch (IllegalFieldTypeException e) {
			errorMessage = String.format("%s.%s(%s)字段不是需要的%s型",
					e.getDataTableName(), e.getFieldName(), e.getFieldType(),
					e.getExpectFieldType());
			return Action.ERROR;
		} catch (MalformedURLException e) {
			errorMessage = String.format("获取文件类型图标错误：" + iconPath);
			return Action.ERROR;
		}
	}

}
