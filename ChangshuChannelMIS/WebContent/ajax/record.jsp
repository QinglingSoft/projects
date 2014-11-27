<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<%-- 获取描述表 --%>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" />

<%-- 获取表数据 --%>
<spring:useBean id="tableDataHelper" beanName="tableDataHelper" scope="request" />
<jsp:setProperty name="tableDataHelper" property="dataTableName" />
<c:forEach items="${dataTable.primaryKeys}" var="pk">
	<c:set var="paramName" value="primaryKeys.${pk.name}" />
	<c:set target="${tableDataHelper.primaryKeys}" property="${pk.name}" value="${param[paramName]}" />
</c:forEach>
<c:set var="data" value="${tableDataHelper.data}"/>

<%-- 形成摘要文字 --%>
<c:set var="brief"><data:briefTitle dataTable="${dataTable}" data="${data}"/></c:set>

<%-- 渲染界面 --%>
<img class="deleteButton" src="images/delete.gif" title="删除" brief="${brief}" />
<h1 class="ui-widget-header">${brief}</h1>
<data:record data="${data}" dataTable="${dataTable}"/>
