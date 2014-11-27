<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

<spring:useBean id="childTableDataHelper" beanName="childTableDataHelper" />
<c:set var="parentTable" value="${dataTable.parent}"/>
<c:forEach items="${dataTable.primaryKeys}" var="parentPk">
	<c:set var="paramName" value="primaryKeys.${parentPk.name}" />
	<c:set target="${childTableDataHelper.parentPrimaryKeys}" property="${parentPk.name}" value="${param[paramName]}" />
</c:forEach>

<c:set var="primaryKeyValues">
	({
	<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
		<c:if test="${not status.first}">, </c:if>
		<c:set var="paramName" value="primaryKeys.${pk.name}"/>
		${pk.name}:'${param[paramName]}'
	</c:forEach>
	})
</c:set>

<ul>
	<c:forEach items="${dataTable.catalogChildrenMap[param.catalog]}" var="childTable">
		<c:set target="${childTableDataHelper}" property="dataTableName" value="${childTable.name}" />
		<c:if test="${childTable.multi or not childTableDataHelper.hasRecord}">
			<li class="adder" dataTableName="${childTable.name}" parentPrimaryKeyValues="${primaryKeyValues}">增加${childTable.label}</li>
		</c:if>
	</c:forEach>
	<c:if test="${not empty dataTable.parent}">
		<li class="deleter" dataTableName="${childTable.name}" parentPrimaryKeyValues="${primaryKeyValues}">删除</li>
	</c:if>
</ul>