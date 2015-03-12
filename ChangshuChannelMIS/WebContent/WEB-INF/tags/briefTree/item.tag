<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" %>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.qinglingsoft.changshuchannel.model.DataTable" %>
<%@ attribute name="catalog" description="子表所在的栏目" required="true" type="java.lang.String" %>
<%@ attribute name="brief" description="摘要数据" required="true" type="java.util.Map" %>
<%@ attribute name="displayTableLabel" description="显示表标签" required="false" type="java.lang.Boolean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/data" prefix="data" %>
<c:set var="pkJson">
	({
	<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
		<c:if test="${not status.first}">,</c:if>
		${pk.name}: '${brief[pk.name]}'
	</c:forEach>
	})
</c:set>

<c:set var="pkJsonId">
	<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
		<c:if test="${not status.first}">_</c:if>
		${pk.name}_${brief[pk.name]}
	</c:forEach>
</c:set>
<li dataTableName="${dataTable.name}" catalog="${catalog}" primaryKeyValues="${pkJson}" id="${pkJsonId}">
	<c:if test="${not empty dataTable.catalogChildrenMap[catalog]}">
		<div class="expander"></div>
	</c:if>
	<div class="brief">
		<c:if test="${displayTableLabel}">
			<span class="tableLabel">${dataTable.label}</span>
		</c:if>
		<c:forEach items="${dataTable.briefFields}" var="field">
			<span><data:simpleField data="${brief}" field="${field}" /></span>
		</c:forEach>
	</div>
</li>
		