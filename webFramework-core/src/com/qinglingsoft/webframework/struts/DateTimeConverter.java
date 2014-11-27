package com.qinglingsoft.webframework.struts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.struts2.util.StrutsTypeConverter;

public class DateTimeConverter extends StrutsTypeConverter {
	private static final Logger logger = Logger
			.getLogger(DateTimeConverter.class.getName());
	private final DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	@Override
	public Object convertFromString(@SuppressWarnings("rawtypes") Map context,
			String[] values, @SuppressWarnings("rawtypes") Class toClass) {
		if (values == null) {
			return null;
		}
		if (values.length == 0) {
			return null;
		}
		String value = values[0];
		if (value.length() == 0) {
			return null;
		}
		if (toClass.equals(Date.class)) {
			try {
				return dateFormat.parse(value);
			} catch (ParseException e) {
				logger.log(Level.WARNING,
						"Value \"%s\" convert to java.util.Date failed.", value);
			}
		}
		return null;
	}

	@Override
	public String convertToString(@SuppressWarnings("rawtypes") Map context,
			Object o) {
		if (o instanceof Date) {
			return dateFormat.format((Date) o);
		}
		logger.log(Level.WARNING,
				"Object %s is not a instance of java.util.Date.", o);
		return null;
	}

}
