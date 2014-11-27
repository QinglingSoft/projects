package com.qinglingsoft.webframework;

import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 自定义函数标签实现
 * 
 * @author guoqiang
 * 
 */
public class Functions {

	/**
	 * 判断集合中是否有指定对象存在
	 * 
	 * @param collection
	 *            集合，可以是数组或java.util.Collection的子类
	 * @param obj
	 *            目标对象
	 * @return 目标对象是否存在于集合中
	 */
	public static boolean contains(Object collection, Object obj) {
		if (collection instanceof Collection) {
			@SuppressWarnings("rawtypes")
			Collection col = (Collection) collection;
			return col.contains(obj);
		}
		if (collection instanceof Object[]) {
			Object[] objects = (Object[]) collection;
			for (Object object : objects) {
				if (object.equals(obj)) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	/**
	 * 为对象生成JSON代码
	 * 
	 * @param obj
	 *            目标对象
	 * @return JSON代码
	 */
	public static String json(Object obj) {
		if (obj instanceof Collection<?> || obj instanceof Object[]) {
			return JSONArray.fromObject(obj).toString(4);
		} else {
			return JSONObject.fromObject(obj).toString(4);
		}
	}
}
