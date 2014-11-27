/*
 * Copyright 1999-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.taglibs.standard.functions;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.jsp.JspTagException;

import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.util.EscapeXML;

/**
 * Apache的JSTL函数标签实现，用String自带算法实现对应函数，用try catch机制处理空值及索引超出范围的情况，采用JDK 1.5的新特性。
 * 
 * @author guoqiang
 * 
 */
public class Functions {

	// *********************************************************************
	// String capitalization

	/**
	 * Converts all of the characters of the input string to upper case.
	 */
	public static String toUpperCase(String input) {
		return input.toUpperCase();
	}

	/**
	 * Converts all of the characters of the input string to lower case.
	 */
	public static String toLowerCase(String input) {
		return input.toLowerCase();
	}

	// *********************************************************************
	// Substring processing

	public static int indexOf(String input, String substring) {
		try {
			return input.indexOf(substring);
		} catch (NullPointerException e) {
			return -1;
		}
	}

	public static boolean contains(String input, String substring) {
		try {
			return input.contains(substring);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static boolean containsIgnoreCase(String input, String substring) {
		try {
			return input.toUpperCase().contains(substring.toUpperCase());
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static boolean startsWith(String input, String substring) {
		try {
			return input.startsWith(substring);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static boolean endsWith(String input, String substring) {
		try {
			return input.endsWith(substring);
		} catch (NullPointerException e) {
			return false;
		}
	}

	public static String substring(String input, int beginIndex, int endIndex) {
		try {
			return input.substring(beginIndex, endIndex);
		} catch (IndexOutOfBoundsException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String substringAfter(String input, String substring) {
		try {
			int index = input.indexOf(substring);
			return input.substring(index + substring.length());
		} catch (IndexOutOfBoundsException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String substringBefore(String input, String substring) {
		try {
			int index = input.indexOf(substring);
			return input.substring(0, index);
		} catch (IndexOutOfBoundsException e) {
			return "";
		} catch (NullPointerException e) {
			return "";
		}
	}

	// *********************************************************************
	// Character replacement

	public static String escapeXml(String input) {
		try {
			return EscapeXML.escape(input);
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String trim(String input) {
		try {
			return input.trim();
		} catch (NullPointerException e) {
			return "";
		}
	}

	public static String replace(String input, String substringBefore,
			String substringAfter) {
		if (input == null)
			return null;
		if (substringBefore == null)
			return input;
		if (substringAfter == null)
			substringAfter = "";
		return input.replace(substringBefore, substringAfter);
	}

	public static String[] split(String input, String delimiters) {
		if (input == null)
			return null;
		return input.split(delimiters);
	}

	// *********************************************************************
	// Collections processing

	/**
	 * 采用JDK 1.5泛型
	 * 
	 * @param obj
	 * @return
	 * @throws JspTagException
	 */
	public static int length(Object obj) throws JspTagException {
		if (obj == null)
			return 0;

		if (obj instanceof String)
			return ((String) obj).length();
		if (obj instanceof Collection)
			return ((Collection<?>) obj).size();
		if (obj instanceof Map)
			return ((Map<?, ?>) obj).size();

		int count = 0;
		if (obj instanceof Iterator) {
			Iterator<?> iter = (Iterator<?>) obj;
			count = 0;
			while (iter.hasNext()) {
				count++;
				iter.next();
			}
			return count;
		}
		if (obj instanceof Enumeration) {
			Enumeration<?> enum_ = (Enumeration<?>) obj;
			count = 0;
			while (enum_.hasMoreElements()) {
				count++;
				enum_.nextElement();
			}
			return count;
		}
		try {
			count = Array.getLength(obj);
			return count;
		} catch (IllegalArgumentException ex) {
		}
		throw new JspTagException(Resources.getMessage("PARAM_BAD_VALUE"));
	}

	/**
	 * 采用JDK 1.5 的for each语法实现循环
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(String[] array, String separator) {
		if (array == null)
			return null;
		if (separator == null)
			separator = "";

		StringBuffer buf = new StringBuffer();
		for (String str : array) {
			if (buf.length() > 0)
				buf.append(separator);
			buf.append(str);
		}
		return buf.toString();
	}
}
