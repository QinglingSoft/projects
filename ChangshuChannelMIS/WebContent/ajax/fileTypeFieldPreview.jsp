<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>

<%-- 获取描述表 --%>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" scope="request" />

<%-- 获取记录数据 --%>
<spring:useBean id="tableDataHelper" beanName="tableDataHelper" scope="request" />
<jsp:setProperty name="tableDataHelper" property="dataTableName" />
<c:forEach items="${dataTable.primaryKeys}" var="pk">
	<c:set var="paramName" value="primaryKeys.${pk.name}" />
	<c:set target="${tableDataHelper.primaryKeys}" property="${pk.name}" value="${param[paramName]}" />
</c:forEach>
<c:set var="data" value="${tableDataHelper.data}" scope="request"/>

<%-- 渲染预览界面 --%>
<c:forEach items="${dataTable.fields}" var="field">
	<c:if test="${field.name == param.fieldName and field.type == 'FILE'}">
		<div class="preview">
			<data:fileTypeFieldPreview data="${data}" dataTable="${dataTable}" field="${field}"/>
		</div>
	</c:if>
</c:forEach>