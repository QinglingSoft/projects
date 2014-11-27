package com.qinglingsoft.webframework.struts;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateTimeConverter extends StrutsTypeConverter {
	private static final Log log = LogFactory.getLog(DateTimeConverter.class);
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
				log.warn("value \"" + value
						+ "\" convert to java.util.Date failed.");
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
		log.warn("Object " + o + " is not a instance of java.util.Date.");
		return null;
	}

}
