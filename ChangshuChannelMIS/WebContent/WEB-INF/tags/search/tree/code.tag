<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dataTableName" description="表名" required="true" type="java.lang.String" %>
<%@ attribute name="fieldName" description="字段名" required="true" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper"/>
<c:set target="${dataTableHelper}" property="dataTableName" value="${dataTableName}" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />
<c:set var="field" value="${dataTable.fieldsMap[fieldName]}" />
<ul>
	<c:forEach items="${field.codeTable.codes}" var="codeEntry">
		<c:set var="code" value="${codeEntry.value}" />
		<c:url var="searchUrl" value="search.action">
			<c:param name="conditions.${field.name}_EQ" value="${code.value}" />
			<c:param name="dataTableName" value="${dataTable.name}" />
		</c:url>
		<li class="link"><a href="${searchUrl}">${code.meaning}</a></li>
	</c:forEach>
</ul>
