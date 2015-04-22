<%@ tag language="java" pageEncoding="UTF-8" body-content="empty"%>
<%@ attribute name="dataTable" description="数据表" required="true" type="com.qinglingsoft.changshuchannel.model.DataTable" %>
<%@ attribute name="data" description="记录数据" required="true" type="java.util.Map" %>
<%@ attribute name="field" description="字段" required="true" type="com.qinglingsoft.changshuchannel.model.DataField" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- 生成预览地址，用时间令牌避免缓存 --%>
<jsp:useBean id="token" class="java.util.Date" scope="page" />
<c:url var="previewUrl" value="previewFileTypeData.action">
	<c:param name="dataTableName" value="${dataTable.name}" />
	<c:forEach items="${dataTable.primaryKeys}" var="pk">
		<c:param name="primaryKeys.${pk.name}" value="${data[pk.name]}" />
	</c:forEach>
	<c:param name="fieldName" value="${field.name}"/>
	<c:param name="token" value="${token.time}"/>
</c:url>

<%-- 生成下载地址，用时间令牌避免缓存 --%>
<c:url var="downloadUrl" value="downloadFileTypeData.action">
	<c:param name="dataTableName" value="${dataTable.name}" />
	<c:forEach items="${dataTable.primaryKeys}" var="pk">
		<c:param name="primaryKeys.${pk.name}" value="${data[pk.name]}" />
	</c:forEach>
	<c:param name="fieldName" value="${field.name}"/>
	<c:param name="token" value="${token.time}"/>
</c:url>
<c:set var="primaryKeyValues">
	({
	<c:forEach items="${dataTable.primaryKeys}" var="pk" varStatus="status">
		<c:if test="${not status.first}">,</c:if>${pk.name}: '${data[pk.name]}'
	</c:forEach>
	})
</c:set>
<%-- 渲染预览图片及连接 --%>
<a id="thumb1" href="${previewUrl}" class="highslide" onclick="return hs.expand(this, { slideshowGroup: '1' })">
	<img keyValue ="${primaryKeyValues}" name="${dataTable.name}" src="${previewUrl}" width="150px" height="30px" />
</a>
<a href="${downloadUrl}" target="_blank">下载</a>
<a href="#" onclick="deleteSelected(${primaryKeyValues})">删除</a>
