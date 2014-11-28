<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="field" description="字段描述" required="true" type="com.qinglingsoft.changshuchannel.model.DataField" %>
<%@ attribute name="data" description="数据对象" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:choose>
	<c:when test="${field.type == 'STRING'}">
		${data[field.name]}
	</c:when>
	<c:when test="${field.type == 'NUMBER'}">
		<fmt:formatNumber maxFractionDigits="${field.fractionDigits}" value="${data[field.name]}"/>
	</c:when>
	<c:when test="${field.type == 'DATE'}">
		<fmt:formatDate type="DATE" value="${data[field.name]}" />
	</c:when>
	<c:when test="${field.type == 'DATETIME'}">
		<fmt:formatDate type="BOTH" value="${data[field.name]}" />
	</c:when>
	<c:when test="${field.type == 'CODE'}">
		${field.codeTable.codes[data[field.name]].meaning}
	</c:when>
	<c:otherwise>
		<td>${field.type}型</td>
	</c:otherwise>
</c:choose>
