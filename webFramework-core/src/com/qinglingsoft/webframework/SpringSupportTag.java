package com.qinglingsoft.webframework;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Spring支持标签，供JSP页面以类似bean的形式引用Spring bean。
 * 
 * @author guoqiang
 * 
 */
public class SpringSupportTag extends TagSupport {

	private static final long serialVersionUID = 4L;

	/**
	 * bean实体在页面中的引用名，类似<jsp:useBean>的id属性
	 */
	private String id;
	/**
	 * Spring bean名称，Spring框架查找bean的依据之一
	 */
	private String beanName;
	/**
	 * 类型，类似<jsp:useBean>的type属性，也是Spring框架查找bean的依据之一
	 */
	private String type;
	/**
	 * bean的作用域，类似<jsp:useBean>的scope属性
	 */
	private String scope;

	public void setId(String id) {
		this.id = id;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
	 */
	@Override
	public int doStartTag() throws JspException {
		if (scope == null) {
			scope = "page";
		}
		int scopeCode = getScopeCode();
		Class<?> clazz = null;
		if (type != null) {
			try {
				clazz = Class.forName(type);
			} catch (ClassNotFoundException e) {
				throw new JspException(e);
			}
		}
		Object obj = pageContext.getAttribute(id, scopeCode);
		if (obj == null) {
			// 如果实例不存在，则创建并放入scope，并调用标签体
			ApplicationContext springContext = getSpringContext();
			if (beanName == null) {
				if (clazz == null) {
					throw new JspException(
							"The wanted bean does not exist and the beanName isn`t assigned.");
				}
				String[] beanNames = springContext.getBeanNamesForType(clazz);
				if (beanNames.length == 1) {
					beanName = beanNames[0];
				} else {
					throw new JspException(
							"The count of beans defined in spring context with type "
									+ clazz + " does not unique.");
				}
			}
			obj = springContext.getBean(beanName, clazz);
			pageContext.setAttribute(id, obj, scopeCode);
			return EVAL_BODY_INCLUDE;
		} else {
			// 如果实例已存在，则检查类型
			if (clazz != null && !clazz.isInstance(obj)) {
				throw new JspException(obj + " is not a instance of class "
						+ clazz);
			}
			return SKIP_BODY;
		}
	}

	private ApplicationContext getSpringContext() {
		ServletContext servletContext = pageContext.getServletContext();
		return WebApplicationContextUtils
				.getWebApplicationContext(servletContext);
	}

	private int getScopeCode() {
		if ("page".equalsIgnoreCase(scope)) {
			return PageContext.PAGE_SCOPE;
		}
		if ("request".equalsIgnoreCase(scope)) {
			return PageContext.REQUEST_SCOPE;
		}
		if ("session".equalsIgnoreCase(scope)) {
			return PageContext.SESSION_SCOPE;
		}
		if ("application".equalsIgnoreCase(scope)) {
			return PageContext.APPLICATION_SCOPE;
		}
		return PageContext.PAGE_SCOPE;
	}
}
