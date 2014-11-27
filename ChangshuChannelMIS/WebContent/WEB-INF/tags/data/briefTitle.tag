<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.chaos.roadbridge.model.DataTable" %>
<%@ attribute name="data" description="记录数据" required="true" type="java.util.Map" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:forEach items="${dataTable.briefFields}" var="briefField">
	<c:choose>
		<c:when test="${briefField.type == 'STRING'}">
			${briefField.label}：${data[briefField.name]}
		</c:when>
		<c:when test="${briefField.type == 'NUMBER'}">
			${briefField.label}：<fmt:formatNumber value="${data[briefField.name]}" maxFractionDigits="${briefField.fractionDigits}"/>
		</c:when>
		<c:when test="${briefField.type == 'DATE'}">
			${briefField.label}：<fmt:formatDate value="${data[briefField.name]}" type="DATE" />
		</c:when>
		<c:when test="${briefField.type == 'DATETIME'}">
			${briefField.label}：<fmt:formatDate value="${data[briefField.name]}" type="BOTH" />
		</c:when>
		<c:when test="${briefField.type == 'CODE'}">
			${briefField.label}：${briefField.codeTable.codes[data[briefField.name]].meaning}
		</c:when>
	</c:choose>
</c:forEach>
