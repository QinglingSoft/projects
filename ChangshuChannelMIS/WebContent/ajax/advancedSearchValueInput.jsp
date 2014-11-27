<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.qinglingsoft.com/java/webFramework/spring" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<spring:useBean id="dataTableHelper" beanName="dataTableHelper" scope="request" />
<jsp:setProperty name="dataTableHelper" property="dataTableName" />
<c:set var="dataTable" value="${dataTableHelper.dataTable}" scope="request" />
<c:set var="field" value="${dataTable.fieldsMap[param.fieldName]}"/>
<c:choose>
	<c:when test="${field.type == 'CODE'}">
		<div class="codeValues">
			<c:forEach items="${field.codeTable.codes}" var="code">
				<li>
					<input type="checkbox" name="conditionValue" value="${code.key}"/>
					${code.value.value}. ${code.value.meaning}
				</li>
			</c:forEach>
		</div>
	</c:when>
	<c:when test="${field.type == 'DATE' || field.type == 'DATETIME'}">
		<input class="Wdate" type="text" name="conditionValue" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="readonly" />
	</c:when>
	<c:when test="${field.type == 'NUMBER' || field.type == 'STRING' || field.type == 'TEXT'}">
		<input type="text" name="conditionValue" />
	</c:when>
</c:choose>