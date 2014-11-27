<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/childTable" prefix="childTable" %>

<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" scope="request" />

<spring:useBean id="childTableDataHelper" beanName="childTableDataHelper" scope="request" />
<jsp:setProperty name="childTableDataHelper" property="dataTableName" />
<c:set var="parentTable" value="${dataTable.parent}"/>

<c:forEach items="${parentTable.primaryKeys}" var="parentPk">
	<c:set var="paramName" value="parentPrimaryKeys.${parentPk.name}" />
	<c:set target="${childTableDataHelper.parentPrimaryKeys}" property="${parentPk.name}" value="${param[paramName]}" />
</c:forEach>

<childTable:newDataDialog dataTable="${dataTable}" parentPrimaryKeys="${childTableDataHelper.parentPrimaryKeys}" />

