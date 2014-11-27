<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="dataTableName" description="表名" required="true" type="java.lang.String" %>
<%@ attribute name="catalog" description="栏目名" required="true" type="java.lang.String" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" />
<c:set target="${dataTableHelper}" property="dataTableName" value="${dataTableName}" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />
<ul>
	<c:forEach items="${dataTable.catalogChildrenMap[catalog]}" var="childTable">
		<c:url var="searchUrl" value="search.action">
			<c:param name="dataTableName" value="${dataTable.name}" />
			<c:param name="childRecordExistsConditions" value="${childTable.name}" />
		</c:url>
		<li class="link"><a href="${searchUrl}" title="${childTable.label}">${childTable.label}</a></li>
	</c:forEach>
</ul>
