package com.qinglingsoft.webframework.helper;

/**
 * 系统助手，供JSP页面获取难以通过Beans方式获取的资源
 * 
 * @author guoqiang
 * 
 */
public class SystemHelper {
	/**
	 * @return 服务器操作系统的换行符
	 */
	public String getLineSeparator() {
		return System.getProperty("line.separator");
	}
}
