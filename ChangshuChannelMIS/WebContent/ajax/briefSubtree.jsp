<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%@ taglib tagdir="/WEB-INF/tags/briefTree" prefix="briefTree" %>

<%-- 获取指定树节点的描述表 --%>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

<%-- 指定栏目下的子表、记录列为子树节点 --%>
<c:forEach items="${dataTable.catalogChildrenMap[param.catalog]}" var="childTable">
	<%-- 通过父表主键获取子表数据 --%>
	<spring:useBean id="childTableDataHelper" beanName="childTableDataHelper" scope="page" />
	<c:set target="${childTableDataHelper}" property="dataTableName" value="${childTable.name}" />
	<c:forEach items="${dataTable.primaryKeys}" var="parentPk">
		<c:set var="pkParamName" value="primaryKeys.${parentPk.name}"/>
		<c:set target="${childTableDataHelper.parentPrimaryKeys}" property="${parentPk.name}" value="${param[pkParamName]}"/>
	</c:forEach>
	
	<c:forEach items="${childTableDataHelper.briefList}" var="brief">
		<briefTree:item catalog="${param.catalog}" brief="${brief}" dataTable="${childTable}" displayTableLabel="true" />
	</c:forEach>
</c:forEach>

