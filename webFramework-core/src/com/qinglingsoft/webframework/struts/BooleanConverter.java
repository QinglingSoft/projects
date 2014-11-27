package com.qinglingsoft.webframework.struts;

import java.util.Map;

import org.apache.struts2.util.StrutsTypeConverter;

/**
 * 布尔型值的Struts类型转换器，以避免缺省转换器将无指定参数的情况视作false的问题。
 * 
 * @author guoqiang
 * 
 */
public class BooleanConverter extends StrutsTypeConverter {

	@SuppressWarnings("rawtypes")
	@Override
	public Object convertFromString(Map context, String[] values, Class toClass) {
		if (values == null) {
			return null;
		}
		if (values.length == 0) {
			return null;
		}
		String value = values[0];
		if (value == null) {
			return null;
		}
		if (value.length() == 0) {
			return null;
		}
		if (toClass.equals(Boolean.class)) {
			return new Boolean(value);
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String convertToString(Map context, Object o) {
		return o.toString();
	}

}
